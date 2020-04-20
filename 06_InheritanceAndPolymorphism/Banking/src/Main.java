import accaunts.BankAccount;
import accaunts.CardBankAccount;
import accaunts.DepositBankAccount;

public class Main {
    public static void main(String[] args) {          // Здесь будем проверять работу классов.
        BankAccount myAccount = new BankAccount("Иванов Пётр Денисович");  // проверка основного класса
        myAccount.deposit(10000.0);
        System.out.println("На вашем счёте " + myAccount.getBalance());
        myAccount.withdraw(6000.0);
        System.out.println("На вашем счёте " + myAccount.getBalance());
        System.out.println(myAccount +"\n"); // для удобства чтения оставим пустую строку

        CardBankAccount cardAccount = new CardBankAccount("Картин Иван Васильевич");  // провека класса карточного счёта
        cardAccount.deposit(10000.0);
        System.out.println("На вашем счёте " + cardAccount.getBalance());
        cardAccount.withdraw(5000.0);
        System.out.println("На вашем счёте " + cardAccount.getBalance());
        System.out.println(cardAccount +"\n"); // для удобства чтения оставим пустую строку

        CardBankAccount overdraftAccount = new CardBankAccount("Овердрафтов Василий Львович", 30000, 10);  // второй конструктор
        System.out.println("На вашем счёте " + overdraftAccount.getBalance());
        overdraftAccount.deposit(10000.0);
        System.out.println("На вашем счёте " + overdraftAccount.getBalance());
        overdraftAccount.withdraw(5000.0);
        System.out.println("На вашем счёте " + overdraftAccount.getBalance());
        System.out.println(overdraftAccount +"\n"); // для удобства чтения оставим пустую строку

        DepositBankAccount depositAccount = new DepositBankAccount("Депозитов Артём Иванович"); //проверка депозитарного счёта
        depositAccount.withdraw(5000.0);
        depositAccount.deposit(10000.0);
        System.out.println("На вашем счёте " + depositAccount.getBalance());
        depositAccount.withdraw(5000.0);
        System.out.println("На вашем счёте " + depositAccount.getBalance());
        System.out.println(depositAccount +"\n"); // для удобства чтения оставим пустую строку

        DepositBankAccount savingAccount = new DepositBankAccount("Богачёв Артём Иванович", 20000); //конструктор для проверки даты.
        System.out.println("На вашем счёте " + savingAccount.getBalance());    // счёт создан сразу с балансом и датой пополнения из прошлого
        savingAccount.withdraw(5000.0);
        System.out.println("На вашем счёте " + savingAccount.getBalance());
        System.out.println(savingAccount);
    }
}
