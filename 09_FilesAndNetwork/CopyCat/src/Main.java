import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.commons.io.file.PathUtils;
import org.javatuples.Quintet;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {
    //This program will be used to COPY the source CATALOG to the target CATALOG.
    // That's why it's called CopyCat. Cats have nothing to do with this:))) . Copycats too.


    public static void main(String[] args) throws IOException {
        // вынесем в отдельный метод обработку входных параметров. Это ведь логично.
        // что если использовать кортеж для объединения этого в один метод?

        Quintet<Boolean, Boolean, Boolean, Path, Path> arguments; // оставим квинтет для случая запуска без параметров, жалко его выкидывать
        boolean recursive;          // рекурсивно
        boolean preserve;           // сохранять параметры
        boolean overwrite;  // перезаписывать ли файлы
        Path source;       //папка, которую копируем
        Path target;        // папка, куда копируем

        if (args.length == 0) {       // если программа запущена без аргументов, предлагаем ввести параметры
            arguments = argsInput();
            recursive = arguments.getValue0(); // рекурсивно
            preserve = arguments.getValue1();  // сохранять параметры
            overwrite = arguments.getValue2();   // перезаписывать ли файлы
            source = arguments.getValue3();       //папка, которую копируем
            target = arguments.getValue4();       // папка, куда копируем
        } else {
            CommandLineArgs parameters = new CommandLineArgs();
            JCommander commander = JCommander.newBuilder()
                    .addObject(parameters)
                    .build();
            try {
                commander.parse(args);
            } catch (ParameterException parex) {
                badArgsExit();
            }
            recursive = parameters.isRecursive(); // рекурсивно
            preserve = parameters.isPreserve();  // сохранять параметры
            overwrite = parameters.isOverwrite();   // перезаписывать ли файлы
            source = Paths.get(parameters.getPaths().get(0));       //папка, которую копируем
            target = Paths.get(parameters.getPaths().get(1));       // папка, куда копируем

        }

        // здесь мы закончили обрабатывать входные аргументы

        // если путь существет, то копируем туда папку, если не существет, то создаём путь и копируем туда файлы из папки
        Path dest = (Files.isDirectory(target)) ? target.resolve(source.getFileName()) : target;
        StandardCopyOption[] options = null;  //используем этот класс для определения параметров копирования
        if (preserve && overwrite) {
            options = new StandardCopyOption[]{COPY_ATTRIBUTES, REPLACE_EXISTING};
        } else if (preserve) {
            options = new StandardCopyOption[]{COPY_ATTRIBUTES};
        } else if (overwrite) {
            options = new StandardCopyOption[]{REPLACE_EXISTING};
        }
        try {
            if (recursive) {     // если рекурсивно, то проходимся по всему дереву
                PathUtils.copyDirectory(source, dest, options);
            } else {
                Files.copy(source, target, options);
            }
        } catch (Exception exception) {
            System.err.println("Ошибка " + exception + " Не удалось скопировать.");
        }

    }            // здесь и заканчивается метод мейн, если отбросить всё (даже не всё) лишнее

    static Quintet<Boolean, Boolean, Boolean, Path, Path> argsInput() {  // здесь оставим квинтет, не зря ж я его добавлял изначально))
        Quintet<Boolean, Boolean, Boolean, Path, Path> result = new Quintet<>(false, false, false, null, null);  // подсказать, если аргументов не введено - наша задача
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя копируемой папки: ");
        result = result.setAt3(Paths.get(scanner.nextLine()));
        System.out.println("Введите имя папки назначения: ");
        result = result.setAt4(Paths.get(scanner.nextLine()));

        result = result.setAt0(isTrueArgs("Копировать ли вложенные файлы и папки (да/нет)? "));
        result = result.setAt1(isTrueArgs("Сохранять ли параметры скопированных файлов (да/нет)?"));
        result = result.setAt2(isTrueArgs("Разрешить перезапись файлов (да/нет)?"));
        return result;
    }

    static boolean isTrueArgs(String text) {
        String answer = System.console().readLine(text);
        return (answer.equalsIgnoreCase("д") || answer.equalsIgnoreCase("да"));
    }

    static void badArgsExit() {                                      // выход из приложения с сообщением об ощибке
        System.err.println("Неверный параметр. Пример вызова программы:");
        System.err.println("java -jar copycat.jar -r -p -o -paths source target");
        System.err.println("Параметры -r(recursive) -o(overwrite) -p(preserve) необязательны");
        System.exit(-1);                                     // не знал, что так можно
    }


}


