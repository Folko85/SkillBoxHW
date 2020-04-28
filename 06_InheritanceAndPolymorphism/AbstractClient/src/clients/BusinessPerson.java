package clients;

import java.math.BigDecimal;

public class BusinessPerson extends Person {    // сделаем индивидуального предпринимателя наследником физического лица, потому что "так можно"

    private final double LITTLE_COMMISSION = 0.005;  //нет смысла вставлять комиссию в конструктор, ведь она зависит от суммы
    private final double BIG_COMMISSION = 0.01;

    public BusinessPerson(String name) {
        super(name);
    }

    @Override
    public void deposit(BigDecimal money) {
        BigDecimal commission = this.getDepositCommission(money);  // вычисляем комиссию в зависимости от вносимой суммы
        super.deposit(money.subtract(commission));  // добавляем на баланс вносимую сумму за вычетом комисии
        System.out.println("Комиссия составила " + commission);
    }

    @Override
    public BigDecimal getDepositCommission(BigDecimal amount) {
        BigDecimal commission;
        if (amount.compareTo(BigDecimal.valueOf(1000)) < 0) {
            commission = amount.multiply(BigDecimal.valueOf(BIG_COMMISSION));
        } else {
            commission = amount.multiply(BigDecimal.valueOf(LITTLE_COMMISSION));
        }
        return commission;
    }
}
