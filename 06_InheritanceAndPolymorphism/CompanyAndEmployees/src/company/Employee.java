package company;

import java.math.BigDecimal;

public interface Employee {

    BigDecimal getMonthSalary();
    void setSalary();
    void setZeroSalary();     // это общие методы для всех сотрудников, находящихся в едином списке

}
