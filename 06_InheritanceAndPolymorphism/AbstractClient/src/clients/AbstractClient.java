package clients;

import java.math.BigDecimal;
import java.math.BigInteger;

abstract class AbstractClient {     // мы не создаём абстрактных объектов и не используем static-методы, делать класс публичным нет смысла.

    protected BigDecimal balance; //  начальный баланс храним в бигдецимале, чтобы обеспечить точность
    protected BigInteger accNumber;   // у нас просят число - пусть будет 20-тизначное число
    protected String ownerName;   // имя владельца. Странно создавать клиента без имени.

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal money) {
        this.balance = this.balance.add(money);
        System.out.println("Клиент " + this.ownerName + " внёс на счёт " + money);
    }

    public void withdraw(BigDecimal money) {
        if (this.balance.compareTo(money) >= 0 ) {
            this.balance = this.balance.subtract(money);
            System.out.println("Клиент " + this.ownerName + " успешно снял со счёта сумму " + money);
        } else {
            System.out.println("На счёте недостаточно денег");
        }
    }
       // абстрактные методы
    public abstract BigDecimal getWithdrawCommission(BigDecimal amount);

    public abstract BigDecimal getDepositCommission(BigDecimal amount);

    @Override
    public String toString()      // переопределим toString, чтобы sout печатало инфу о клиенте, а не хэш-код
    {
        return "Клиент " + this.ownerName + " владеет деньгами в размере " + this.balance + ", находящимися на счёте " + this.accNumber;
    }
}
