import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final String userId;
    private final String pin;
    private double balance;
    private final ArrayList<Transaction> transactions;

    public Account(String userId, String pin, double openingBalance) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty.");
        }
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must contain exactly 4 digits.");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }

        this.userId = userId;
        this.pin = pin;
        this.balance = openingBalance;
        this.transactions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public boolean withdraw(double amount) {
        if (!isValidAmount(amount) || amount > balance) {
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAWAL", amount,
                "Cash withdrawal", balance));
        return true;
    }

    public boolean deposit(double amount) {
        if (!isValidAmount(amount)) {
            return false;
        }
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount,
                "Cash deposit", balance));
        return true;
    }

    public boolean canWithdraw(double amount) {
        return isValidAmount(amount) && amount <= balance;
    }

    public void recordTransferOut(double amount, String recipientId) {
        balance -= amount;
        transactions.add(new Transaction("TRANSFER OUT", amount,
                "Transferred to account " + recipientId, balance));
    }

    public void recordTransferIn(double amount, String senderId) {
        balance += amount;
        transactions.add(new Transaction("TRANSFER IN", amount,
                "Received from account " + senderId, balance));
    }

    private boolean isValidAmount(double amount) {
        return Double.isFinite(amount) && amount > 0;
    }
}
