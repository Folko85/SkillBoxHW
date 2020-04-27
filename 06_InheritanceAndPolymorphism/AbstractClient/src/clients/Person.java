package clients;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class Person extends AbstractClient {
    public Person(String name) {
        this.balance = 0.0;
        this.ownerName = name;             // в классе не делаем никаких проверок ввода, это будет в основной программе (если будет)
        StringBuilder accNumbers = new StringBuilder();  // идея ругается(warning) на конструкцию String += string. Воспользуемся изменяемым объектом.
        for (int i = 0; i < 20; i++) {
            accNumbers.append(ThreadLocalRandom.current().nextInt(0, 10));  // номер счёта генерируем случайным образом с помощью цифр от 0 до 9
        }
        this.accNumber = new BigInteger(accNumbers.toString());
    }

    @Override
    public void deposit(double money) {
        this.balance += money;
        System.out.println("Клиент " + this.ownerName + " внёс на счёт " + money);
    }
}
