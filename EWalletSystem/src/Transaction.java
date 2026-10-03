import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type;
    private final String username;
    private final String details;
    private final double amount;
    private final LocalDateTime dateTime;

    public Transaction(String type, String username, double amount, String details) {
        this.type = type;
        this.username = username;
        this.amount = amount;
        this.details = details;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String time = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.format("[%s] %-16s User: %-12s Amount: %.2f | %s",
                time, type, username, amount, details);
    }
}