import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> arraySign = new ArrayList<>();          //список для хранения знаков
        String[] chars = new String[]{"А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"};   // буквы, используемые в Российских номерах
        Arrays.stream(chars).forEach(                            //прилежные ученики используют всю силу StreamAPI и мудрость предыдущих обитателей чатика
                letterA -> IntStream.range(1, 10).forEach(
                        num -> Arrays.stream(chars).forEach(
                                letterB -> Arrays.stream(chars).forEach(
                                        letterC -> IntStream.range(1, 200).forEach(          // реальные коды регионов - чересполосица случайных цифр. Не будем излишне усложнять,
                                                region -> arraySign.add(letterA + num + num + num + letterB + letterC + String.format("%03d", region))
                                        )
                                )
                        )
                )

        );
        System.out.println("Количество знаков в списке: " + arraySign.size());
        // сортировка не нужна, ведь мы правильно сформировали список.
        HashSet hashSign = new HashSet(arraySign);
        TreeSet treeSign = new TreeSet(arraySign);     //множества создаём на основе списков

        String searchResult = "";
        while (true) {
            System.out.println("Введите номер для поиска");
            Scanner input = new Scanner(System.in);
            String forFind = input.nextLine();
            long start = System.nanoTime();
            if (arraySign.contains(forFind)) {
                searchResult = "найден";
            } else {
                searchResult = " не найден";
            }
            long duration = System.nanoTime() - start;
            System.out.println("Поиск перебором: номер " + searchResult + ", поиск занял " + duration + " нс");

            start = System.nanoTime();
            if (Collections.binarySearch(arraySign, forFind) >= 0) {
                searchResult = "найден";
            } else {
                searchResult = " не найден";
            }
            duration = System.nanoTime() - start;
            System.out.println("Бинарный поиск: номер " + searchResult + ", поиск занял " + duration + " нс");

            start = System.nanoTime();
            if (hashSign.contains(forFind)) {
                searchResult = "найден";
            } else {
                searchResult = " не найден";
            }
            duration = System.nanoTime() - start;
            System.out.println("Поиск в HashSet: номер " + searchResult + ", поиск занял " + duration + " нс");

            start = System.nanoTime();
            if (treeSign.contains(forFind)) {
                searchResult = "найден";
            } else {
                searchResult = " не найден";
            }
            duration = System.nanoTime() - start;
            System.out.println("Поиск в TreeSet: номер " + searchResult + ", поиск занял " + duration + " нс");

        }
    }
}
