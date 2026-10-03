import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wallet {
    private final List<Account> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public Wallet() {
        addDefaultAdmin();
    }

    private void addDefaultAdmin() {
        if (findByUsername("IAM") == null) {
            accounts.add(new Account("IAM", "IAM123", 18, "01000000000", true));
        }
    }

    public boolean addAccount(Account account) {
        if (account == null || findByUsername(account.getUsername()) != null
                || findByPhone(account.getPhoneNumber()) != null) {
            return false;
        }
        accounts.add(account);
        addTransaction("SIGNUP", account.getUsername(), 0, "Account created");
        return true;
    }

    public Account findByUsername(String username) {
        if (username == null) return null;
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(username.trim())) return account;
        }
        return null;
    }

    public Account findByPhone(String phone) {
        if (phone == null) return null;
        for (Account account : accounts) {
            if (account.getPhoneNumber().equals(phone.trim())) return account;
        }
        return null;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void addTransaction(String type, String username, double amount, String details) {
        transactions.add(new Transaction(type, username, amount, details));
    }

    public boolean deleteAccount(String username) {
        Account account = findByUsername(username);
        if (account == null || account.isAdmin()) return false;
        accounts.remove(account);
        addTransaction("DELETE", username, 0, "Account deleted by admin");
        return true;
    }
}