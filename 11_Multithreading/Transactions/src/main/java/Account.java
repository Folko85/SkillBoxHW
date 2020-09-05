import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class Account {
    private BigDecimal money;
    private String accNumber;
    private volatile boolean blocked;
    private Logger logger = LogManager.getLogger(Account.class);

    public Account(String accNumber, BigDecimal money) {
        this.money = money;
        this.accNumber = accNumber;
        this.blocked = false;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public synchronized void deposit(BigDecimal money) {
        this.money = this.money.add(money);
        logger.info(this.getAccNumber() + " Баланс:" + this.getMoney());
    }

    public synchronized void withdraw(BigDecimal money) {
        this.money = this.money.subtract(money);
        logger.info(this.getAccNumber() + " Баланс:" + this.getMoney());
    }

    public void block() {
        this.blocked = true;
        logger.warn(this.getAccNumber() + ":заблокирован. Баланс:" + this.getMoney());}

    public boolean isBlocked() {
        return blocked;
    }
}
