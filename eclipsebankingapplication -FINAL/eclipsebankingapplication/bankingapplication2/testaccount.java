package bankingapplication2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Description of Banking Application Test Account
 * This class simulates a simple console-based banking application for managing checking and savings accounts.
 * 
 * @author Nathanael D.
 * @author Aneica A.
 * @author Mahnoor A.
 * @version 1.0 Build March 21, 2024
 */
public class testaccount {
    
    /** List to hold all accounts */
    private static final ArrayList<Account_S2024_Group2> accounts = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(testaccount.class.getName());

    /**
     * Main method that runs the banking application.
     * Handles user interaction through console input to perform various banking operations.
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Initial setup: adding some accounts
            accounts.add(new Checking_S2024_Group2("Jess", 1, 500, 50)); 
            accounts.add(new Saving_S2024_Group2("Carl", 2, 500, 0.5)); 
            accounts.add(new Saving_S2024_Group2("Jerry", 3, 550, 0.5));
            
            System.out.println("Welcome to Group2 Banking App");

            while (true) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Create New Checking Account");
                System.out.println("2. Create New Savings Account");
                System.out.println("3. Operate on Existing Account");
                System.out.println("4. Exit");
                System.out.print("Enter choice (1-4): ");
                int choice = getInt(scanner);

                switch (choice) {
                    case 1: // Create Checking Account
                    case 2: // Create Savings Account
                        createAccount(scanner, choice);
                        break;
                    case 3:
                        operateOnExistingAccount(scanner);
                        break;
                    case 4:
                        System.out.println("Exiting Banking App.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.severe("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method used to create an account based on user input.
     * @param scanner Scanner for user input of the name and account type.
     * @param accountType The type of account to be created, checking or savings.
     */
    private static void createAccount(Scanner scanner, int accountType) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        int accountNum = accounts.size() + 1;  // Incremental account number

        System.out.print("Enter initial deposit amount (minimum $25 for account setup fee): $");
        double initialDeposit = getDouble(scanner);

        if (initialDeposit < 25) {
            System.out.println("Initial deposit must be at least $25 to cover the setup fee.");
            return;
        }

        if (accountType == 1) {
            System.out.print("Enter overdraft limit (e.g., 500): $");
            double overdraftLimit = getDouble(scanner);
            Account_S2024_Group2 account = new Checking_S2024_Group2(name, accountNum, initialDeposit - 25, overdraftLimit);
            accounts.add(account);
            System.out.println("New Checking account created for " + name + ". Account Number: " + accountNum);
        } else {
            System.out.print("Enter annual interest rate (e.g., 0.03 for 3%): ");
            double interestRate = getDouble(scanner);
            if (interestRate < 0 || interestRate > 1) {
                System.out.println("Interest rate must be between 0 and 1 (e.g., 0.03 for 3%).");
                return;
            }
            Account_S2024_Group2 account = new Saving_S2024_Group2(name, accountNum, initialDeposit - 25, interestRate);
            accounts.add(account);
            System.out.println("New Savings account created for " + name + ". Account Number: " + accountNum);
        }
    }

    /**
     * Method to operate on an existing account.
     * @param scanner The scanner for user input.
     * @throws FormatAmountException if the amount entered by the user is unapplicable.
     */
    private static void operateOnExistingAccount(Scanner scanner) throws FormatAmountException {
        System.out.print("Enter your account number: ");
        int accountNum = getInt(scanner);
        Account_S2024_Group2 account = findAccountByNumber(accountNum);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        operateAccount(scanner, account);
    }

    /**
     * Method to find an account by account number.
     * @param accountNum The account number.
     * @return The account if found, null otherwise.
     */
    private static Account_S2024_Group2 findAccountByNumber(int accountNum) {
        for (Account_S2024_Group2 account : accounts) {
            if (account.getAccountNum() == accountNum) {
                return account;
            }
        }
        return null; // Account not found
    }

    /**
     * Method to perform various operations on the account.
     * @param scanner Scanner for user input.
     * @param account The account on which operations are performed.
     * @throws FormatAmountException if the amount entered by the user is unapplicable.
     */
    private static void operateAccount(Scanner scanner, Account_S2024_Group2 account) throws FormatAmountException {
        while (true) {
            System.out.println("\nWelcome " + account.getName() + ",");
            System.out.println("Account Menu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter choice (1-4): ");

            int operation = getInt(scanner);
            switch (operation) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = getDouble(scanner);
                    deposit(account, depositAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = getDouble(scanner);
                    withdraw(account, withdrawAmount);
                    break;
                case 3:
                    System.out.println("Current balance: $" + account.getBalance());
                    break;
                case 4:
                    System.out.println("Exiting Menu.");
                    return;
                default:
                    System.out.println("Invalid operation. Please try again.");
                    break;
            }
        }
    }

    private static void deposit(Account_S2024_Group2 account, double amount) {
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive.");
            return;
        }
        account.deposit(amount);
        System.out.println("Deposited: $" + amount + ". New Balance: $" + account.getBalance());
    }

    private static void withdraw(Account_S2024_Group2 account, double amount) throws FormatAmountException {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return;
        }
        if (amount > account.getBalance()) {
            System.out.println("Error: Insufficient funds.");
            return;
        }
        account.withdraw(amount);
        System.out.println("Withdrawn: $" + amount + ". New Balance: $" + account.getBalance());
    }

    /**
     * Utility method to handle integer inputs safely.
     * @param scanner The scanner used for input.
     * @return int The validated input.
     */
    private static int getInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.next(); // clear the invalid input before trying again
            System.out.print("Please enter a valid number: ");
        }
        return scanner.nextInt();
    }

    /**
     * Utility method to handle double inputs safely.
     * @param scanner The scanner used for input.
     * @return double The validated input.
     */
    private static double getDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            scanner.next(); // clear the invalid input before trying again
            System.out.print("Please enter a valid number: ");

        }
        return scanner.nextDouble();
    }


}
