package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    private IAccount setupAccount() {
        IAccount account = new Account();
        account.DepositFunds(100.0);

        return account;
    }

    // D
    @Test
    public void defaultBalance() {
        IAccount account = new Account();
        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void negativeDeposit() {
        IAccount account = new Account();
        account.DepositFunds(-100.0);

        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void negativeMarginalDeposit() {
        IAccount account = new Account();
        account.DepositFunds(-0.01);

        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void zeroDeposit() {
        IAccount account = new Account();
        account.DepositFunds(0.0);

        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void insufficientMarginalDeposit() {
        IAccount account = new Account();
        account.DepositFunds(0.0099999);

        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void marginalDeposit() {
        IAccount account = new Account();
        account.DepositFunds(0.01);

        assertEquals(0.01, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void fractionalDeposit1() {
        IAccount account = new Account();
        account.DepositFunds(0.0100001);

        assertEquals(0.01, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void fractionalDeposit2() {
        IAccount account = new Account();
        account.DepositFunds(0.015);

        assertEquals(0.01, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void fractionalDeposit3() {
        IAccount account = new Account();
        account.DepositFunds(0.0199999);

        assertEquals(0.01, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void normalDeposit() {
        IAccount account = new Account();
        account.DepositFunds(100.0);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void bigDeposit() {
        IAccount account = new Account();
        account.DepositFunds(1_000_000.0);

        assertEquals(1_000_000.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void maxDeposit() {
        IAccount account = new Account();
        account.DepositFunds(Double.MAX_VALUE);

        assertEquals(Double.MAX_VALUE, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void overflowDeposit() {
        IAccount account = new Account();
        account.DepositFunds(Double.MAX_VALUE);
        account.DepositFunds(100.0);

        assertEquals(Double.MAX_VALUE + 100.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void infiniteDeposit() {
        IAccount account = new Account();
        account.DepositFunds(Double.POSITIVE_INFINITY);

        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void negativeWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(-100.0);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void zeroWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(0.0);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void insufficientMarginalWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(0.0099999);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void marginalWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(0.01);

        assertEquals(99.99, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void fractionalWithdrawal1() {
        IAccount account = setupAccount();
        account.WithdrawFunds(0.0100001);

        assertEquals(99.99, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void fractionalWithdrawal2() {
        IAccount account = setupAccount();
        account.WithdrawFunds(0.015);

        assertEquals(99.99, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void fractionalWithdrawal3() {
        IAccount account = setupAccount();
        account.WithdrawFunds(0.0199999);

        assertEquals(99.99, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void normalWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(50.0);

        assertEquals(50.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void fullWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(100.0);

        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void overWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(100.01);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    // Z + I
    @Test
    public void maxOverWithdrawal() {
        IAccount account = setupAccount();
        account.WithdrawFunds(Double.MAX_VALUE);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    // Z + I + I
    @Test
    public void multipleDeposits() {
        IAccount account = new Account();

        for (int i = 0; i < 10_000; ++i) {
            account.DepositFunds(100.0);
        }

        assertEquals(1_000_000.0, account.AccountStatus(), 0.0);
    }

    // Z + I + I
    @Test
    public void lotsOfBigDeposits() {
        IAccount account = new Account();

        for (int i = 0; i < 1_000_000; ++i) {
            account.DepositFunds(1_000_000.0);
        }

        assertEquals(1_000_000_000_000.0, account.AccountStatus(), 0.0);
    }

    // Z + I + I
    @Test
    public void multipleWithdrawals() {
        IAccount account = setupAccount();

        for (int i = 0; i < 100; ++i) {
            account.WithdrawFunds(0.1);
        }

        assertEquals(90.0, account.AccountStatus(), 0.0);
    }

    // Z + I + I
    @Test
    public void multipleWithdrawalsToZero() {
        IAccount account = setupAccount();

        for (int i = 0; i < 10_000; ++i) {
            account.WithdrawFunds(0.01);
        }

        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z + I + I
    @Test
    public void lotsOfWithdrawals() {
        IAccount account = new Account();
        account.DepositFunds(1_000_000.0);

        for (int i = 0; i < 1_000_000; ++i) {
            account.WithdrawFunds(0.01);
        }

        assertEquals(990_000.0, account.AccountStatus(), 0.0);
    }

    // Z + Z + I
    @Test
    public void maxDepositAndWithdrawal() {
        IAccount account = setupAccount();
        account.DepositFunds(Double.MAX_VALUE);
        account.WithdrawFunds(Double.MAX_VALUE);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    // Z + Z + I + I
    @Test
    public void twoMaxDepositsAndWithdrawals() {
        IAccount account = setupAccount();
        account.DepositFunds(Double.MAX_VALUE);
        account.DepositFunds(Double.MAX_VALUE);
        account.WithdrawFunds(Double.MAX_VALUE);
        account.WithdrawFunds(Double.MAX_VALUE);

        assertEquals(100.0, account.AccountStatus(), 0.0);
    }
}
