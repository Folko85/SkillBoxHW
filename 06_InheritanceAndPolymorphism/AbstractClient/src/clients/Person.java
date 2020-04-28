package clients;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class Person extends AbstractClient {
    public Person(String name) {
        this.balance = BigDecimal.valueOf(0.0);
        this.ownerName = name;             // в классе не делаем никаких проверок ввода, это будет в основной программе (если будет)
        StringBuilder accNumbers = new StringBuilder();  // идея ругается(warning) на конструкцию String += string. Воспользуемся изменяемым объектом.
        for (int i = 0; i < 20; i++) {
            accNumbers.append(ThreadLocalRandom.current().nextInt(0, 10));  // номер счёта генерируем случайным образом с помощью цифр от 0 до 9
        }
        this.accNumber = new BigInteger(accNumbers.toString());
    }

    @Override                                                  // у физического лица комиссии нулевые
    public BigDecimal getWithdrawCommission(BigDecimal amount) {
        return BigDecimal.valueOf(0.0);
    }

    @Override
    public BigDecimal getDepositCommission(BigDecimal amount) {
        return BigDecimal.valueOf(0.0);
    }
}
