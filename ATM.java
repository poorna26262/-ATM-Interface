import java.util.List;
import java.util.Scanner;

public class ATM {
    private final Bank bank;
    private final Scanner scanner;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        printHeader();
        Account loggedInAccount = login();

        if (loggedInAccount == null) {
            System.out.println("\nAccess denied. Your account is temporarily locked for this session.");
            System.out.println("Thank you for using our ATM.");
            return;
        }

        System.out.println("\nLogin successful. Welcome, " + loggedInAccount.getUserId() + "!");
        runMenu(loggedInAccount);
    }

    private Account login() {
        final int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print("\nEnter User ID: ");
            String userId = scanner.nextLine().trim();

            System.out.print("Enter 4-digit PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = bank.authenticate(userId, pin);
            if (account != null) {
                return account;
            }

            int remaining = maxAttempts - attempt;
            if (remaining > 0) {
                System.out.println("Invalid User ID or PIN. Attempts remaining: " + remaining);
            }
        }
        return null;
    }

    private void runMenu(Account account) {
        boolean running = true;

        while (running) {
            System.out.println("\n========== ATM MAIN MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("===================================");
            System.out.printf("Available balance: ₹%.2f%n", account.getBalance());
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showTransactionHistory(account);
                    break;
                case "2":
                    withdraw(account);
                    break;
                case "3":
                    deposit(account);
                    break;
                case "4":
                    transfer(account);
                    break;
                case "5":
                    System.out.println("\nThank you for using our ATM. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose from 1 to 5.");
            }
        }
    }

    private void showTransactionHistory(Account account) {
        List<Transaction> history = account.getTransactions();

        System.out.println("\n============= TRANSACTION HISTORY =============");
        if (history.isEmpty()) {
            System.out.println("No transactions recorded in this session.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
        System.out.printf("%nCurrent balance: ₹%.2f%n", account.getBalance());
        System.out.println("===============================================");
    }

    private void withdraw(Account account) {
        double amount = readAmount("Enter amount to withdraw: ₹");
        if (amount <= 0) {
            System.out.println("Invalid amount. Enter a number greater than zero.");
            return;
        }

        if (!account.canWithdraw(amount)) {
            System.out.println("Insufficient Funds. Withdrawal was not completed.");
            return;
        }

        account.withdraw(amount);
        System.out.printf("Please collect your cash. ₹%.2f withdrawn successfully.%n", amount);
        System.out.printf("Remaining balance: ₹%.2f%n", account.getBalance());
    }

    private void deposit(Account account) {
        double amount = readAmount("Enter amount to deposit: ₹");
        if (amount <= 0) {
            System.out.println("Invalid amount. Enter a number greater than zero.");
            return;
        }

        if (account.deposit(amount)) {
            System.out.printf("₹%.2f deposited successfully.%n", amount);
            System.out.printf("Updated balance: ₹%.2f%n", account.getBalance());
        } else {
            System.out.println("Deposit could not be completed.");
        }
    }

    private void transfer(Account sender) {
        System.out.print("Enter recipient account ID: ");
        String recipientId = scanner.nextLine().trim();

        if (recipientId.isEmpty()) {
            System.out.println("Recipient account ID cannot be empty.");
            return;
        }

        double amount = readAmount("Enter amount to transfer: ₹");
        if (amount <= 0) {
            System.out.println("Invalid amount. Enter a number greater than zero.");
            return;
        }

        Account recipient = bank.findAccount(recipientId);
        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }
        if (recipient == sender) {
            System.out.println("You cannot transfer money to your own account.");
            return;
        }
        if (!sender.canWithdraw(amount)) {
            System.out.println("Insufficient Funds. Transfer was not completed.");
            return;
        }

        if (bank.transfer(sender, recipientId, amount)) {
            System.out.printf("₹%.2f transferred successfully to %s.%n", amount, recipientId);
            System.out.printf("Your updated balance: ₹%.2f%n", sender.getBalance());
        } else {
            System.out.println("Transfer failed. Please check the account and amount.");
        }
    }

    private double readAmount(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(input);
            return Double.isFinite(amount) ? amount : -1;
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private void printHeader() {
        System.out.println("==================================");
        System.out.println("          JAVA ATM SYSTEM");
        System.out.println("==================================");
    }
}
