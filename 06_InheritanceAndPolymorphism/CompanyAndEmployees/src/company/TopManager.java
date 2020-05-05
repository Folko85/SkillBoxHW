package company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class TopManager implements Employee {

    private String name;
    private BigDecimal salary;
    private Company ownCompany;   //

    public TopManager(String name) {
        this.name = name;
        this.salary = BigDecimal.valueOf(0.0);
    }

    @Override
    public void setSalary() {
        this.salary = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(40000.0, 100000.0)); // базовый оклад
        if (ownCompany != null && ownCompany.getSuccessStatus()) { // если компания процветает (имеет доход выше 10 млн), то начальство получает бонус
            this.salary = this.salary.multiply(BigDecimal.valueOf(1.5));
        }
    }

    @Override
    public void setZeroSalary()  // этот метод используем при увольнении для назначения нулевой зарплаты
    {
        this.salary = BigDecimal.valueOf(0.0);
    }

    public void setOwnCompany (Company company)
    {
        this.ownCompany = company;  // доходы топ-менеджера зависят от прибыли компании, значит ссылка на компанию в этом классе обязательна
    }

    @Override
    public BigDecimal getMonthSalary() {
        return this.salary;
    }

    @Override
    public String toString() {
        return "Топ-менеджер " + this.name + " - " + this.salary.setScale(2, RoundingMode.HALF_DOWN);
    }
}
