package company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Company {

    private ArrayList<Employee> allSlavesOfCompany = new ArrayList<>();    // все сотрудники компании будут находиться в общем списке с символичным названием
    private String companyTitle;                       // у любой компании есть название
    private String creator;                           // каждая компания кому-то принадлежит

    public Company(String companyTitle, String companyCreator) {
        this.companyTitle = companyTitle;
        this.creator = companyCreator;
    }

    public void hair(Employee slave) {
        allSlavesOfCompany.add(slave); // добавляем сотрудника в общий список
        if (slave instanceof TopManager) {     // если сотрудник - топ-менеджер, то ему добавляется ссылка на нашу компанию
            ((TopManager) slave).setOwnCompany(this);
        }
        slave.setSalary();   // только после этого назначается зарплата
        if (slave instanceof Manager) // если сотрудник менеджер, то пересчитываем бонус топ-менеджеров, так как изменится доход компании
        {
            for (Employee employee : allSlavesOfCompany) {
                if (employee instanceof TopManager)
                    ((TopManager) employee).recalculationBonus(this.getIncome()); // передаём в метод пересчёта бонуса текущий доход.
            }
        }
    }

    public void hairAll(ArrayList<Employee> approvedSlaves) {
        for (Employee slave : approvedSlaves) {
            this.hair(slave);          // просто многократно вызываем метод найма
        }
    }

    public void fair(Employee slave) {
        if (allSlavesOfCompany.remove(slave)) {   // вот это удивило, но проверил документацию  - метод действительно может возвращать boolean
            slave.setZeroSalary();             // обнуляем его зарплату
        } else {
            System.out.println("Такой сотрудник здесь не работает");
        }
        if (slave instanceof Manager) // если сотрудник менеджер, то пересчитываем зарплату топ-менеджеров, так как изменится доход компании
        {
            for (Employee employee : allSlavesOfCompany) {
                if (employee instanceof TopManager)
                    ((TopManager) employee).recalculationBonus(this.getIncome());  // передаём в метод пересчёта бонуса текущий доход.
            }
        }
    }

    public BigDecimal getIncome() {
        BigDecimal income = BigDecimal.valueOf(0.0);
        for (Employee slave : allSlavesOfCompany) {
            if (slave instanceof Manager) {
                income = income.add(((Manager) slave).getProfit());// если сотрудник менеджер, то он генерирует доход, сумма коих есть общий доход компании
            }
        }
        return income.setScale(2, RoundingMode.HALF_DOWN);
    }

    public boolean getSuccessStatus() {              // отдельный метод, чтобы сильно не загромождать код. Используется для проверки бонуса топ-менеджерам
        return this.getIncome().compareTo(BigDecimal.valueOf(10000000.0)) >= 0;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public int getEmployeeCount() {               // чтобы проверять, сколько ещё уволить))
        return allSlavesOfCompany.size();
    }

    public List<Employee> getAllStaff() {
        return allSlavesOfCompany;                //чтобы разобраться, кого увольнять, надо видеть весь список сотрудников
    }

    public List<Employee> getTopSalaryStaff(int count) {
        Collections.sort(allSlavesOfCompany, Collections.<Employee>reverseOrder()); // сортируем по убыванию
        return allSlavesOfCompany.subList(0, count);
    }

    public List<Employee> getLowestSalaryStaff(int count) {
        Collections.sort(allSlavesOfCompany);                              // по возрастанию метод коллекции сортируются по дефолту
        return allSlavesOfCompany.subList(0, count);
    }
}
