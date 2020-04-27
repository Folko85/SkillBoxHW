package clients;

import java.math.BigInteger;

abstract class AbstractClient {     // мы не создаём абстрактных объектов и не используем static-методы, делать класс публичным нет смысла.

    protected double balance; //  начальный баланс
    protected BigInteger accNumber;   // у нас просят число - пусть будет 20-тизначное число
    protected String ownerName;   // имя владельца. Странно создавать клиента без имени.

    public double getBalance() {
        return balance;
    }


    // сделаем один метод абстрактным, иначе асбтрактный клиент слишком похож на физическое лицо, хоть это и не оптимальный вариант
    public abstract void deposit(double money);

    public void withdraw(double money) {
        if (this.balance >= money) {
            this.balance -= money;
            System.out.println("Клиент " + this.ownerName + " успешно снял со счёта сумму " + money);
        } else {
            System.out.println("На счёте недостаточно денег");
        }
    }

    @Override
    public String toString()      // переопределим toString, чтобы sout печатало инфу о клиенте, а не хэш-код
    {
        return "Клиент " + this.ownerName + " владеет деньгами в размере " + this.balance + ", находящимися на счёте " + this.accNumber;
    }
}
