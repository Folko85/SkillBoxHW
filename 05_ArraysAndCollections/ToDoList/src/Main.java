import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern manageTasks = Pattern.compile("([A-Z]+)(\\s+\\d+)?\\s*(.+)?"); // плюс вместо звёздочки. Группа должна содержать хотя бы один символ, иначе матчер возвращает пустую строку, а не null
    private static ArrayList<String> toDoList = new ArrayList<>();                              // тоже выносим на уровень класса, так как постоянно к нему обращаемся

    public static void main(String[] args) throws IOException {
        System.out.println("Добро пожаловать в приложение \"Список дел\". Введите необходимую команду." +
                " Чтобы познакомится со списком команд введите HELP:");
        Scanner scanner = new Scanner(System.in);
        boolean workIsActive = true;
        while (workIsActive) {
            String command = scanner.nextLine();
            Matcher matcherTasks = manageTasks.matcher(command);
            if (matcherTasks.find()) {
                String commandType = matcherTasks.group(1);
                String indexString = matcherTasks.group(2);   // второй группы может не существовать, поэтому индекс вычисляем в методах
                String toDoElement = matcherTasks.group(3);
                switch (commandType) {
                    case ("LIST"):
                        list();
                        break;
                    case ("ADD"):
                        add(indexString, toDoElement);// обращаться к группе можно только после поиска. Объявлять же элементы в каждом кейсе нет смысла. Довольно понятные имена в описании методов
                        break;
                    case ("DELETE"):
                        delete(indexString);
                        break;
                    case ("EDIT"):
                        edit(indexString, toDoElement);
                        break;
                    case ("HELP"):
                        help();
                        break;
                    case ("SAVE"):                                      // Не для того мы дела придумывали, чтоб их забывать
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
                        " Чтобы познакомится со списком команд введите HELP:");
            }
        }
    }

    public static void list() {
        if (toDoList.size() > 0)
            for (int i = 0; i < toDoList.size(); i++) {
                System.out.println((i + 1) + ": " + toDoList.get(i));
            }
        else {
            System.out.println("Вы не запланировали никаких дел. Чтобы добавить задачу введите \"ADD Какое-то дело\".");
        }
    }

    public static void add(String indexString, String toDoElement) {
        if (toDoElement != null)  // проверяем, введён ли текст задачи
        {
            if (indexString != null && Integer.parseInt(indexString.trim()) > 0 && Integer.parseInt(indexString.trim()) <= toDoList.size()) //проверяем, введен ли номер задачи
            {
                toDoList.add(Integer.parseInt(indexString.trim()) - 1, toDoElement);  // пользователь начинает счёт с единицы, а не с нуля
            } else {
                toDoList.add(toDoElement);  // а если кто-то считает иначе, то ему место в конце списка
            }
            System.out.println("Дело добавлено в список");
        } else if (toDoElement == null) {
            System.out.println("Вы не ввели описание запланированного дела");
        }
    }

    public static void delete(String indexString) {
        if (indexString != null) {  // если указан номер задачи
            int index = Integer.parseInt(indexString.trim());         // индекса может не существовать в некоторых методах, поэтому вынести его отдельно не выйдет
            if (index > 0 && index <= toDoList.size()) {  // если номер корректен
                toDoList.remove(index - 1);          // ведём счёт с единицы
                System.out.println("Задача под номером " + index + " успешно удалена.");
            } else {
                System.out.println("В списке дел отсутствует задача под номером " + index);
            }
        } else {
            System.out.println("Команда некорректна. Чтобы познакомиться с правильмым написанием команд введите HELP:");
        }
    }

    public static void edit(String indexString, String toDoElement) {
        if (indexString != null && toDoElement != null)    // если указан номер записи и заменяющий текст
        {
            int index = Integer.parseInt(indexString.trim());
            if (index > 0 && index <= toDoList.size()) {    // проверяем корректность номера
                toDoList.set(index - 1, toDoElement);
                System.out.println("Вы успешно изменили задачу под номером " + index);
            } else {
                System.out.println("В списке дел отсутствует задача под номером " + index);
            }
        } else {
            System.out.println("Команда некорректна. Чтобы познакомиться с правильмым написанием команд введите HELP:");
        }
    }

    public static void help() {
        System.out.println("Список возможных команд:");
        System.out.println("LIST  /Выводит список всех запланированных дел");
        System.out.println("HELP  /Выводит примеры возможных команд");
        System.out.println("SAVE  /Сохраняет наши задачи в файл save.txt");
        System.out.println("LOAD  /Загружает наши задачи из файла save.txt");
        System.out.println("EXIT  /Завершает программу");
        System.out.println("ADD Что-нибудь /Добавляет задачу \"Что-нибудь\" в конец списка задач");
        System.out.println("ADD 4 Что-нибудь /Добавляет задачу \"Что-нибудь\" на 4ю позицию списка. Если её нет, то в конец списка задач");
        System.out.println("EDIT 2 Новое описание /Изменяет задачу на 2й позиции на  \"Новое описание\". Если 2й задачи не было, то выдаёт предупреждение и ничего не меняет");
        System.out.println("DELETE 6 /Удаляет задачу на 6й позиции списка. Если её не было, то выдаёт предупреждение и ничего не меняет");
    }

    public static void save() {
        if (toDoList.size() > 0) {
            try (FileWriter saveArray = new FileWriter("save.txt", false)) {
                for (int j = 0; j < toDoList.size(); j++) {
                    saveArray.write(toDoList.get(j) + "\r\n");
                }
                saveArray.close();
                System.out.println("Список дел сохранён в файл \"save.txt\".");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Вы не запланировали дел, которые могли бы сохранить");
        }
    }

    public static void load() throws IOException {
        File saveFile = new File("save.txt");
        if (saveFile.exists() && saveFile.isFile() && saveFile.length() != 0)            //проверяем, есть ли что загружать
        {
            FileReader loadArray = new FileReader(saveFile);
            Scanner readFile = new Scanner(loadArray);
            toDoList.clear();
            int k = 0;
            while (readFile.hasNextLine()) {
                toDoList.add(k, readFile.nextLine());
                k++;
            }
            loadArray.close();
            readFile.close();
            System.out.println("Список дел загружен");
        } else {
            System.out.println("Нет информации для загрузки");
        }
    }
}