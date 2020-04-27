package clients;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class Company extends AbstractClient {
    private final double COMMISSION = 0.01;
    private BigInteger inn;

    public Company (String title, BigInteger inn)    // раз уж это компания, то надо указывать ИНН
    {
        this.balance = 0.0;
        this.ownerName = title;             // в классе не делаем никаких проверок ввода, это будет в основной программе (если будет)
        this.inn = inn;
        StringBuilder accNumbers = new StringBuilder();  // идея ругается(warning) на конструкцию String += string. Воспользуемся изменяемым объектом.
        for (int i = 0; i < 20; i++) {
            accNumbers.append(ThreadLocalRandom.current().nextInt(0, 10));  // номер счёта генерируем случайным образом с помощью цифр от 0 до 9
        }
        this.accNumber = new BigInteger(accNumbers.toString());
    }

    @Override                                     //издержки демонстрации возможностей, то же самое мы уже делали в классе Person
    public void deposit(double money) {
        this.balance += money;
        System.out.println("Клиент " + this.ownerName + " внёс на счёт " + money);
    }

    @Override
    public void withdraw(double money) {
        super.withdraw(money +(money * COMMISSION));
        System.out.println("Комиссия составила: " + money * COMMISSION);
    }

    @Override
    public String toString()      // переопределим toString снова. Пусть это звучит пафоснее для юридических лиц
    {
        return "Организация " + this.ownerName + ", зарегистрированная с ИНН: " + this.inn + " владеет деньгами в размере " + this.balance + ", находящимися на счёте " + this.accNumber;
    }
}
