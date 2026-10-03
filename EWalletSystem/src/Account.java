public class Account {
    private final String username;
    private String password;
    private final int age;
    private final String phoneNumber;
    private double balance;
    private final boolean isAdmin;
    private boolean active;

    public Account(String username, String password, int age, String phoneNumber, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.balance = 0.0;
        this.isAdmin = isAdmin;
        this.active = true;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getAge() { return age; }
    public String getPhoneNumber() { return phoneNumber; }
    public double getBalance() { return balance; }
    public boolean isAdmin() { return isAdmin; }
    public boolean isActive() { return active; }

    public void setPassword(String password) { this.password = password; }
    public void setActive(boolean active) { this.active = active; }

    public void deposit(double amount) { balance += amount; }
    public void withdraw(double amount) { balance -= amount; }

    @Override
    public String toString() {
        return String.format("Username: %s | Phone: %s | Age: %d | Balance: %.2f | Admin: %s | Active: %s",
                username, phoneNumber, age, balance, isAdmin, active);
    }
}