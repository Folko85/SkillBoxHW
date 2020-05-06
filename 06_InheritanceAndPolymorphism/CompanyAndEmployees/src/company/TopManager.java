package company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class TopManager implements Employee {

    private String name;
    private BigDecimal baseSalary;
    private BigDecimal bonus;  // выведем бонус отдельно, ведь при изменении дохода компании по-хорошему только он и должен меняться
    private Company ownCompany;

    public TopManager(String name) {
        this.name = name;
        this.baseSalary = BigDecimal.valueOf(0.0);
        this.bonus = BigDecimal.valueOf(0.0);
    }

    @Override
    public void setSalary() {
        this.baseSalary = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(40000.0, 100000.0)); // базовый оклад
        this.recalculationBonus(this.ownCompany.getIncome()); // при назначении зарплаты тоже пересчитываем бонус
    }

    public void recalculationBonus(BigDecimal income) {
        if (income.compareTo(BigDecimal.valueOf(10000000)) >= 0) {
            this.bonus = this.baseSalary.multiply(BigDecimal.valueOf(1.5));
        } else this.bonus = BigDecimal.valueOf(0.0);
    }

    @Override
    public void setZeroSalary()  // этот метод используем при увольнении для назначения нулевой зарплаты
    {
        this.baseSalary = BigDecimal.valueOf(0.0);
        this.bonus = BigDecimal.valueOf(0.0);
    }

    public void setOwnCompany(Company company) {
        this.ownCompany = company;  // доходы топ-менеджера зависят от прибыли компании, значит ссылка на компанию в этом классе обязательна
    }

    @Override
    public BigDecimal getMonthSalary() {
        return this.baseSalary.add(this.bonus);
    }  // зарплата равна оклад + бонус

    @Override
    public String toString() {
        return "Топ-менеджер " + this.name + " - " + this.getMonthSalary().setScale(2, RoundingMode.HALF_DOWN);
    }
}
