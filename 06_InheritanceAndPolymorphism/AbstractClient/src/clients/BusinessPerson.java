package clients;

public class BusinessPerson extends Person {    // сделаем индивидуального предпринимателя наследником физического лица, потому что "так можно"

    private final double LITTLE_COMMISSION = 0.005;  //нет смысла вставлять комиссию в конструктор, ведь она зависит от суммы
    private final double BIG_COMMISSION = 0.01;

    public BusinessPerson(String name) {
        super(name);
    }

    @Override
    public void deposit(double money) {
        if (money < 1000) {
            super.deposit(money - (money * BIG_COMMISSION));
            System.out.println("Комиссия составила " + money * BIG_COMMISSION);
        } else {
            super.deposit(money - (money * LITTLE_COMMISSION));
            System.out.println("Комиссия составила " + money * LITTLE_COMMISSION);
        }
    }
}
