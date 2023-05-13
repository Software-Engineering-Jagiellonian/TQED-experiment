package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	
	private Account account;

    @Before
    public void setUp() {
        this.account = new Account();
    }

    @Test
    public void testDefaultBalance() {
        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    @Test
    public void testDepositNegativeAmount() {
        assertEquals(0.0, account.DepositFunds(-1.0), 0.0);
    }

    @Test
    public void testDepositPositiveAmount() {
        assertEquals(1.0, account.DepositFunds(1.0), 0.0);
    }

    @Test
    public void testWithdrawNegativeAmount() {
        assertEquals(0.0, account.WithdrawFunds(-1.0), 0.0);
    }

    @Test
    public void testWithdrawMoreMoneyThanOnTheAccount() {
        account.DepositFunds(1.0);
        assertEquals(1.0, account.WithdrawFunds(2.0), 0.0);
    }

    @Test
    public void testWithdrawGoodAmount() {
        account.DepositFunds(1.0);
        assertEquals(0.0, account.WithdrawFunds(1.0), 0.0);
    }
}