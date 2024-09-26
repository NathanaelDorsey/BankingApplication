/** Description of Banking Application Checking Account
 * Extends the Account_S2024_Group2 class
 *  
 *  @author Nathanael D.
 *  @author Aneica A.
 *  @author Mahnoor A.
 *  @version 1.0 Build March 21, 2024
 */

package bankingapplication2;
import java.util.logging.Logger;

/**
 * Constructor for Checking_S2024_Group2 with security annotations.
 * @param name Name of the account holder
 * @param accountNum Account number
 * @param initialDeposit Initial deposit minus setup fee
 */

public class Checking_S2024_Group2 extends Account_S2024_Group2 {
    private static final Logger LOGGER = Logger.getLogger(Checking_S2024_Group2.class.getName());

    private double overdraftLimit;
    private double balance;

   

        public Checking_S2024_Group2(String name, int accountNum, double balance, double overdraftLimit) {
            super(name, accountNum, balance, AccountType.CHECKING);
            this.overdraftLimit = overdraftLimit;
        }
        
        
       

        /**
         * Deposits a specified amount into the checking account.
         * @param amount Amount to deposit, must be positive.
         */
        public void deposit(double amount) {
            if (amount <= 0) {
                LOGGER.warning("Attempt to deposit non-positive amount.");
                throw new IllegalArgumentException("Deposit amount must be positive.");
            }
            balance += amount;
            LOGGER.fine("Deposited " + amount + "; New balance: " + balance);
        }

        /**
         * Withdraws a specified amount from the checking account.
         * @param amount Amount to withdraw, must not exceed balance + overdraft.
         */
        public void withdraw(double amount) {
            if (amount > balance + overdraftLimit) {
                LOGGER.warning("Attempt to withdraw exceeding overdraft limit.");
                throw new IllegalArgumentException("Withdrawal amount exceeds available funds.");
            }
            balance -= amount;
            LOGGER.fine("Withdrew " + amount + "; New balance: " + balance);     
    }
   
	public double getBalance() {
		return balance;
	}
}

	


	