import java.util.Scanner;

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
        int sumOfSalary = 0;          /// сюда будем складывать деньги
        String onlyNumbers = clearInput(text, "[\\D+\\s+]").trim();
        String[] parts = onlyNumbers.split(" ");   // разделим оклады по пробелам
        int salary = 0;                          //вспомогательная переменная, где будут содержаться оклады
        for (int i = 0; i < parts.length; i++) {
            salary = Integer.parseInt(parts[i]);
            sumOfSalary += salary;
        }
        System.out.println("Сумма всех окладов: " + sumOfSalary);


        // разбивка текста
        String bigText = "Tastes differ. That’s why all people wear different clothes. Besides they wear different clothes when it is warm and cold." +
                " When it is cold we put on sweaters, coats, caps and gloves. When it’s warm we take off warm clothes and put on light shirts or blouses and dresses." +
                " My favourite clothes are jeans, shirts and sweaters or jackets. They are comfortable. And I can wear them in any weather." +
                " Now I’m wearing jeans, a white shirt and a sweater. But tomorrow is my friend’s birthday. He invited me to the birthday party. So I shall be in my best.";
        bigText = clearInput(bigText, "[-.?!)(,:]");
        String[] wordsOfBigText = bigText.split(" ");
        for (int j = 0; j < wordsOfBigText.length; j++) {
            System.out.println(wordsOfBigText[j]);
        }
        System.out.println("Введите ФИО:");
        Scanner scanner = new Scanner(System.in);
        String yourName = scanner.nextLine().trim();           //  уберём лишние пробелы по краям
        String clearName = clearInput(yourName, " -");            // уберём мусор
        String checkName = checkName(clearName);           //проверим корректность введённой информации
        System.out.println(checkName);                    // выведем на экран
        System.out.println("Введите номер телефона:");
        String telephone = scanner.nextLine().trim();
        telephone = telephone.replaceAll("[^0-9+]", "");
        if (telephone.length() == 11 && telephone.indexOf('+') < 0 && telephone.charAt(0) == '8') {    //формат с первой восьмёркой
            System.out.println("Номер телефона: +7 " + telephone.substring(1, 4) + " " + telephone.substring(4, 7) +
                    "-" + telephone.substring(7, 9) + "-" + telephone.substring(9, 11));
        } else if ((telephone.length() == 12 && telephone.indexOf('+') == 0)) {
            System.out.println("Номер телефона: " + telephone.substring(0, 2) + telephone.substring(2, 5) + " " + telephone.substring(5, 8) +  // формат с первым плюсом
                    "-" + telephone.substring(8, 10) + "-" + telephone.substring(10, 12));
        } else {
            System.out.println("Некорректный номер телефона");    // всё остальное
        }
    }

    public static String clearInput(String name, String regex)   //очищаем строку от случайно введённых знаков
    {
        name = name.replaceAll(regex, " ");  // избавляемся от лишних знаков
        name = name.replaceAll("\\s+", " ");//  избавимся от задвоенных/троенных и т.д. пробелов в центре
        return name;
    }

    public static String checkName(String name) {
        String[] titles = {"Фамилия", "Имя", "Отчество"};
        String[] names = name.split(" ");
        String result = "";
        if (names.length != 3) {
            result = "Вы должны ввести три слова разделённые двумя пробелами";
        } else {                 // проверяем каждую графу.
            for (int k = 0; k < names.length; k++) {
                if (!names[k].matches("[А-я,ё,Ё, ,-]*"))    // проверяем на лишние символы
                {
                    result += "Введены недопустимые символы в графе " + titles[k] + "\n"; //остановимся на кириллице, дефисе и пробелах
                } else {
                    result += titles[k] + ": " + firstToUpperNextToLow(names[k]) + "\n";
                }
            }
        }
        return result;
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