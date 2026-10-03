import java.util.Scanner;

public class EWalletSystem {
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private final Scanner scanner = new Scanner(System.in);
    private final Wallet wallet = new Wallet();

    public static void main(String[] args) {
        new EWalletSystem().run();
    }

    private void run() {
        System.out.println("====================================");
        System.out.println("       E-WALLET SYSTEM");
        System.out.println("====================================");

        boolean running = true;
        while (running) {
            try {
                showMainMenu();
                int choice = readInt("Choose: ");

                switch (choice) {
                    case 1 -> signUp();
                    case 2 -> login();
                    case 3 -> {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void showMainMenu() {
        System.out.println("\n1. Sign Up");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    private void signUp() {
        System.out.println("\n--- SIGN UP ---");
        String username = readNonEmpty("Username: ");

        if (!Validator.validUsername(username)) {
            System.out.println("Username must be 3-20 characters and start with an uppercase letter.");
            return;
        }
        if (wallet.findByUsername(username) != null) {
            System.out.println("Username already exists.");
            return;
        }

        String password = readNonEmpty("Password: ");
        if (!Validator.validPassword(password)) {
            System.out.println("Password must be at least 8 chars and contain uppercase, lowercase and digit.");
            return;
        }

        int age = readInt("Age: ");
        if (!Validator.validAge(age)) {
            System.out.println("Age must be at least 18.");
            return;
        }

        String phone = readNonEmpty("Egyptian phone number: ");
        if (!Validator.validPhone(phone)) {
            System.out.println("Invalid Egyptian phone number. Example: 01012345678");
            return;
        }
        if (wallet.findByPhone(phone) != null) {
            System.out.println("Phone number already exists.");
            return;
        }

        Account account = new Account(username, password, age, phone, false);
        if (wallet.addAccount(account)) {
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Could not create account.");
        }
    }

    private void login() {
        System.out.println("\n--- LOGIN ---");

        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            String username = readNonEmpty("Username: ");
            String password = readNonEmpty("Password: ");

            Account account = wallet.findByUsername(username);

            if (account == null) {
                System.out.println("User does not exist.");
            } else if (!account.isActive()) {
                System.out.println("This account is inactive.");
                return;
            } else if (!account.getPassword().equals(password)) {
                System.out.println("Wrong password.");
            } else {
                wallet.addTransaction("LOGIN", account.getUsername(), 0, "Successful login");
                System.out.println("Login successful. Welcome " + account.getUsername() + "!");
                if (account.isAdmin()) adminMenu(account);
                else userMenu(account);
                return;
            }

            if (attempt < MAX_LOGIN_ATTEMPTS) {
                System.out.println("Attempts remaining: " + (MAX_LOGIN_ATTEMPTS - attempt));
            }
        }

        System.out.println("Maximum login attempts reached.");
    }

    private void userMenu(Account account) {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Account Details");
            System.out.println("5. Change Password");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");

            try {
                int choice = readInt("Choose: ");
                switch (choice) {
                    case 1 -> deposit(account);
                    case 2 -> withdraw(account);
                    case 3 -> transfer(account);
                    case 4 -> showDetails(account);
                    case 5 -> changePassword(account);
                    case 6 -> showHistory(account);
                    case 7 -> {
                        System.out.println("Goodbye, " + account.getUsername() + "!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private void deposit(Account account) {
        double amount = readAmount("Deposit amount: ");
        account.deposit(amount);
        wallet.addTransaction("DEPOSIT", account.getUsername(), amount, "Money deposited");
        System.out.printf("Deposit successful. New balance: %.2f%n", account.getBalance());
    }

    private void withdraw(Account account) {
        double amount = readAmount("Withdraw amount: ");
        if (amount > account.getBalance()) {
            System.out.println("Insufficient balance.");
            return;
        }
        account.withdraw(amount);
        wallet.addTransaction("WITHDRAW", account.getUsername(), amount, "Money withdrawn");
        System.out.printf("Withdrawal successful. New balance: %.2f%n", account.getBalance());
    }

    private void transfer(Account sender) {
        String destinationUsername = readNonEmpty("Destination username: ");
        Account receiver = wallet.findByUsername(destinationUsername);

        if (receiver == null) {
            System.out.println("Destination account does not exist.");
            return;
        }
        if (receiver == sender) {
            System.out.println("You cannot transfer money to yourself.");
            return;
        }
        if (!receiver.isActive()) {
            System.out.println("Destination account is inactive.");
            return;
        }

        double amount = readAmount("Transfer amount: ");
        if (amount > sender.getBalance()) {
            System.out.println("Insufficient balance.");
            return;
        }

        sender.withdraw(amount);
        receiver.deposit(amount);

        wallet.addTransaction("TRANSFER", sender.getUsername(), amount,
                "Sent to " + receiver.getUsername());
        wallet.addTransaction("TRANSFER", receiver.getUsername(), amount,
                "Received from " + sender.getUsername());

        System.out.printf("Transfer successful. Your balance: %.2f%n", sender.getBalance());
        System.out.printf("Receiver balance: %.2f%n", receiver.getBalance());
    }

    private void showDetails(Account account) {
        System.out.println("\n--- ACCOUNT DETAILS ---");
        System.out.println("Username: " + account.getUsername());
        System.out.println("Phone: " + account.getPhoneNumber());
        System.out.println("Age: " + account.getAge());
        System.out.printf("Balance: %.2f%n", account.getBalance());
        System.out.println("Admin: " + account.isAdmin());
        System.out.println("Active: " + account.isActive());
        System.out.println("Password: ********");
    }

    private void changePassword(Account account) {
        String oldPassword = readNonEmpty("Old password: ");
        if (!account.getPassword().equals(oldPassword)) {
            System.out.println("Old password is incorrect.");
            return;
        }

        String newPassword = readNonEmpty("New password: ");
        if (!Validator.validPassword(newPassword)) {
            System.out.println("Invalid password format.");
            return;
        }
        if (newPassword.equals(oldPassword)) {
            System.out.println("New password must be different.");
            return;
        }

        account.setPassword(newPassword);
        wallet.addTransaction("PASSWORD_CHANGE", account.getUsername(), 0, "Password changed");
        System.out.println("Password changed successfully.");
    }

    private void showHistory(Account account) {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        boolean found = false;
        for (Transaction transaction : wallet.getTransactions()) {
            if (transaction.toString().contains("User: " + String.format("%-12s", account.getUsername()))) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) System.out.println("No transactions found.");
    }

    private void adminMenu(Account admin) {
        while (true) {
            System.out.println("\n--- ADMIN PANEL ---");
            System.out.println("1. View All Accounts");
            System.out.println("2. Delete Account");
            System.out.println("3. Activate Account");
            System.out.println("4. Deactivate Account");
            System.out.println("5. View All Transactions");
            System.out.println("6. Logout");

            try {
                int choice = readInt("Choose: ");
                switch (choice) {
                    case 1 -> viewAllAccounts();
                    case 2 -> deleteAccount();
                    case 3 -> setAccountActive(true);
                    case 4 -> setAccountActive(false);
                    case 5 -> viewAllTransactions();
                    case 6 -> {
                        System.out.println("Admin logged out.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Admin operation failed: " + e.getMessage());
            }
        }
    }

    private void viewAllAccounts() {
        System.out.println("\n--- ALL ACCOUNTS ---");
        for (Account account : wallet.getAccounts()) {
            System.out.println(account);
        }
    }

    private void deleteAccount() {
        String username = readNonEmpty("Username to delete: ");
        Account account = wallet.findByUsername(username);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        if (account.isAdmin()) {
            System.out.println("The default admin account cannot be deleted.");
            return;
        }

        if (wallet.deleteAccount(username)) System.out.println("Account deleted.");
        else System.out.println("Could not delete account.");
    }

    private void setAccountActive(boolean active) {
        String username = readNonEmpty("Username: ");
        Account account = wallet.findByUsername(username);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        if (account.isAdmin()) {
            System.out.println("The default admin account cannot be deactivated.");
            return;
        }

        account.setActive(active);
        System.out.println(active ? "Account activated." : "Account deactivated.");
    }

    private void viewAllTransactions() {
        System.out.println("\n--- ALL TRANSACTIONS ---");
        if (wallet.getTransactions().isEmpty()) {
            System.out.println("No transactions.");
            return;
        }
        for (Transaction transaction : wallet.getTransactions()) {
            System.out.println(transaction);
        }
    }

    private String readNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty.");
        }
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readAmount(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(value);
                if (Double.isFinite(amount) && amount > 0) return amount;
                System.out.println("Amount must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}