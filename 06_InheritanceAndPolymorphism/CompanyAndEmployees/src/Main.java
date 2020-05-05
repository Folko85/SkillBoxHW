import com.github.javafaker.Faker;
import company.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("ru"));    // Пусть наш первый Faker будет создателем компании
        String creatorName = faker.name().fullName();
        String titleOfCompany = faker.company().name();

        Company firstCompany = new Company(titleOfCompany, creatorName);  // создаём компанию.

        ArrayList<Faker> candidates = new ArrayList<>();   // кандидаты (объекты класс faker, от них нам пока нужно только имя)
        ArrayList<Employee> operators = new ArrayList<>(); // будущие операторы
        ArrayList<Employee> managers = new ArrayList<>(); // будущие менеджеры
        // создадим будущих сотрудников
        for (int i = 0; i < 270; i++) {
            candidates.add(i, new Faker(new Locale("ru")));  // создаём соискателей и сразу превращаем их в специалистов
            if (i < 180) {
                operators.add(new Operator(candidates.get(i).name().fullName()));
            }
            if (i >= 180 && i < 260) {
                managers.add(new Manager(candidates.get(i).name().fullName()));
            }
            if (i >= 260) {
                firstCompany.hair(new TopManager(candidates.get(i).name().fullName())); // топ-менеджеров нанимаем поодиночке.
            }
        }
        firstCompany.hairAll(operators);    // а потом уж остальных
        firstCompany.hairAll(managers);

        if (firstCompany.getSuccessStatus()) {
            System.out.println("Компания " + firstCompany.getCompanyTitle() + " преуспевает. Доход " + firstCompany.getIncome()+ "\n");
        } else {
            System.out.println("Компания " + firstCompany.getCompanyTitle() + " в упадке. Доход " + firstCompany.getIncome() + "\n");
        }

        List<Employee> topStaff = firstCompany.getTopSalaryStaff(15);
        System.out.println("Самые высокооплачиваемые сотрудники: ");
        for (Employee staff : topStaff) {
            System.out.println(staff);
        }
        System.out.println("Самые низкооплачиваемые сотрудники: ");
        List<Employee> bottomStaff = firstCompany.getLowestSalaryStaff(30);

        for (Employee otherStaff : bottomStaff) {
            System.out.println(otherStaff);
        }
        System.out.print("\n"); // для более удобного чтения результата
        int startStaffCount = firstCompany.getEmployeeCount();
        int endStaffCount = firstCompany.getEmployeeCount() / 2;
        System.out.println("Теперь уволим половину сотрудников.");
        for (int j = startStaffCount; j > endStaffCount; j--) {
            firstCompany.fair(firstCompany.getAllStaff().get(ThreadLocalRandom.current().nextInt(0, j - 1)));  //увольняем случайного сотрудника из списка
        }
        System.out.println("Осталось работать сотрудников: " + firstCompany.getEmployeeCount() + "\n");

        if (firstCompany.getSuccessStatus()) {
            System.out.println("Компания " + firstCompany.getCompanyTitle() + " преуспевает. Доход " + firstCompany.getIncome());
        } else {
            System.out.println("Компания " + firstCompany.getCompanyTitle() + " в упадке. Доход " + firstCompany.getIncome());
        }

        List<Employee> newTopStaff = firstCompany.getTopSalaryStaff(15);
        System.out.println("Самые высокооплачиваемые сотрудники: ");
        for (Employee staff : newTopStaff) {
            System.out.println(staff);
        }
        System.out.println("Самые низкооплачиваемые сотрудники: ");
        List<Employee> newBottomStaff = firstCompany.getLowestSalaryStaff(30);

        for (Employee otherStaff : newBottomStaff) {
            System.out.println(otherStaff);
        }
    }
}

