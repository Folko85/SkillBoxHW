import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // мы не ищем лёгких путей. Находить размер папки будем с помощью интерфейса FileVisitor
        while (true) {
            System.out.println("Введите имя папки: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            Path path = Paths.get(input);

            if (!Files.exists(path) || !Files.isDirectory(path)) {   // избавляемся от неверного ввода или от ввода имени файла
                System.out.println("Вы ввели невозможное имя.");     // мы ведь считаем размер папки.
                continue;
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

        }
    }

    public static String sizeDef(long sizeByte)  // вынесем отпределение размерности в отдельный метод
    {
        BigDecimal sizeKiloByte = new BigDecimal(sizeByte / 1024).setScale(2, RoundingMode.HALF_UP);
        BigDecimal sizeMegaByte = sizeKiloByte.divide(new BigDecimal(1024)).setScale(2,RoundingMode.HALF_UP);
        if (sizeByte < 1024) {
            return sizeByte + " Б";
        } else if (sizeKiloByte.compareTo(new BigDecimal(1024)) < 0) {
            return sizeKiloByte + " Кб";
        } else if (sizeMegaByte.compareTo(new BigDecimal(1024)) < 0) {
            return sizeMegaByte + " Мб";
        }
        return sizeMegaByte.divide(new BigDecimal(1024)).setScale(2,RoundingMode.HALF_UP) + " Гб";
    }
}
