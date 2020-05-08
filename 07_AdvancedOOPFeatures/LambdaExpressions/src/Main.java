import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    //открываю эту задачу, как модуль, поэтому изменил путь
    private static String staffFile = "./07_AdvancedOOPFeatures/LambdaExpressions/data/staff.txt";
    //private static String staffFile = "./data/staff.txt";     // Старый путь закомментировал, если будете открывать отдельно - надо будет поменять местами
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) {
        ArrayList<Employee> staff = loadStaffFromFile();    // зарплату сделаем по убыванию
        Collections.sort(staff, Comparator.comparingInt(Employee::getSalary).reversed().thenComparing(Employee::getName));
        for (Employee employee : staff) {
            System.out.println(employee);
        }
    }

    private static ArrayList<Employee> loadStaffFromFile() {
        ArrayList<Employee> staff = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for (String line : lines) {
                String[] fragments = line.split("\t");
                if (fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                        fragments[0],
                        Integer.parseInt(fragments[1]),
                        (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}