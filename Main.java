public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Demo accounts for testing. In a real application, never store PINs like this.
        bank.addAccount(new Account("U1001", "1234", 5000.00));
        bank.addAccount(new Account("U1002", "4321", 3000.00));
        bank.addAccount(new Account("U1003", "1111", 7500.00));

        ATM atm = new ATM(bank);
        atm.start();
    }
}
