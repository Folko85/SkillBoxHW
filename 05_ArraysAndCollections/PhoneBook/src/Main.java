import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Pattern phoneText = Pattern.compile("(\\s*)?(\\+)?([- ()]?\\d+[- ()]?){10,14}(\\s*)?"); //используем регулярку для телефона с проверкой возможно вводимого мусора
    private static Pattern nameText = Pattern.compile("([a-zA-Zа-яА-ЯёЁ\\s\\-]+)");            //оставим для имени буквы, пробел с дефисом, цифры запретим, чтоб не путаться
    private static HashMap<String, String> phoneBook = new HashMap<>();               // два зеркальных хешмапа - залог простоты кода.
    private static HashMap<String, String> contactBook = new HashMap<>();
    // поскольку приходится часто возвращаться к началу цикла или прерывать цикл, то введём несколько флагов типа boolean, которые будут регулировать это
    private static boolean isPhoneBad = false;
    private static boolean isPhoneExist = false;
    private static boolean isNameExist = false;

    public static void main(String[] args) throws IOException {
        System.out.println("Введите данные контакта или команду:");
        Scanner scanner = new Scanner(System.in);
        String phoneNumber = new String();
        String contactName = new String();
        boolean workIsActive = true;
        while (workIsActive) {
            String inputOne = scanner.nextLine();
            Matcher matcherText = nameText.matcher(inputOne);
            Matcher matcherPhone = phoneText.matcher(inputOne);
            if (matcherText.find() || matcherPhone.find()) {             // введённое должно подходить хотя бы под одну регулярку
                if (isCommand(inputOne)) {                             //а теперь уже сортируем
                    switch (matcherText.group()) {
                        case ("LIST"):
                            list();
                            break;
                        case ("HELP"):                                   // добавим кучу лишних функций из прошлого проекта для солидности
                            help();
                            break;
                        case ("SAVE"):
                            save();
                            break;
                        case ("LOAD"):
                            load();
                            break;
                        case ("EXIT"):
                            workIsActive = false;
                            System.out.println("До свидания!!!");
                            break;
                        default:
                            System.out.println("Вы ввели недопустимую команду. Введите другую команду." +
                                    " Чтобы познакомится со списком команд введите HELP.");              //лучше двойная страховка, чем никакой.
                            break;
                    }
                    continue;
                } else if (isPhone(inputOne))                       //сперва проверяем, ввёл ли пользователь номер телефона
                {
                    phoneNumber = normalize(matcherPhone.group());                // все номера приводим к единому формату
                    System.out.println(checkPhone(phoneNumber));
                    if (isPhoneBad || isPhoneExist) {
                        continue;                            // если номер некорректен или уже есть, то повторяем цикл
                    }
                    System.out.println("Теперь введите имя контакта:");
                    boolean isNameEntering = true;
                    while (isNameEntering) {
                        String inputTwo = scanner.nextLine();
                        Matcher matcherName = nameText.matcher(inputTwo);
                        if (matcherName.find()) {
                            if (isName(inputTwo)) {
                                contactName = matcherName.group();
                                System.out.println(checkName(contactName));
                                if (isNameExist) {
                                    continue;                                // если имя существует, просим ввести другое
                                }
                                phoneBook.put(phoneNumber, contactName);      // возможно это преждевременная оптимизация, но нафига городить сложные схемы,
                                contactBook.put(contactName, phoneNumber);    //  если можно просто создать два ассоциативных массива и не париться.
                                System.out.println("Контакт сохранён в телефонной книге. Введите следующий контакт или команду.");
                                isNameEntering = false;                // если имя введено корректно, то покидаем вложенный цикл
                            }
                        } else {
                            System.out.println("Вы ввели что-то не то. Имя контакта может оодержать только буквы, дефис и пробел. Попробуйте снова:");
                        }
                    }
                } else if (isName(inputOne)) {
                    contactName = matcherText.group();
                    System.out.println(checkName(contactName));
                    if (isNameExist) {
                        continue;                                // если имя существует, просим ввести другое
                    }
                    System.out.println("Введите номер телефона:");
                    boolean isPhoneEntering = true;
                    while (isPhoneEntering) {
                        String inputTwoAlter = scanner.nextLine();
                        if (isPhone(inputTwoAlter)) {
                            phoneNumber = normalize(inputTwoAlter);
                            System.out.println(checkPhone(phoneNumber));
                            if (isPhoneBad || isPhoneExist) {
                                continue;                            // если номер некорректен или уже есть, то повторяем цикл
                            }
                            phoneBook.put(phoneNumber, contactName);
                            contactBook.put(contactName, phoneNumber);
                            System.out.println("Контакт сохранён в телефонной книге. Введите следующий контакт или команду.");
                            isPhoneEntering = false;
                        } else {
                            System.out.println("Вы ввели что-то не то. Номер телефона может содержать только цифры, скобочки, дефисы и знак плюс. Попробуйте снова:");
                        }
                    }
                }
            } else {
                System.out.println("Что вы ввели? Это не имя, не номер и не команда. Не надо так!!! Попробуйте снова:");
            }
        }
    }

    public static boolean isPhone(String input)   // метод проверяет, является ли строка номером телефона
    {
        Matcher phoneMatcher = phoneText.matcher(input);
        if (phoneMatcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isName(String input)   // метод проверяет, является ли строка корректным именем
    {
        Matcher nameMatcher = nameText.matcher(input);
        if (nameMatcher.find() && !nameMatcher.group().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCommand(String input)   // метод проверяет, является ли строка командой.
    {
        if (input != null && (input.equals("LIST") || input.equals("EXIT") || input.equals("SAVE") || input.equals("LOAD") || input.equals("HELP"))) {
            return true;
        } else {
            return false;
        }
    }

    public static String normalize(String phone) {
        String normalNumber = "";
        for (int i = 0; i < phone.length(); i++)      //вычленяем цифры и + из введённого номера
        {
            if (phone.charAt(i) == '+' || (phone.charAt(i) >= 48 && phone.charAt(i) <= 57)) {
                normalNumber += String.valueOf(phone.charAt(i));
            }
        }
        if (normalNumber.charAt(0) == '8') {
            normalNumber = "+7" + normalNumber.substring(1);   // заменяем восьмёрку на +7
        }
        if (normalNumber.charAt(0) != '+') {
            normalNumber = "+" + normalNumber;    // если забыли ввести плюс - добавляем
        }
        return normalNumber;    // выходной формат максимально простой. Только + и цифры.
    }

    public static String checkPhone(String phone) {
        if (phone.length() != 12)   // отсекаем все некорректные номера. Если ты из-за рубежа - купи русскую симку.
        {
            isPhoneBad = true;
            return "Номер телефона должен состоять из знака + и 11 цифр, введите корректный номер телефона";
        } else if (phoneBook.containsKey(phone)) {        //проверяем, есть ли номер в базе
            isPhoneBad = false;
            isPhoneExist = true;
            String contact = phoneBook.get(phone);
            String number = contactBook.get(contact);
            return "Номер " + number + " уже есть в базе и принадлежит контакту " + contact + ". Повторите ввод:";
        } else {
            isPhoneBad = false;
            isPhoneExist = false;
            return "Вы ввели номер телефона.";
        }
    }

    public static String checkName(String contact)           // оставим пользователю небольшую свободу именования. Проверяем лишь дубли
    {
        if (contactBook.containsKey(contact)) {
            isNameExist = true;
            String number = contactBook.get(contact);
            String name = phoneBook.get(number);
            return "Имя " + name + " уже есть в базе и соответствует номеру " + number + ". Повторите ввод:";
        } else {
            isNameExist = false;
            return "Вы ввели имя контакта.";
        }
    }

    public static void list() {                 //проще всего вывести список с помощью стрима
        if (phoneBook.isEmpty())
            System.out.println("Вы не добавили контактов. Введите имя или номер телефона, которые хотите добавить.");
        else {
            phoneBook.entrySet().stream()
                    .sorted(HashMap.Entry.comparingByValue())
                    .forEach(e -> System.out.println(e.getValue() + " : " + e.getKey()));
            System.out.println("Введите данные контакта или команду:");
        }
    }

    public static void help() {
        System.out.println("Список возможных команд:");
        System.out.println("LIST  /Выводит список всех введённых адресов");
        System.out.println("HELP  /Выводит примеры возможных команд");
        System.out.println("SAVE  /Сохраняет наши адреса в файл save.txt");
        System.out.println("LOAD  /Загружает наши адреса из файла save.txt");
        System.out.println("EXIT  /Завершает программу");
        System.out.println("Чтобы добавить контакт, просто введите имя или номер телефона");
    }

    public static void save() {
        if (!phoneBook.isEmpty()) {
            try (FileWriter saveMap = new FileWriter("save.txt", false)) {
                for (HashMap.Entry<String, String> entry : phoneBook.entrySet()) {
                    saveMap.write(entry.getKey() + "=" + entry.getValue() + "\r\n");  // выбираем хороший разделитель. Нам это потом ещё читать
                }
                saveMap.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Список контактов сохранён в файл \"save.txt\".");
        } else {
            System.out.println("Вы не ввели контактов, которые могли бы сохранить");
        }
    }

    public static void load() throws IOException {
        File saveFile = new File("save.txt");
        if (saveFile.exists() && saveFile.isFile() && saveFile.length() != 0)            //проверяем, есть ли что загружать
        {
            FileReader loadMap = new FileReader(saveFile);
            Scanner readFile = new Scanner(loadMap);
            phoneBook.clear();
            contactBook.clear();

            while (readFile.hasNextLine()) {
                String[] readLine = readFile.nextLine().split("=");  // как разделяли, так и возвращаем
                phoneBook.put(readLine[0], readLine[1]);
                contactBook.put(readLine[1], readLine[0]);
            }
            loadMap.close();
            readFile.close();
            System.out.println("Список контактов загружен");
        } else {
            System.out.println("Нет информации для загрузки");
        }
    }
}
