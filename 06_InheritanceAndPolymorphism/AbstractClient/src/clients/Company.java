package clients;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class Company extends AbstractClient {
    private final double COMMISSION_PERCENT = 0.01;
    private BigInteger inn;

    public Company (String title, BigInteger inn)    // раз уж это компания, то надо указывать ИНН
    {
        this.balance = BigDecimal.valueOf(0.0);
        this.ownerName = title;             // в классе не делаем никаких проверок ввода, это будет в основной программе (если будет)
        this.inn = inn;
        StringBuilder accNumbers = new StringBuilder();  // идея ругается(warning) на конструкцию String += string. Воспользуемся изменяемым объектом.
        for (int i = 0; i < 20; i++) {
            accNumbers.append(ThreadLocalRandom.current().nextInt(0, 10));  // номер счёта генерируем случайным образом с помощью цифр от 0 до 9
        }
        this.accNumber = new BigInteger(accNumbers.toString());
    }

    @Override
    public void withdraw(BigDecimal money) {
        BigDecimal commission = this.getWithdrawCommission(money);
        super.withdraw(money.add(commission));
        System.out.println("Выдано денег: " + money + ". Комиссия за снятие " + commission);
    }

    @Override
    public BigDecimal getWithdrawCommission(BigDecimal amount) {
        BigDecimal result = amount.multiply(BigDecimal.valueOf(COMMISSION_PERCENT));
        return result;
    }

    @Override
    public BigDecimal getDepositCommission(BigDecimal amount) {
        return BigDecimal.valueOf(0.0);
    }

    @Override
    public String toString()      // переопределим toString снова. Пусть это звучит пафоснее для юридических лиц
    {
        return "Организация " + this.ownerName + ", зарегистрированная с ИНН: " + this.inn + " владеет деньгами в размере " + this.balance + ", находящимися на счёте " + this.accNumber;
    }
}
