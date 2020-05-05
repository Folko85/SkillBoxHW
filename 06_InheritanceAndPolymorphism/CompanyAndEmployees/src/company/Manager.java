package company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Manager implements Employee {

    private String name;
    private BigDecimal salary;
    private BigDecimal profit;   // отличительная особенность менеджера - он приносит прибыль, от которой и считается зарплата
    private BigDecimal bonus;

    public Manager (String name)
    {
        this.name = name;
        this.profit = BigDecimal.valueOf(0.0); //пока менеджер не нанят, он ничего не продаёт
        this.bonus = BigDecimal.valueOf(0.0);
        this.salary = BigDecimal.valueOf(0.0); //
    }

    @Override
    public void setSalary() {
        this.profit = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, 500000.0)); //все эти бонусы определяются при найме
        this.bonus = profit.multiply(BigDecimal.valueOf(0.05));
        this.salary = BigDecimal.valueOf(25000.0).add(bonus); // суммарная зарплата равна оклад + премия
    }

    @Override
    public void setZeroSalary()  // этот метод используем при увольнении для назначения нулевой зарплаты
    {
        this.profit = BigDecimal.valueOf(0.0); //уволенный менеджер тоже ничего не продаёт
        this.bonus = BigDecimal.valueOf(0.0); // и не получает за это процент к зарплате
        this.salary = BigDecimal.valueOf(0.0);
    }

    @Override
    public BigDecimal getMonthSalary() {
        return this.salary;
    }

    public BigDecimal getProfit()
    {
        return this.profit;
    }

    @Override
    public String toString()
    {
        return "Менеджер " + this.name + " - " + this.salary.setScale(2, RoundingMode.HALF_DOWN);
    }
}
