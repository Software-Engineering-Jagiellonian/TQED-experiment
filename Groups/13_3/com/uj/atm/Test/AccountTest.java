package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void AccountStatusTest() {
        IAccount account = new Account();
        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    @Test
    public void DepositFundsTest() {
        IAccount account = new Account();
        double amount = 100.0;
        assertEquals(account.DepositFunds(amount), amount, 0.0);
    }

    @Test
    public void WithdrawFundsTest() {
        IAccount account = new Account();
        double balance = account.AccountStatus();
        double amount = 100.0;
        assertEquals(account.WithdrawFunds(amount), balance, 0.0);
        account.DepositFunds(amount);
        assertEquals(account.WithdrawFunds(amount), balance, 0.0);
    }
}