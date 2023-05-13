package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    private Account account = new Account();

    private void assertBalanceEquals(double value){
        double accountBalance = account.AccountStatus();
        double expectedBalance = value;
        assertEquals(accountBalance, expectedBalance, 1e-9);
    }

    @Test
    public void testDefaultBalance() {
        double expectedBalance = 0.0;

        assertBalanceEquals(expectedBalance);
    }

    @Test
    public void testPositiveDeposit() {
        account.DepositFunds(50.0);

        double expectedBalance = 50.0;
        assertBalanceEquals(expectedBalance);
    }

    @Test
    public void testMultipleDeposits() {
        account.DepositFunds(50.1);
        account.DepositFunds(150.25);
        account.DepositFunds(99.75);

        double expectedBalance = 300.1;
        assertBalanceEquals(expectedBalance);
    }

    @Test
    public void testNegativeDeposit() {
        account.DepositFunds(-50.0);

        double expectedBalance = 0.0;
        assertBalanceEquals(expectedBalance);
    }

    @Test
    public void testSufficientWithdraw() {
        account.DepositFunds(150.0);
        account.WithdrawFunds(50.0);

        double expectedBalance = 100.0;
        assertBalanceEquals(expectedBalance);
    }

    @Test
    public void testWithdrawToZero() {
        account.DepositFunds(150.0);
        account.WithdrawFunds(150.0);

        double expectedBalance = 0.0;
        assertBalanceEquals(expectedBalance);
    }

    @Test
    public void testNotSufficientWithdraw() {
        account.DepositFunds(50.0);
        account.WithdrawFunds(150.0);

        double expectedBalance = 50.0;
        assertBalanceEquals(expectedBalance);
    }
}