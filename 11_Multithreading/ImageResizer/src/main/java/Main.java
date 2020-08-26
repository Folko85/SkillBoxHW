import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        String srcFolder;
        String dstFolder;
        int size;
        if (args.length == 0) {       // если программа запущена без аргументов, предлагаем ввести параметры
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите адрес исходной папки");
            srcFolder = scanner.nextLine();       //папка, которую копируем
            logger.info("Копируемая папка: " + srcFolder);
            System.out.println("Введите адрес конечной папки");
            dstFolder = scanner.nextLine();       // папка, куда копируем
            logger.info("Папка назначения: " + dstFolder);
            size = 300;
        } else {
            CommandLineArgs parameters = new CommandLineArgs();
            JCommander commander = JCommander.newBuilder()
                    .addObject(parameters)
                    .build();
            try {
                commander.parse(args);
            } catch (ParameterException parEx) {
                logger.error("Возникла ошибка при вводе параметров: " + parEx);
                badArgsExit();
            }
            srcFolder = parameters.getPaths().get(0);       //папка, которую копируем
            dstFolder = parameters.getPaths().get(1);       // папка, куда копируем
            size = parameters.getSize();
        }
        try {

            int processorsCount = Runtime.getRuntime().availableProcessors();
            List<File> files = Files.walk(Paths.get(srcFolder)).map(Path::toFile)
                    .filter(file -> !file.isDirectory()).filter(f -> getFileExtension(f).equals("jpg") || getFileExtension(f).equals("png"))
                    .sorted(Comparator.comparing(File::length)).collect(Collectors.toList());
            // сортировка сильно замедляет процесс, но без неё как-то ненаглядно выходит. Пусть в потоках с чуть
            // большим количеством файлов, исходные файлы будут меньше
            System.out.println("Ядер задействовано: " + processorsCount);
            int filesPerThread = files.size() / processorsCount;  // количество файлов на поток
            int oneMoreSizeCount = files.size() % processorsCount;  // количество потоков, где на 1 файл больше
            int bound = oneMoreSizeCount * (filesPerThread + 1);
            List<File> subListOneMore = files.subList(0, bound); // здесь все файлы для увеличенных потоков
            List<File> subListStandard = files.subList(bound, files.size());
            for (int i = 0; i < processorsCount; i++) {  // не люблю такие формулы, но не городить же лишние сущности ради одного цикла
                int lowBound = (i < oneMoreSizeCount) ? i * (filesPerThread + 1) : (i - oneMoreSizeCount) * filesPerThread;
                int upBound = (i < oneMoreSizeCount) ? lowBound + filesPerThread + 1 : lowBound + filesPerThread;
                List<File> perThread = (i < oneMoreSizeCount) ? subListOneMore.subList(lowBound, upBound) : subListStandard.subList(lowBound, upBound);
                ImageResizer ir = new ImageResizer(perThread, dstFolder, size);  // вот и нашлось место для тернарных операторов
                new Thread(ir).start();
            }
        } catch (Exception e) {
            logger.error("Произошла ошибка " + e);
            System.out.println("Проверьте корректность введённых данных");
            System.exit(-1);
        }
    }

    //метод определения расширения файла
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".") + 1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }

    private static void badArgsExit() { // выход из приложения с сообщением об ошибке
        System.err.println("Неверный параметр. Пример вызова программы:");
        System.err.println("java -jar ImageResizer.jar -paths source target -size 300");
        System.exit(-1);                                     // не знал, что так можно
    }
}

class CommandLineArgs {
    @Parameter(names = "-size", arity = 1, description = "WidthSize")
    private int size;
    @Parameter(names = "-paths", arity = 2, description = "Paths")
    private List<String> paths;

    public int getSize() {
        return size;
    }

    public List<String> getPaths() {
        return paths;
    }
}
