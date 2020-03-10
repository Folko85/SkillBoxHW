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
        String[] parts = text.split(",");   // разделяем предложение на части
        int sumOfSalary = 0;          /// сюда будем складывать деньги
        String salary = "";
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].indexOf("Вася") >= 0 || parts[i].indexOf("Маша") >= 0)  //Находим часть строки, повествующую о героях
            {
                for (int j = 0; j < parts[i].length(); j++) {
                    if (Character.isDigit(parts[i].charAt(j))) {
                        salary += parts[i].charAt(j);  // и извлекаем оттуда оклад
                    } else {                         // цифры закончились
                        if (!salary.isEmpty())       // если мы смогли что-то вычленить,
                        {
                            sumOfSalary += Integer.parseInt(salary); // прибавляем к общей сумме
                            salary = "";         // и обнуляем
                        }
                    }
                }
            }
        }
        System.out.println(salary);
        System.out.println("Сумма окладов Васи и Маши: " + sumOfSalary);
        System.out.println("Введите ФИО:");
        Scanner fio = new Scanner(System.in);

        String yourName = fio.nextLine().trim();           //  уберём лишние пробелы по краям
        String clearName = clearName(yourName);            // уберём мусор
        String checkName = checkName(clearName);           //проверим корректность введённой информации
        System.out.println(checkName);                    // выведем на экран
    }

    public static String clearName(String name)   //очищаем строку от случайно введённых знаков
    {
        while (name.indexOf("  ") >= 0) {
            name = name.replace("  ", " ");//  избавимся от задвоенных пробелов в центре
        }

        while (name.indexOf(" -") >= 0) {
            name = name.replace(" -", " ");//  избавимся от дефисов в начале имени
        }
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