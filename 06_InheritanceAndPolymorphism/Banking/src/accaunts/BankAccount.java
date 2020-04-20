package accaunts;

import java.time.LocalDate;

public class BankAccount {                // всё делаем приватным, кроме трёх методов
    protected double balance; //  начальный баланс будет защищенным, чтобы можно было изменить начальное значение в контрукторе класса-наследника
    private LocalDate createDate;          // дата создания счёта
    private String accNumber;   // номер счёта
    private String ownerName;   // имя владельца

    public BankAccount(String name) {   // у счёта всяко должен быть владелец
        this.createDate = LocalDate.now();
        this.balance = 0.0;
        this.ownerName = name;             // в классе не делаем никаких проверок ввода, это будет в основной программе (если будет)
        this.accNumber = "";
        for (int i = 0; i < 20; i++) {
            this.accNumber += (int) (Math.random() * 10);  // номер счёта генерируем случайным образом с помощью цифр от 0 до 9
        }

    }
    // здесь будет три метода (положить, снять, узнать сколько денег)

    public double getBalance() {
        return balance;
    }

    public void deposit(double money) {
        this.balance += money;
        System.out.println("Вы внесли на счёт " + money);
    }

    public void withdraw(double money) {
        if (this.balance >= money) {
            this.balance -= money;
            System.out.println("Вы успешно сняли со счёта сумму " + money);
        } else {
            System.out.println("На вашем счёте недостаточно денег");
        }
    }

    public String toString()      // переопределим toString, чтобы sout печатало инфу о счёте, а не хэш-код
    {
        return "Счёт " + this.accNumber + " принадлежит клиенту по имени " + this.ownerName;
    }
}
