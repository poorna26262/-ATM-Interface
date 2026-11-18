import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getUserId(), account);
    }

    public Account authenticate(String userId, String pin) {
        Account account = accounts.get(userId);
        if (account != null && account.verifyPin(pin)) {
            return account;
        }
        return null;
    }

    public Account findAccount(String userId) {
        return accounts.get(userId);
    }

    public synchronized boolean transfer(Account sender, String recipientId, double amount) {
        if (sender == null || recipientId == null || !Double.isFinite(amount) || amount <= 0) {
            return false;
        }

        Account recipient = accounts.get(recipientId);
        if (recipient == null || recipient == sender || !sender.canWithdraw(amount)) {
            return false;
        }

        // Both account balances and both transaction logs are updated together.
        sender.recordTransferOut(amount, recipientId);
        recipient.recordTransferIn(amount, sender.getUserId());
        return true;
    }
}
