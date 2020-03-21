import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {
        String text = "Каждый охотник желает знать, где сидит фазан";
        String[] colors = text.split(",?\\s+");
        for (int i = 0; i < colors.length; i++) {
            System.out.println(colors[i]);
        }
        // чтобы перевернуть поменяем местами первые и последние элементы массива
        for (int j = 0; j < colors.length / 2; j++) {
            String temp = colors[j];
            colors[j] = colors[colors.length - j - 1];
            colors[colors.length - j - 1] = temp;
        }
        // Проверяем, всё ли верно
        System.out.print("\n");   // разрыв между массивами, для лучшей видимости
        for (String color : colors) {
            System.out.println(color);
        }
        // средняя температура по больнице
        int minValue = 32;
        int maxValue = 40;
        int healthyPatientsCount = 0; // счётчик здоровых пациентов
        double minHealthyTemperature = 36.2;
        double maxHealthyTemperature = 36.9;
        double patients[] = new double[30];
        for (int k = 0; k < patients.length; k++) {
            patients[k] = randomValue(minValue, maxValue);
        }
        System.out.format("Средняя температура по больнице: %.1f%n", calculateAverage(patients));
        System.out.println("Здоровых пациентов: " + countHealthPatients(patients, minHealthyTemperature, maxHealthyTemperature));

        String[][] crossing = new String[7][7];                     // крестик рисуем двумерным массивом
        for (int row = 0; row < crossing.length; row++) {
            for (int column = 0; column < crossing[0].length; column++) {
                if (row == column || row == (crossing[0].length - 1 - column)) {
                    crossing[row][column] = "X";
                } else {
                    crossing[row][column] = "  "; // поставим два пробела, чтоб крестик был не сильно узким
                }
                System.out.print(crossing[row][column]);
            }
            System.out.print("\n");
        }


    }

    public static double randomValue(int min, int max)  //оформим методом получение случайных значений
    {
        max -= min;   // диапазон Math.random считается от нуля, вычтем min, потом прибавим
        double result = (Math.random() * max) + min;
        double around = new BigDecimal(result).setScale(1, RoundingMode.HALF_UP).doubleValue(); // округляем до десятых по обзщепринятым правилам округления
        return around;
    }

    public static double calculateAverage(double[] array)  //вычисление средней температуры
    {
        DoubleStream stream = Arrays.stream(array);
        return stream.average().getAsDouble();

    }

    public static int countHealthPatients(double[] array, double minTemperature, double maxTemperature)  //подсчёт количества здоровых пациентов
    {
        DoubleStream stream = Arrays.stream(array);
        return (int) stream.filter(a -> a >= minTemperature && a <= maxTemperature).count();
    }
}
