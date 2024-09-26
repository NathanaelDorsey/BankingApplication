package bankingapplication2;

import java.util.logging.Logger;

/**
 * This abstract class is the foundation for the banking application.
 * It ensures sensitive information like customer's name and account number are handled securely.
 * 
 * @author Nathanael D.
 * @author Aneica A.
 * @author Mahnoor A.
 * @author Brandon B.
 * @version 1.0 Build March 21, 2024
 */
public abstract class Account_S2024_Group2 {
    private static final Logger LOGGER = Logger.getLogger(Account_S2024_Group2.class.getName());
    private static SecureAccountManager secureManager;

    // Sensitive data: Customer's name and Account number are stored encrypted
    protected String encryptedName;
    protected String encryptedAccountNum;

    // Financial data: Account balance, requires careful handling and auditing
    protected double balance;
    protected double addOn; //(interestRate/overdraft depending on account)
    private AccountType accountType; // Added field to store account type

    static {
        try {
            secureManager = new SecureAccountManager();
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize SecureAccountManager: " + e.getMessage());
        }
    }

    // Abstract methods for depositing and withdrawing, getting the balance and getting the addon
    protected abstract void deposit(double depositAmount);
    protected abstract void withdraw(double withdrawAmount) throws FormatAmountException;
    public abstract double getBalance();
    public abstract double getAddOn();

    /**
     * Constructor for Account_S2024_Group2 ensuring all inputs are valid and encrypts sensitive data.
     * Incorporates SEI best practices for handling sensitive information.
     * 
     * @param name The name associated with the account
     * @param accountNum The account number associated with the account
     * @param balance The initial balance of the account
     * @param accountType The type of the account (checking or savings)
     * @throws IllegalArgumentException if any parameters are invalid
     */
    public Account_S2024_Group2(String name, int accountNum, double balance, AccountType accountType, double addOn) {
        LOGGER.info("Attempting to create a new account");
        if (name == null || name.trim().isEmpty()) {
            LOGGER.severe("Invalid name input: Name cannot be null or empty.");
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (accountNum <= 0) {
            LOGGER.severe("Invalid account number input: Account number must be positive.");
            throw new IllegalArgumentException("Account number must be positive.");
        }
        if (balance < 0) {
            LOGGER.severe("Invalid balance input: Balance must be non-negative.");
            throw new IllegalArgumentException("Balance must be non-negative.");
        }

        try {
            this.encryptedName = secureManager.encryptData(name);
            this.encryptedAccountNum = secureManager.encryptData(String.valueOf(accountNum));
        } catch (Exception e) {
            LOGGER.severe("Encryption of sensitive data failed: " + e.getMessage());
            throw new RuntimeException("Encryption failed", e);
        }

        this.balance = balance;
        this.accountType = accountType;
        LOGGER.config("Account created successfully with encrypted data");
    }

    public String getName() {
        try {
            return secureManager.decryptData(encryptedName);
        } catch (Exception e) {
            LOGGER.severe("Decryption of name failed: " + e.getMessage());
            return null;
        }
    }

    public int getAccountNum() {
        try {
            return Integer.parseInt(secureManager.decryptData(encryptedAccountNum));
        } catch (Exception e) {
            LOGGER.severe("Decryption of account number failed: " + e.getMessage());
            return 0;
        }
    }


    public AccountType getAccountType() {
        return accountType;
    }
    
}
