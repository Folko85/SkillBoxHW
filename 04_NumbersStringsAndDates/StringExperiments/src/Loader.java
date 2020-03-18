import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {
    public static void main(String[] args) {
        for (char letter = 'A'; letter <= 'Z'; letter++)   //выведем коды всех прописных букв латинского алфавита
        {
            int sign = letter;
            System.out.println(letter + ": " + Integer.toHexString(sign)); // в шестнадтцатиричном формате
        }

        for (char letter = 'a'; letter <= 'z'; letter++)   //прописные выведем отдельно
        {
            int sign = letter;
            System.out.println(letter + ": " + Integer.toHexString(sign));
        }
        // Предположим, что конструкция предложения неизменна (иначе вообще фиг что выяснишь)
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
        Pattern numbers = Pattern.compile("(\\d+)");    // Создаем объект Pattern
        Matcher numbersMatcher = numbers.matcher(text);               //ему соответствует Matcher
        int sumOfSalary = 0;          /// сюда будем складывать деньги
        while (numbersMatcher.find()) {
            sumOfSalary += Integer.parseInt(numbersMatcher.group());  //суммируем всё, что найдём
        }
        System.out.println("Сумма всех окладов: " + sumOfSalary);

        // разбивка текста
        String bigText = "Tastes differ. That’s why all people wear different clothes. Besides they wear different clothes when it is warm and cold." +
                " When it is cold we put on sweaters, coats, caps and gloves. When it’s warm we take off warm clothes and put on light shirts or blouses and dresses." +
                " My favourite clothes are jeans, shirts and sweaters or jackets. They are comfortable. And I can wear them in any weather." +
                " Now I’m wearing jeans, a white shirt and a sweater. But tomorrow is my friend’s birthday. He invited me to the birthday party. So I shall be in my best.";
        Pattern words = Pattern.compile("[a-zA-Z’]+");    // не хотелось бы разделять всякие It’s и what’s, поэтому оставляем символ ’
        Matcher wordsMatcher = words.matcher(bigText);
        while (wordsMatcher.find()) {
            System.out.println(wordsMatcher.group());     //печатаем всё, что найдём
        }
        System.out.println("Введите ФИО:");
        Scanner scanner = new Scanner(System.in);
        String yourName = scanner.nextLine();           //  уберём лишние пробелы по краям
        Pattern names = Pattern.compile("[а-яА-ЯёЁ]+[-]?[а-яА-ЯёЁ]+");   //допустим только один дефис в середине
        Matcher namesMatcher = names.matcher(yourName);
        String[] titles = {"Фамилия: ", "Имя: ", "Отчество: "};
        ArrayList<String> result = new ArrayList<>(Arrays.asList(titles));  //ArrayList позволяет добавлять элементы в середину массива.
        int fioCounter = 0;         // нам надо проверять количество введённых строк
        while (namesMatcher.find()) {
            fioCounter++;
            result.add((fioCounter * 2) - 1, firstToUpperNextToLow(namesMatcher.group()) + "\n");
        }
        if (fioCounter != 3) {
            System.out.println("Вы должны ввести три слова русским алфавитом, без цифр, разделённые двумя пробелами");
        } else {
            for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i));
            }
        }
        System.out.println("Введите номер телефона:");
        String telephone = scanner.nextLine();
        Pattern telephoneNumber = Pattern.compile("[\\+]?[\\d]+");  //берём из введённого только цифры и +
        Matcher telephoneMatcher = telephoneNumber.matcher(telephone);
        String resultTelephone = "";
        while (telephoneMatcher.find()) {
            resultTelephone += telephoneMatcher.group();    // и складываем их вместе
        }
        if (resultTelephone.length() == 11 && resultTelephone.indexOf('+') < 0 && resultTelephone.charAt(0) == '8') {    //формат с первой восьмёркой
            System.out.println("Номер телефона: +7 " + resultTelephone.substring(1, 4) + " " + resultTelephone.substring(4, 7) +
                    "-" + resultTelephone.substring(7, 9) + "-" + resultTelephone.substring(9, 11));
        } else if ((resultTelephone.length() == 12 && resultTelephone.indexOf('+') == 0)) {
            System.out.println("Номер телефона: " + resultTelephone.substring(0, 2) + " " + resultTelephone.substring(2, 5) + " " + resultTelephone.substring(5, 8) +  // формат с первым плюсом
                    "-" + resultTelephone.substring(8, 10) + "-" + resultTelephone.substring(10, 12));
        } else {
            System.out.println("Некорректный номер телефона");    // всё остальное
        }

        // Сюда же добавим последнее задание с датами. Их ведь тоже можно вводить
        Calendar today = Calendar.getInstance();           // текущая дата
        Calendar myBorn = Calendar.getInstance();          // день рождения будем вводить из консоли
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");   // формат в котором принимаем дату, максимально простой
        SimpleDateFormat outFormat = new SimpleDateFormat("dd MMMM yyyy - EEEE", Locale.forLanguageTag("ru"));  //формат вывода, вестимо на русском
        System.out.println("Введите дату рождения в формате dd-MM-yyyy");
        String birthDay = scanner.nextLine();
        Pattern date = Pattern.compile("([\\d]{2})[-]([\\d]{2})[-]([\\d]{4})");    //в этом деле требуется точный ввод
        Matcher dateMatcher = date.matcher(birthDay);
        int yourAge = 0;          // введём дополнительную переменную, чтобы фиксировать возраст
        if (dateMatcher.find()) {
            if ((Integer.parseInt(dateMatcher.group(1)) <= 31) && Integer.parseInt(dateMatcher.group(2)) <= 12) {  // грубая проверка корректности ввода числа и месяца
                try {                                                                 //// устанавливаем введённую дату в календарь и распечатываем все годовщины
                    for (myBorn.setTime(format.parse(dateMatcher.group())); myBorn.before(today); myBorn.add(Calendar.YEAR, 1)) {
                        System.out.println(yourAge + " - " + outFormat.format(myBorn.getTime()));
                        yourAge++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Вы ввели некорректную дату");
            }
        } else {
            System.out.println("Вы ввели дату в неверном формате");
        }

        // Решаем задачу с датами другими методами и с помощью других классов. Структура кода осталась почти той же.
        LocalDate todayDate = LocalDate.now();       // текущая дата
        LocalDate birthdayDate;                     //день рождения, пока просто объявляем
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy - EEEE", new Locale("ru"));
        System.out.println("Введите дату рождения в формате dd-MM-yyyy");
        String birthdayString = scanner.nextLine();
        Pattern inputDate = Pattern.compile("([\\d]{2})[-]([\\d]{2})[-]([\\d]{4})");    //в этом деле требуется точный ввод
        Matcher inputDateMatcher = inputDate.matcher(birthdayString);
        int yourAgeNow = 0;          // введём дополнительную переменную, чтобы фиксировать возраст
        if (inputDateMatcher.find()) {
            if ((Integer.parseInt(dateMatcher.group(1)) <= 31) && Integer.parseInt(dateMatcher.group(2)) <= 12) {  // грубая проверка корректности ввода числа и месяца
                int dayDate = Integer.parseInt(inputDateMatcher.group(1));
                int monthDate = Integer.parseInt(inputDateMatcher.group(2));
                int yearDate = Integer.parseInt(inputDateMatcher.group(3));       // устанавливаем введённую дату в календарь и распечатываем все годовщины
                for (birthdayDate = LocalDate.of(yearDate, monthDate, dayDate); birthdayDate.isBefore(todayDate); birthdayDate = birthdayDate.plusYears(1)) {
                    System.out.println(yourAgeNow + " - " + outputFormat.format(birthdayDate));
                    yourAgeNow++;
                }
            } else {
                System.out.println("Вы ввели некорректную дату");
            }
        } else {
            System.out.println("Вы ввели дату в неверном формате");
        }
    }

    public static String firstToUpperNextToLow(String name)   // вдруг кто-то любит писать заглавными.
    {                                                         // или только строчными. Этож не ошибка
        String nameToUp = new String();
        if (name.indexOf('-') > 0) {
            int tire = name.indexOf('-');      // учтём дефис, есть же двойные фамилии
            nameToUp = name.substring(0, 1).toUpperCase() + name.substring(1, tire + 1).toLowerCase()
                    + name.substring(tire + 1, tire + 2).toUpperCase() + name.substring(tire + 2).toLowerCase();
        } else nameToUp = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return nameToUp;
    }
}