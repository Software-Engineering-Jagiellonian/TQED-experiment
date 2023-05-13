package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	private Account account = new Account();
    private double epsilon = 0.000001d;

//region DepositFunds
    @Test
    public void depositFundsOnceInt() {
        double expected = 150;
        double actual = account.DepositFunds(150);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void depositFundsMultipleInt() {
        double expected = 150 * 10;
        double actual = 0;

        for(int i = 0; i < 10; i++)
        {
            actual = account.DepositFunds(150);
        }

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void depositFundsOnceFloatingPoint() {
        double expected = 0.5;
        double actual = account.DepositFunds(0.5);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void depositFundsMultipleFloatingPoint() {
        double expected = 0.1 * 10;
        double actual = 0;

        for(int i = 0; i < 10; i++)
        {
            actual = account.DepositFunds(0.1);
        }

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void depositFundsZero() {
        double expected = 0.0;
        double actual = account.DepositFunds(0.0);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void depositFundsAddZero() {
        double expected = 0.5;
        double actual = account.DepositFunds(0.5);
        actual = account.DepositFunds(0.0);

        assertEquals(expected, actual, epsilon);
    }
//endregion DepositFunds

//region AccountStatus
    @Test
    public void accountStatus() {
        double expected = 0.0;
        double actual = account.AccountStatus();

        assertEquals(expected, actual, epsilon);
    }
//endregion AccountStatus

// region WithdarawFunds
    @Test
    public void withdrawFundsOnceInt() {
        double expected = 100 - 50;
        double actual = 0.0;

        account.DepositFunds(100);
        actual = account.WithdrawFunds(50);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void withdrawFundsMultipleInt() {
        double expected = 100 - 50;
        double actual = 0.0;

        account.DepositFunds(100);
        for(int i = 0; i < 50; i++)
        {
            actual = account.WithdrawFunds(1);
        }

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void withdrawFundsOnceFloatingPoint() {
        double expected = 100 - 0.1;
        double actual = 0.0;

        account.DepositFunds(100);
        actual = account.WithdrawFunds(0.1);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void withdrawFundsMultipleFloatingPoint() {
        double expected = 100 - (0.1 * 50);
        double actual = 0.0;

        account.DepositFunds(100);
        for(int i = 0; i < 50; i++)
        {
            actual = account.WithdrawFunds(0.1);
        }

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void withdrawFundsMoreThanAmount() {
        double expected = 100 - 200;
        double actual = 0.0;

        account.DepositFunds(100);
        actual = account.WithdrawFunds(200);

        assertNotEquals(expected, actual, epsilon);
    }

    @Test
    public void withdrawFundsBitMoreThanAmount() {
        double expected = 0.0;
        double actual = account.WithdrawFunds(0.000000001);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void withdrawFundsZero() {
        double expected = 0.0;
        double actual = account.WithdrawFunds(0.0);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void withdrawFundsAndZero() {
        double expected = 0.5;
        double actual = account.DepositFunds(0.5);
        actual = account.WithdrawFunds(0.0);

        assertEquals(expected, actual, epsilon);
    }
// endregion WithdrawFunds

    @Test
    public void zeroAfterDepositWithdraw() {
        double expected = 0.0;
        double actual = account.DepositFunds(100);
        actual = account.DepositFunds(100);

        actual = account.WithdrawFunds(100);
        actual = account.WithdrawFunds(100);
        actual = account.WithdrawFunds(100);

        actual = account.DepositFunds(100);
        actual = account.WithdrawFunds(100);

        assertEquals(expected, actual, epsilon);
    }

}