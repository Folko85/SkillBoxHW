import clients.BusinessPerson;
import clients.Company;
import clients.Person;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Person pupkin = new Person("Вася Пупкин");
        pupkin.deposit(BigDecimal.valueOf(10000.0));
        pupkin.withdraw(BigDecimal.valueOf(5000.0));
        System.out.println(pupkin.getBalance());
        System.out.println(pupkin);

        BusinessPerson dengoff = new BusinessPerson("Иван Деньгов");
        dengoff.deposit(BigDecimal.valueOf(10000.0));
        dengoff.deposit(BigDecimal.valueOf(500.0));
        dengoff.withdraw(BigDecimal.valueOf(5000.0));
        System.out.println(dengoff.getBalance());
        System.out.println(dengoff);

        StringBuilder innNumber = new StringBuilder(); // сгенерируем инн уже известным методом
        for (int i = 0; i < 10; i++) {
            innNumber.append(ThreadLocalRandom.current().nextInt(0, 10));  // номер счёта генерируем случайным образом с помощью цифр от 0 до 9
        }
        BigInteger inn = new BigInteger(innNumber.toString());
        Company mmm = new Company("МММ", inn);
        mmm.deposit(BigDecimal.valueOf(10000.0));
        mmm.withdraw(BigDecimal.valueOf(5000.0));
        System.out.println(mmm.getBalance());
        System.out.println(mmm);
    }
}
