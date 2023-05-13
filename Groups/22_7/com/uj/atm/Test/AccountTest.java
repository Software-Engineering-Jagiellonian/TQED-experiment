package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class AccountTest {
    private IAccount account;

    @Before
    public void BeforeEach() {
        account = new Account();
    }

    // A - Status
    @Test
    public void GetStatus() {
        assertEquals(account.AccountStatus(), 0.0, 0.001);
    }

    // D - +, A - Deposit, Status
    @Test
    public void GetStatusAfterDeposit() {
        account.DepositFunds(1.0);
        assertEquals(account.AccountStatus(), 1.0, 0.001);
    }

    //D - -, A - Deposit
    @Test(expected = IllegalArgumentException.class)
    public void DepositNegativeAmount() {
        account.DepositFunds(-1.0);
    }

    //D - 0, A - Deposit
    @Test
    public void Deposit0() {
        assertEquals(account.DepositFunds(0.0), 0.0, 0.001);
    }

    //D - +, A - Deposit
    @Test
    public void DepositPositive() {
        assertEquals(account.DepositFunds(2.0), 2.0, 0.001);
    }

    //D - -, A - Withdraw
    @Test(expected = IllegalArgumentException.class)
    public void WithdrawNegativeAmount() {
        account.WithdrawFunds(-1.0);
    }

    //D - >, A - Withdraw
    @Test(expected = IllegalArgumentException.class)
    public void WithdrawMoreThanStatus() {
        account.WithdrawFunds(1.0);
    }

    //D - 0, A - Deposit
    @Test
    public void Withdraw0() {
        assertEquals(account.DepositFunds(0.0), 0.0, 0.001);
    }

    //D - <, A - Deposit
    @Test
    public void WithdrawPositive() {
        account.DepositFunds(2.0);
        assertEquals(account.WithdrawFunds(1.0), 1.0, 0.001);
    }

    // D - +, A - Deposit, Withdraw, Status
    @Test
    public void GetStatusAfterDepositWithdraw() {
        account.DepositFunds(2.0);
        account.WithdrawFunds(1.0);
        assertEquals(account.AccountStatus(), 1.0, 0.001);
    }

    // Time impact impossible
}