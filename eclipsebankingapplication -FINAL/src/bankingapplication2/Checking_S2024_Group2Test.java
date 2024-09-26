package bankingapplication2;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Checking_S2024_Group2Test {

	@Test
	void testWithdraw() {
		Checking_S2024_Group2 demoSavingsAccount = new Checking_S2024_Group2("Jess", 1, 5050, 50); 
		demoSavingsAccount.withdraw(1000);
		assertEquals(4000, demoSavingsAccount.getBalance());
	}

	@Test
	void testDeposit() {
		Checking_S2024_Group2 demoSavingsAccount = new Checking_S2024_Group2("Jess", 1, 5050, 50); 
		demoSavingsAccount.deposit(1000);
		assertEquals(6000, demoSavingsAccount.getBalance());
	}


}