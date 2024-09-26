/** Description of an exception thrown if the amount entered by the user is unapplicable
 *  
 *  @author Nathanael D.
 *  @author Aneica A.
 *  @author Mahnoor A.
 *  @author Brandon B.
 *  @version 1.0 Build March 21, 2024
 */


package bankingapplication2;

//class - an exception used if the amount the user entered is unapplicable
public class FormatAmountException extends Exception {
private double amount;

/**
 * Constructs an exception with an amount
 * @param amount the amount entered by the user
 */

public FormatAmountException(double amount) {
   super("The amount entered is unapplicable: " + amount);
   this.amount = amount;
}

/**
 * Gets the amount that caused the exception
 * @return The amount inputed by the user is returned
 */

public double getAmount() {
   return amount;
}
}