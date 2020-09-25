package bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Bank {
    private ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    Logger logger = LogManager.getLogger(Bank.class);

    public Bank() {  // при создании банка в целях удобства сразу создадим ему несколько сотен аккаунтов
        double startLimit = 300_000.0;
        int accountCount = 5000;
        for (int i = 0; i < accountCount; i++) {
            String name = String.format("%06d", i);
            this.accounts.put(name, new Account(name, BigDecimal.valueOf(startLimit)));
        }
    }

    public boolean checkFraud(String fromAccountNum, String toAccountNum, BigDecimal amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return ThreadLocalRandom.current().nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, BigDecimal amount) throws InterruptedException {
                if (checkAbility(fromAccountNum, toAccountNum, amount)) {     // проверяем корректность перевода
                    if (amount.compareTo(BigDecimal.valueOf(50000)) <= 0) {
                        accounts.get(fromAccountNum).withdraw(amount);  // если сумма мелкая - переводим
                        accounts.get(toAccountNum).deposit(amount);
                    } else if (checkFraud(fromAccountNum, toAccountNum, amount)) {
                        accounts.get(fromAccountNum).withdraw(amount);  // если не мошенничество - тож переводим
                        accounts.get(toAccountNum).deposit(amount);
                    } else {
                        accounts.get(fromAccountNum).block();  // если мошенничество - блокируем
                        accounts.get(toAccountNum).block();
                    }
        }
    }
     // метод для проверки callable. Как ни странно, всё работает
    public BigDecimal smartTransfer(String fromAccountNum, String toAccountNum, BigDecimal amount) throws InterruptedException {
       // BigDecimal result = new BigDecimal(BigInteger.ZERO);
        if (checkAbility(fromAccountNum, toAccountNum, amount)) {     // проверяем корректность перевода
            if (amount.compareTo(BigDecimal.valueOf(50000)) <= 0) {
                accounts.get(fromAccountNum).withdraw(amount);  // если сумма мелкая - переводим
                accounts.get(toAccountNum).deposit(amount);
            } else if (checkFraud(fromAccountNum, toAccountNum, amount)) {
                accounts.get(fromAccountNum).withdraw(amount);  // если не мошенничество - тож переводим
                accounts.get(toAccountNum).deposit(amount);
            } else {
                accounts.get(fromAccountNum).block();  // если мошенничество - блокируем
                accounts.get(toAccountNum).block();
            }
        }
        return accounts.get(toAccountNum).getMoney();
    }

    public BigDecimal getSumBalance() {
        return this.accounts.values().stream().map(Account::getMoney).reduce(BigDecimal::add).orElseThrow();
    }

    public boolean checkAbility(String fromAccountNum, String toAccountNum, BigDecimal amount) {
        if (!checkExist(fromAccountNum, toAccountNum)) {
            return false;
        }  // проверяем существование аккаунтов
        else // проверяем, не заблокированы ли уже аккаунты
            if (!checkMoney(fromAccountNum, amount)) {    // проверяем наличие денег
                logger.warn(fromAccountNum + ": нет денег Баланс:" + accounts.get(fromAccountNum).getMoney());
                return false;
            } else return checkNotBlocked(fromAccountNum, toAccountNum);
    }

    public boolean checkExist(String... accNumber) {  // просто опробовать varargs
        for (String s : accNumber) {
            if (!accounts.containsKey(s)) {
                logger.warn(s + ": некорректный номер");
                return false;
            }     // если хотя бы один из аккаунтов не существует
        }
        return true;
    }

    public boolean checkMoney(String accNumber, BigDecimal amount) {
        return (accounts.get(accNumber).getMoney().compareTo(amount) >= 0);
    }

    public boolean checkNotBlocked(String... accNumber) {  // просто опробовать varargs
        for (String s : accNumber) {
            if (accounts.get(s).isBlocked()) {
                logger.warn(s + ": заблокирован ранее");
                return false;
            }     // если хотя бы один из аккаунтов заблокирован
        }
        return true;
    }
}
