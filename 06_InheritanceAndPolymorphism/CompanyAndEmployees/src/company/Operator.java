package company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Operator implements Employee {

    private String name;
    private BigDecimal salary;

    public Operator(String name)  // сотрудники для нас не бесправные юниты, у каждого есть своё имя
    {

        this.salary = BigDecimal.valueOf(0.0); // при создании объекта зарплаты нет. Зарплата присваивается при найме
        this.name = name;
    }

    /*месячный оклад каждого сотрудника будет отличаться, но не сильно ведь операторы могут
             иметь разную квалификацию (категорию, разряд), могут отработать разное время
             в конце концов есть небольшие штрафы м премии. Колебаться оклад будет в диапазоне 20 - 30 тыр */
    @Override
    public void setSalary() {
        this.salary = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(20000.0, 30000.0));
    }

    @Override
    public void setZeroSalary()  // этот метод используем при увольнении для назначения нулевой зарплаты
    {
        this.salary = BigDecimal.valueOf(0.0);
    }

    @Override
    public BigDecimal getMonthSalary() {
        return this.salary;
    }

    @Override
    public String toString() {
        return "Оператор " + this.name + " - " + this.getMonthSalary().setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public int compareTo(Employee employee) {
        return this.getMonthSalary().compareTo(employee.getMonthSalary());
    }
}
