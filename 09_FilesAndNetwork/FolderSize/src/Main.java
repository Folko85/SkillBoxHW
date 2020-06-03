import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // мы не ищем лёгких путей. Находить размер папки будем с помощью интерфейса FileVisitor
        String input;      //
        Path path;

        while (true) {
            if (args.length > 1) {
                System.out.println("Программа может иметь только один аргумент - имя папки.");
                break;        // если кто-то решил ввести несколько аргуметов
            } else if (args.length == 0) {
                input = inputPathName();  // если программа вызвана без параметров, то она сама предложит ввести имя файла
            } else {
                input = args[0]; // в противном случае имя папки берётся из единственного параметра
            }
            try {
                path = Paths.get(input);
            } catch (InvalidPathException ex) {
                System.out.println("Введено некорректное имя файла: " + input);
                System.out.println("Возникла ошибка: " + ex);
                break;
            }
            if (!Files.exists(path) || !Files.isDirectory(path)) {   // избавляемся от неверного ввода или от ввода имени файла
                System.out.println("Вы ввели невозможное имя папки.");     // мы ведь считаем размер папки.
                break;                                              // если имя папки введено неверно, программа будет прекращать выполнение
            }
            FolderSizeVisitor visitor = new FolderSizeVisitor(path);
            try {
                Files.walkFileTree(path, visitor);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long sizeByte = visitor.getPathSize();

            System.out.println("Размер папки \"" + path + "\" составляет: " + sizeDef(sizeByte));
            if (visitor.getExceptionCounter() > 0) {
                System.out.println("В ходе подсчёта не удалось получить доступ к некоторым файлам и папкам. Количество непрочитанных элементов: " + visitor.getExceptionCounter());
            }
            break;
        }
    }


    public static String inputPathName() {            // вынесем это в отдельный метод и будем вызывать, если пользователь вызвал программу без параметров
        System.out.println("Введите имя папки: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static String sizeDef(long sizeByte)  // вынесем отпределение размерности в отдельный метод
    {
        BigDecimal sizeKiloByte = new BigDecimal(sizeByte / 1024).setScale(2, RoundingMode.HALF_UP);
        BigDecimal sizeMegaByte = sizeKiloByte.divide(new BigDecimal(1024)).setScale(2, RoundingMode.HALF_UP);
        if (sizeByte < 1024) {
            return sizeByte + " Б";
        } else if (sizeKiloByte.compareTo(new BigDecimal(1024)) < 0) {
            return sizeKiloByte + " Кб";
        } else if (sizeMegaByte.compareTo(new BigDecimal(1024)) < 0) {
            return sizeMegaByte + " Мб";
        }
        return sizeMegaByte.divide(new BigDecimal(1024)).setScale(2, RoundingMode.HALF_UP) + " Гб";
    }
}
