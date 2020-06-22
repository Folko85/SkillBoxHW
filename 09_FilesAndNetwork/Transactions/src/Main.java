import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static String csvFile = "./09_FilesAndNetwork/Transactions/data/movementList.csv";// адрес оставляю таким, так как проект у меня модуль общего проекта ДЗ
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yy");
    private static Pattern subject = Pattern.compile("([\\d\\S]+)+([\\s\\d]+)+([/\\\\][\\w\\s\\\\/>]+)+\\s{10}([\\w .]+)+");

    public static void main(String[] args) {

        ArrayList<Transaction> operations = loadFromFile(csvFile);
        BigDecimal sumInput = operations.stream().map(Transaction::getIncomingMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Сумма доходов " + sumInput);
        BigDecimal sumOutput = operations.stream().map(Transaction::getOutgoingMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Сумма расходов " + sumOutput + "\n");
        Map<String, BigDecimal> sortedOutput = operations.stream().filter(a -> a.getOutgoingMoney().compareTo(BigDecimal.ZERO) > 0)//создаём словарь из листа объектов
                .collect(Collectors.toMap(Transaction::getDescription, Transaction::getOutgoingMoney, (a, b) -> a = a.add(b)));  // значения одинаковых ключей складываем
        System.out.println("Суммы расходов по организациям: ");
        String specifiers = "%-40s %-10s%n";    // это чтоб вывести красивой табличкой
        sortedOutput.forEach((key, value) -> System.out.format(specifiers, key, value));
    }

    private static ArrayList<Transaction> loadFromFile(String csvFile) {  // метод стырен из 7го модуля и чуть изменён
        ArrayList<Transaction> result = new ArrayList<>();                // этот список мы будем возвращать
        try {
            List<String> lines = Files.readAllLines(Paths.get(csvFile));     // это список полученных строк - 120 штук
            lines.remove(0);           // убираем заголовки таблицы
            for (String line : lines) {                              // каждую строку
                String[] fragments = line.split(",");            // мы делим запятыми и записываем в массив
                ArrayList<String> columnList = new ArrayList<>(); // а это список строк, на основании которого мы будем создавать список транзакций
                for (String fragment : fragments) {             // каждый фрагмент массива мы обрабатываем, чтобы учесть запятые внутри кавычек
                    if (IsColumnPart(fragment)) {                              //Если колонка заканчиваеться на кавычки
                        String lastText = columnList.get(columnList.size() - 1);     // то получаем предыдущий фрагмент
                        columnList.set(columnList.size() - 1, lastText + "," + fragment);// и прибавляем к нему текущий фрагмент
                    } else {
                        columnList.add(fragment);           // иначе просто добавляем фрагмент в список
                    }
                }
                LocalDate date = LocalDate.parse(columnList.get(3), dateFormat);  // на самом деле дату нам парсить не нужно, просто для тренировки
                String description = normalizeDescription(columnList.get(5));
                BigDecimal incomeMoney = new BigDecimal(columnList.get(6).replaceAll(",", ".").replaceAll("\"", ""));
                BigDecimal outgoingMoney = new BigDecimal(columnList.get(7).replaceAll(",", ".").replaceAll("\"", ""));
                Transaction transaction = new Transaction(date, description, incomeMoney, outgoingMoney);
                result.add(transaction);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    //Проверка является ли колонка частью предыдущей колонки, грубовато, но для данного случая достаточно
    private static boolean IsColumnPart(String text) {    // Вариант с двумя запятыми внутри кавычек не рассматриваем, так как в нашем файле такого нет
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }

    private static String normalizeDescription(String description) {   // избавляемся от мусора с помощью регулярок
        Matcher subjectMatcher = subject.matcher(description);
        if (subjectMatcher.find()) {
            String result = subjectMatcher.group(3).trim();
            result = result.replaceAll("\\\\", "");  // убираем слеши
            result = result.replaceAll("/", "");
            return result;
        } else return null;
    }
}

