import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BankAccount {
    private String userId;
    private String pin;
    private double balance;
    
    public BankAccount(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getPin() {
        return pin;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        balance += amount;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    public boolean transfer(BankAccount recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            return true;
        }
        return false;
    }
}

class Transaction {
    private String type;
    private double amount;
    
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

class TransactionHistory {
    private List<Transaction> history;
    
    public TransactionHistory() {
        history = new ArrayList<>();
    }
    
    public void addTransaction(String type, double amount) {
        history.add(new Transaction(type, amount));
    }
    
    public void displayHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : history) {
            System.out.println(transaction);
        }
    }
}

class ATMSystem {
    private BankAccount currentUser;
    private TransactionHistory transactionHistory;
    
    public ATMSystem(BankAccount currentUser) {
        this.currentUser = currentUser;
        this.transactionHistory = new TransactionHistory();
    }
    
    public void runATM() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        
        while (!quit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    transactionHistory.displayHistory();
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    if (currentUser.withdraw(withdrawAmount)) {
                        transactionHistory.addTransaction("Withdraw", withdrawAmount);
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                    break;
                case 3:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    transactionHistory.addTransaction("Deposit", depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.next();
                    System.out.print("Enter transfer amount: $");
                    double transferAmount = scanner.nextDouble();
                    if (currentUser.transfer(new BankAccount(recipientId, ""), transferAmount)) {
                        transactionHistory.addTransaction("Transfer", transferAmount);
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed. Insufficient funds.");
                    }
                    break;
                case 5:
                    quit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

public class ATMApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your User ID: ");
        String userId = scanner.next();
        System.out.print("Enter your PIN: ");
        String pin = scanner.next();
        
        // Simulate user authentication (you can replace this with your own logic)
        BankAccount userAccount = authenticateUser(userId, pin);
        
        if (userAccount != null) {
            ATMSystem atmSystem = new ATMSystem(userAccount);
            atmSystem.runATM();
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }
    
    private static BankAccount authenticateUser(String userId, String pin) {
        // Implement your own logic for user authentication here
        // You can replace this with a database or file-based authentication mechanism
        // For simplicity, a predefined account is used in this example.
        if (userId.equals("123456") && pin.equals("7890")) {
            return new BankAccount(userId, pin);
        } else {
            return null;
        }
    }
}