import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

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

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static String csvFile = "./09_FilesAndNetwork/Transactions/data/movementList.csv";// адрес оставляю таким, так как проект у меня модуль общего проекта ДЗ
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yy");
    private static Pattern subject = Pattern.compile("([\\d\\S]+)+([\\s\\d]+)+([/\\\\][\\w\\s\\\\/>]+)+\\s{10}([\\w .]+)+");
    private static final int DATE_INDEX = 3;
    private static final int DESCRIPTION_INDEX = 5;
    private static final int INCOMING_INDEX = 6;
    private static final int OUTGOING_INDEX = 7;

    public static void main(String[] args) {
        Configurator.setLevel(logger.getName(), Level.DEBUG);  //Теперь логи будут писаться, несмотря на более высокий уровень логирования в настройках
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
                ArrayList<String> columnList = fragmentsToList(fragments); // а это список строк, на основании которого мы будем создавать список транзакций
                LocalDate date = LocalDate.parse(columnList.get(DATE_INDEX), dateFormat);  // на самом деле дату нам парсить не нужно, просто для тренировки
                String description = normalizeDescription(columnList.get(DESCRIPTION_INDEX));
                BigDecimal incomeMoney = new BigDecimal(columnList.get(INCOMING_INDEX).replaceAll(",", ".").replaceAll("\"", ""));
                BigDecimal outgoingMoney = new BigDecimal(columnList.get(OUTGOING_INDEX).replaceAll(",", ".").replaceAll("\"", ""));
                Transaction transaction = new Transaction(date, description, incomeMoney, outgoingMoney);
                logger.debug("Для теста нам хватит и одной строчки. " + transaction);
                Configurator.setLevel(logger.getName(), Level.INFO);  // лог отключён, так как мы повысили уровень логирования. Ибо нефиг писать логи по 120 строчек
                result.add(transaction);
            }
        } catch (Exception ex) {
            logger.error("Возникла ошибка" + ex);
        }
        return result;
    }

    private static ArrayList<String> fragmentsToList(String[] fragments) {
        ArrayList<String> resultList = new ArrayList<>();
        for (String fragment : fragments) {             // каждый фрагмент массива мы обрабатываем, чтобы учесть запятые внутри кавычек
            if (isColumnPart(fragment)) {                              //Если колонка заканчиваеться на кавычки
                String lastText = resultList.get(resultList.size() - 1);     // то получаем предыдущий фрагмент
                resultList.set(resultList.size() - 1, lastText + "," + fragment);// и прибавляем к нему текущий фрагмент
            } else {
                resultList.add(fragment);           // иначе просто добавляем фрагмент в список
            }
        }
        return resultList;
    }

    //Проверка является ли колонка частью предыдущей колонки, грубовато, но для данного случая достаточно
    private static boolean isColumnPart(String text) {    // Вариант с двумя запятыми внутри кавычек не рассматриваем, так как в нашем файле такого нет
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

