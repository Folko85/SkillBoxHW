package accaunts;

import java.time.LocalDate;

public class DepositBankAccount extends BankAccount {
    LocalDate lastDepositDate;                     // в депозитарном счёте храним также дату последнего пополнения

    public DepositBankAccount(String name) {
        super(name);
    }

    public DepositBankAccount(String name, double startBalance) {
        super(name);
        this.balance = startBalance;
        lastDepositDate = LocalDate.now().minusWeeks(5);    // можно сразу снять деньги со счёта. Так как установлена дата пополнения из прошлого.
    }                                                      // Это лишь для проверки, конечно.

    @Override
    public void deposit(double money)       // фиксируем дату пополнения, когда кладём деньги
    {
        super.deposit(money);
        this.lastDepositDate = LocalDate.now();
    }

    @Override
    public void withdraw(double money) {
        if (this.lastDepositDate != null && LocalDate.now().isAfter(this.lastDepositDate.plusMonths(1))) // проверяем, пополняли ли счёт и когда пополняли.
        {
            super.withdraw(money);
        } else {
            System.out.print("Снять деньги со счёта можно только через месяц после последнего пополнения. ");
            if (getLastDepositDate() == null) {
                System.out.println("Вы пока ещё не пополняли счёт.");
            } else {
                System.out.println("Дата последнего пополнения счёта " + getLastDepositDate());
            }
        }
    }

    private LocalDate getLastDepositDate()  // раз уж нас привязывают ко времени пополнения, то надо иметь возможность проверить это время
    {
        return lastDepositDate;
    }
}
