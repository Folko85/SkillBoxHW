import org.javatuples.Quintet;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;
import java.util.Scanner;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {
    //This program will be used to COPY the source CATALOG to the target CATALOG.
    // That's why it's called CopyCat. Cats have nothing to do with this:))) . Copycats too.

    public static void main(String[] args) throws IOException {
        // вынесем в отдельный метод обработку входных параметров. Это ведь логично.
        // что если использовать кортеж для объединения этого в один метод?

        Quintet<Boolean, Boolean, Boolean, Path, Path> arguments;

        if (args.length == 0) {       // если программа запущена без аргументов, предлагаем ввести параметры
            arguments = argsInput();
        } else {
            arguments = argsConversion(args);    // иначе переводим аргументы в удобный вид
        }
        boolean recursive = arguments.getValue0(); // рекурсивно
        boolean preserve = arguments.getValue1();  // сохранять параметры
        boolean overwrite = arguments.getValue2();   // перезаписывать ли файлы
        Path source = arguments.getValue3();       //папка, которую копируем
        Path target = arguments.getValue4();       // папка, куда копируем
        // здесь мы закончили обрабатывать входные аргументы

        // если путь существет, то копируем туда папку, если не существет, то создаём путь и копируем туда файлы из папки
        Path dest = (Files.isDirectory(target)) ? target.resolve(source.getFileName()) : target;

        if (recursive) {     // если рекурсивно, то проходимся по всему дереву
            EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
            CopyCatVisitor tc = new CopyCatVisitor(source, dest, overwrite, preserve);
            Files.walkFileTree(source, opts, Integer.MAX_VALUE, tc);
        } else {
            copyFile(source, dest, overwrite, preserve);
        }
    }            // здесь и заканчивается метод мейн, если отбросить всё (даже не всё) лишнее

    static Quintet<Boolean, Boolean, Boolean, Path, Path> argsConversion(String[] arguments) {      // обработка аргументов, переданных в командной строке
        Quintet<Boolean, Boolean, Boolean, Path, Path> result = new Quintet<>(false, false, false, null, null);
        int index = 0;
        while (index < arguments.length) {
            String arg = arguments[index];
            if (!arg.startsWith("-"))  // проверяем, есть ли дополнительные параметры, доппараметром считается всё, что начинаем с дефиса
                break;                    // если доппараметра нет идём дальше
            if (arg.length() < 2)         // если есть дополнительные параметры и меньше двух букв, то есть один дефис без буквы
                badArgsExit();                  // значит мы чего-то забыли, выходим с ошибкой
            for (int j = 1; j < arg.length(); j++) {   // этот цикл для случаев написания -rip, иначе рн выполнится один раз
                char c = arg.charAt(j);
                switch (c) {                 // свитч внутри цикла, норм
                    case 'r':
                        result = result.setAt0(true);   // использовать рекурсию
                        break;
                    case 'p':
                        result = result.setAt1(true); //копировать атрибуты
                        break;
                    case 'o':
                        result = result.setAt2(true); //разрешение перезаписи файлов
                        break;
                    default:
                        badArgsExit();   // другие буквы не принимаются
                }
            }
            index++;
        }
        // оставшиеся аргументы - имена папок
        if (arguments.length - index != 2) {    // наша программа не будет копировать по несколько папок сразу.
            badArgsExit();
        }
        result = result.setAt3(Paths.get(arguments[index]));   // кортежи неизменны (immutable), как и строки, так что запись именно такая
        result = result.setAt4(Paths.get(arguments[index + 1]));
        return result;
    }

    static Quintet<Boolean, Boolean, Boolean, Path, Path> argsInput() {  // пользователь не обязан знать аргументы программы
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

    static void copyFile(Path source, Path target, boolean overwrite, boolean preserve) {
        CopyOption[] options = (preserve) ?
                new CopyOption[]{COPY_ATTRIBUTES, REPLACE_EXISTING} :
                new CopyOption[]{REPLACE_EXISTING};
        if (!overwrite || Files.notExists(target) || okayToOverwrite(target)) {
            try {
                Files.copy(source, target, options);
            } catch (IOException x) {
                System.err.format("Невозможно скопировать: %s: %s%n", source, x);
            }
        }
    }

    /**
     * Метод спрашивает, переписывать ли файл, если программа запущена с параметром -o
     */
    static boolean okayToOverwrite(Path file) {
        String answer = System.console().readLine("перезаписать %s (да/нет)? ", file);       // этот метод приводит в экзепшен среду, но скомпилированный и запущенный
        return (answer.equalsIgnoreCase("д") || answer.equalsIgnoreCase("да"));  // файл отлично работают.
    }

    static boolean isTrueArgs(String text) {
        String answer = System.console().readLine(text);
        return (answer.equalsIgnoreCase("д") || answer.equalsIgnoreCase("да"));
    }

    static void badArgsExit() {                                      // выход из приложения с сообщением об ощибке
        System.err.println("Неверный аргумент. Пример вызова программы:");
        System.err.println("java -jar copycat.jar -rp source target");
        System.err.println("java -jar copycat.jar -r -p source target");
        System.exit(-1);                                     // не знал, что так можно
    }

    static class CopyCatVisitor implements FileVisitor<Path> {

        private final Path source;
        private final Path target;
        private final boolean preserve;
        private final boolean overwrite;

        public CopyCatVisitor(Path source, Path target, boolean overwrite, boolean preserve) {
            this.source = source;
            this.target = target;
            this.preserve = preserve;
            this.overwrite = overwrite;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
            // перед входом в папку, мы её копируем
            CopyOption[] options = (preserve) ?
                    new CopyOption[]{COPY_ATTRIBUTES} : new CopyOption[0];

            Path newDir = target.resolve(source.relativize(path));
            try {
                Files.copy(path, newDir, options);
            } catch (FileAlreadyExistsException x) {
            } catch (IOException x) {
                System.err.format("Невозможно создать: %s: %s%n", newDir, x);
                return SKIP_SUBTREE;
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
            copyFile(file, target.resolve(source.relativize(file)), overwrite, preserve); // использовать этот статический метод из мейна позволяет тот факт
            return CONTINUE;                                                    //что наш класс встроенный и статически
        }                                                                      // иначе пришлось бы делать костыли

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            if (exc instanceof FileSystemLoopException) {
                System.err.println("Обнаружено зацикливание: " + file);
            } else {
                System.err.format("Невозможно скопировать: %s: %s%n", file, exc);
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
            // фмксируем время изменени я папки
            if (exc == null && preserve) {
                Path newCatalog = target.resolve(source.relativize(path));
                try {
                    FileTime time = Files.getLastModifiedTime(path);
                    Files.setLastModifiedTime(newCatalog, time);
                } catch (IOException x) {
                    System.err.format("Невозможно скопирповать все параметры: %s: %s%n", newCatalog, x);
                }
            }
            return CONTINUE;
        }
    }
}


