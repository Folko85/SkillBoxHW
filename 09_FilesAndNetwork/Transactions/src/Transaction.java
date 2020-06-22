import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private LocalDate transactionDate;
    private String description;
    private BigDecimal incomingMoney;
    private BigDecimal outgoingMoney;

    public Transaction(LocalDate transactionDate, String description, BigDecimal incomingMoney, BigDecimal outgoingMoney) {
        this.transactionDate = transactionDate;   // чтобы было
        this.description = description;
        this.incomingMoney = incomingMoney;
        this.outgoingMoney = outgoingMoney;
    }

    @Override
    public String toString() {
        return "Дата операции " + this.transactionDate + ". Описание операции " + this.description + ". Приход " + this.incomingMoney + ". Расход " + this.outgoingMoney;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getIncomingMoney() {
        return incomingMoney;
    }

    public BigDecimal getOutgoingMoney() {
        return outgoingMoney;
    }
}
