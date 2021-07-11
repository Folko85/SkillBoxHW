package benchmark;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    static char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

    private int region;

    public Generator(int regions) {
        this.region = regions;
    }

    public static void oldGenerate() throws IOException {

        FileOutputStream writer = new FileOutputStream("res/numbers.txt");

        for (int number = 1; number < 1000; number++) {
            int regionCode = 199;
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {
                        String carNumber = firstLetter + padNumber(number, 3) +
                                secondLetter + thirdLetter + padNumber(regionCode, 2);
                        writer.write(carNumber.getBytes());
                        writer.write('\n');
                    }
                }
            }
        }
        writer.flush();
        writer.close();
    }

    public static void newGenerate(int regionCode) {
        try (PrintWriter writer = new PrintWriter("res/numbers" + regionCode + ".txt")) {
                StringBuilder builder = new StringBuilder();
                for (int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                builder.append(firstLetter);
                                builder.append(newPadNumber(number, 3));
                                builder.append(secondLetter);
                                builder.append(thirdLetter);
                                builder.append(newPadNumber(regionCode, 2));
                                builder.append("\n");
                            }
                        }
                    }
                }
                writer.write(builder.toString());
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }

    private static StringBuilder newPadNumber(int number, int numberLength) {
        StringBuilder numberStr = new StringBuilder();
        numberStr.append(number);
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr.insert(0, '0');
        }
        return numberStr;
    }
}
