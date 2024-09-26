package bankingapplication2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Saving_S2024_Group2Test {

    @Test
    void testWithdraw() throws FormatAmountException {
        Saving_S2024_Group2 demoSavingsAccount = new Saving_S2024_Group2("Carl", 2, 500, 0.5);
        demoSavingsAccount.withdraw(100);
        assertEquals(400, demoSavingsAccount.getBalance(), "Balance should be $400 after withdrawing $100.");
    }

    @Test
    void testDeposit() {
        Saving_S2024_Group2 demoSavingsAccount = new Saving_S2024_Group2("Carl", 2, 500, 0.5);
        demoSavingsAccount.deposit(100);
        assertEquals(600, demoSavingsAccount.getBalance(), "Balance should be $600 after depositing $100.");
    }
}