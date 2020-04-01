import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern manageEmail = Pattern.compile("^([A-Z]+)(\\s+[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6})?$", Pattern.CASE_INSENSITIVE); //берём первую попавшуюся регулярку для e-mail, ибо совершенству нет предела.
    private static HashSet<String> emailSet = new HashSet<>();   // здесь будем хранить email-адреса

    public static void main(String[] args) throws IOException {

        System.out.println("Добро пожаловать в приложение \"Хранилище адресов электронной почты\". Введите необходимую команду." +
                " Чтобы познакомится со списком команд введите HELP:");
        Scanner scanner = new Scanner(System.in);
        boolean workIsActive = true;
        while (workIsActive) {
            String command = scanner.nextLine();
            Matcher matcherEmail = manageEmail.matcher(command);
            if (matcherEmail.find()) {
                String commandType = matcherEmail.group(1).toUpperCase();       // а ещё наш новый код будет регистронезависимым. Долой тиранию заглавных букв
                String emailText = matcherEmail.group(2);
                switch (commandType) {
                    case ("LIST"):
                        list();
                        break;
                    case ("ADD"):
                        add(emailText);
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
            } else {
                System.out.println("Вы ввели недопустимую команду. Введите другую команду." +
                        " Чтобы познакомится со списком команд введите HELP.");
            }
        }
    }

    public static void list() {
        if (emailSet.size() > 0)
            for (String email : emailSet) {        // тут только итератором
                System.out.println(email);
            }
        else {
            System.out.println("Вы не добавили адресов e-mail. Чтобы добавить адрес введите \"ADD email@company.com\".");
        }
    }

    public static void add(String email) {
        if (email != null)  // проверяем, введён ли email
        {
            if (emailSet.contains(email.trim())) {
                System.out.println("Вы уже вводили этот адрес электронной почты. Не надо так делать!!!");
            } else {
                emailSet.add(email.trim());
                System.out.println("Адрес электронной почты добавлен");
            }
        } else {
            System.out.println("Вы не ввели корректного адреса e-mail");
        }
    }

    public static void help() {
        System.out.println("Список возможных команд:");
        System.out.println("LIST  /Выводит список всех введённых адресов");
        System.out.println("HELP  /Выводит примеры возможных команд");
        System.out.println("SAVE  /Сохраняет наши адреса в файл save.txt");
        System.out.println("LOAD  /Загружает наши адреса из файла save.txt");
        System.out.println("EXIT  /Завершает программу");
        System.out.println("ADD email@company.com /Добавляет email@company.com в перечень");
    }

    public static void save() {
        if (emailSet.size() > 0) {
            try (FileWriter saveSet = new FileWriter("save.txt", false)) {
                for (String email : emailSet) {
                    saveSet.write(email + "\r\n");
                }
                saveSet.close();
                System.out.println("Список e-mail адресов сохранён в файл \"save.txt\".");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Вы не ввели адресов, которые могли бы сохранить");
        }
    }

    public static void load() throws IOException {
        File saveFile = new File("save.txt");
        if (saveFile.exists() && saveFile.isFile() && saveFile.length() != 0)            //проверяем, есть ли что загружать
        {
            FileReader loadSet = new FileReader(saveFile);
            Scanner readFile = new Scanner(loadSet);
            emailSet.clear();
            while (readFile.hasNextLine()) {
                emailSet.add(readFile.nextLine());
            }
            loadSet.close();
            readFile.close();
            System.out.println("Список e-mail адресов загружен");
        } else {
            System.out.println("Нет информации для загрузки");
        }
    }
}
