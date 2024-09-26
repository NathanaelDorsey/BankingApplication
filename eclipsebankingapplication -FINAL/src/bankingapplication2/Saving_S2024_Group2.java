package bankingapplication2;
import java.util.logging.Logger;

/**
 * Description of Banking Application Saving Account
 * Extends the Account_S2024_Group2 class
 *  
 * @author Nathanael D.
 * @author Aneica A.
 * @author Mahnoor A.
 * @author Brandon B.
 * @version 1.0 Build March 21, 2024
 */
public class Saving_S2024_Group2 extends Account_S2024_Group2 {
    private static final Logger LOGGER = Logger.getLogger(Saving_S2024_Group2.class.getName());

    private double interestRate;

    /**
     * Constructor for Saving_S2024_Group2 with security annotations.
     * @param name The name associated with the account
     * @param accountNum The account number associated with the account
     * @param initialDeposit Initial deposit, from which a setup fee is deducted
     * @param interestRate Annual interest rate, must be non-negative
     * @param accountType The type of the account (saving in this case)
     */
    public Saving_S2024_Group2(String name, int accountNum, double initialDeposit, double interestRate) {
        super(name, accountNum, initialDeposit, AccountType.SAVINGS, interestRate); // Deduct the setup fee and pass account type
        this.interestRate = interestRate;
        if (interestRate < 0) {
            LOGGER.severe("Invalid interest rate input: Interest rate must be non-negative.");
            throw new IllegalArgumentException("Interest rate must be non-negative.");
        }

        LOGGER.info("Savings account created for " + name + " with balance: " + this.balance + " and interest rate: " + this.interestRate);
    }

    /**
     * Method to withdraw from the saving account
     * @param amount The amount being withdrawn
     * @throws FormatAmountException if the withdrawal amount is negative or exceeds available funds
     */
    @Override
    protected void withdraw(double amount) throws FormatAmountException {
        if (amount < 0) {
            throw new FormatAmountException(amount);
        } else if (amount > this.balance) {
            throw new FormatAmountException(amount);
        }
        this.balance -= amount;
        System.out.println("Withdrawn from savings: $" + amount);
    }

    /**
     * Method used to handle deposits into the saving account
     * @param amount The amount being deposited
     */
    @Override
    protected void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
        System.out.println("Deposited to savings: $" + amount);
    }

    @Override
    public double getBalance() {
        return balance;
    }

	@Override
	public double getAddOn() {
		return interestRate;
	}
}