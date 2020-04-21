package accaunts;

public class CardBankAccount extends BankAccount {
    private double withdrawPercent;           //в карточном счёте храним процент за снятие

    public CardBankAccount(String name) {
        super(name);
        this.withdrawPercent = 1.0;                 // процент по умолчанию
    }

    public CardBankAccount(String name, double startBalance, double percent) {
        super(name);
        this.withdrawPercent = percent;                  // предположим у нас овердрафтовая карта и процент зависит от начального остатка
        this.balance = startBalance;                     // вполне реалистичный вариант
    }

// если метод не меняется, то нет смысла и переопределять его
    @Override
    public void withdraw(double money) {
        double commission = money * this.withdrawPercent / 100;
        super.withdraw(money + commission);
    }
}
