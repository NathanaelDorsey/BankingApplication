/** Description of an exception thrown if the account entered by the user is not found
 *  
 *  @author Nathanael D.
 *  @author Aneica A.
 *  @author Mahnoor A.
 *  @version 1.0 Build March 21, 2024
 */

package bankingapplication2;

//class - an exception used if the account number entered by the user is not found with an account
public class AccountNotFoundException extends Exception {
 private int accountNumber;
//the exception
 
/**
 * Constructs an exception with accountNumber
 * @param accountNumber the account number entered by the user
 */

 
 public AccountNotFoundException(int accountNumber) {
     super("Account not found for account number: " + accountNumber);
     this.accountNumber = accountNumber;
 }
 
 /**
  * 
  * @return the account number inputed by the user is returned
  */

 public int getAccountNumber() {
     return accountNumber;
 }
}