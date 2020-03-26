import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Добро пожаловать в приложение \"Список дел\". Введите необходимую команду." +
                " Чтобы познакомится со списком команд введите HELP:");
        Scanner scanner = new Scanner(System.in);
        boolean workIsActive = true;
        String command = new String();  // создаём пустую строку
        ArrayList<String> toDoList = new ArrayList<>();   // создаём пустой список дел
        Pattern manageTasks = Pattern.compile("([A-Z]+)(\\s+\\d+)?\\s*([а-яА-ЯёЁ\\w\\s.?!)(,:-]+)?"); // первое слово - команда (заглавной латинницей), потом число или ничего
        Matcher matcherTasks;                                                                         // дальше пробел и произвольный текст (дело) или опять же ничего
        while (workIsActive) {
            command = scanner.nextLine();
            matcherTasks = manageTasks.matcher(command);
            if (matcherTasks.find()) {
                switch (matcherTasks.group(1)) {
                    case ("LIST"):
                        if (toDoList.size() > 0)
                            for (int i = 0; i < toDoList.size(); i++) {
                                System.out.println((i + 1) + ": " + toDoList.get(i));
                            }
                        else {
                            System.out.println("Вы не запланировали никаких дел. Чтобы добавить задачу введите \"ADD Какое-то дело\".");
                        }
                        break;
                    case ("ADD"):
                        if (matcherTasks.group(3) != null)  // проверяем, введён ли текст задачи
                        {
                            if (matcherTasks.group(2) != null && Integer.parseInt(matcherTasks.group(2).trim()) > 0 && Integer.parseInt(matcherTasks.group(2).trim()) <= toDoList.size()) //проверяем, введен ли номер задачи
                            {
                                toDoList.add(Integer.parseInt(matcherTasks.group(2).trim()) - 1, matcherTasks.group(3));  // пользователь начинает счёт с единицы, а не с нуля
                            } else {
                                toDoList.add(matcherTasks.group(3));  // а если кто-то считает иначе, то ему место в конце списка
                            }
                            System.out.println("Дело добавлено в список");
                        } else if (matcherTasks.group(3) == null) {
                            System.out.println("Вы не ввели описание запланированного дела");
                        }
                        ;
                        break;
                    case ("DELETE"):
                        if (matcherTasks.group(3) == null && matcherTasks.group(2) != null) {  // если указан номер задачи и нет дополнительного текста
                            if (Integer.parseInt(matcherTasks.group(2).trim()) > 0 && Integer.parseInt(matcherTasks.group(2).trim()) <= toDoList.size()) {  // если номер корректен
                                toDoList.remove(Integer.parseInt(matcherTasks.group(2).trim()) - 1);          // ведём счёт с единицы
                                System.out.println("Задача под номером " + Integer.parseInt(matcherTasks.group(2).trim()) + " успешно удалена.");
                            } else {
                                System.out.println("В списке дел отсутствует задача под номером " + Integer.parseInt(matcherTasks.group(2).trim()));
                            }
                        } else {
                            System.out.println("Команда некорректна. Чтобы познакомиться с правильмым написанием команд введите HELP:");
                        }
                        break;
                    case ("EDIT"):
                        if (matcherTasks.group(3) != null && matcherTasks.group(2) != null)    // если указан номер записи и заменяющий текст
                        {
                            if (Integer.parseInt(matcherTasks.group(2).trim()) > 0 && Integer.parseInt(matcherTasks.group(2).trim()) <= toDoList.size()) {    // проверяем корректность номера
                                toDoList.remove(Integer.parseInt(matcherTasks.group(2).trim()) - 1);
                                toDoList.add(Integer.parseInt(matcherTasks.group(2).trim()) - 1, matcherTasks.group(3));
                                System.out.println("Вы успешно изменили задачу под номером " + Integer.parseInt(matcherTasks.group(2).trim()));
                            } else {
                                System.out.println("В списке дел отсутствует задача под номером " + Integer.parseInt(matcherTasks.group(2).trim()));
                            }
                        } else {
                            System.out.println("Команда некорректна. Чтобы познакомиться с правильмым написанием команд введите HELP:");
                        }
                        break;
                    case ("HELP"):
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
                        break;
                    case ("SAVE"):                                      // Не для того мы дела придумывали, чтоб их забывать
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
                        break;
                    case ("LOAD"):
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
}