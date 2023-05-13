package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    IAccount account = new Account();

    @Test
    public void test01() {
        assertTrue(account.AccountStatus() == 0.0);
    }

    @Test
    public void test02() {
        account.WithdrawFunds(100.0);
        assertTrue(account.AccountStatus() == 0.0);
    }

    @Test
    public void test03() {
        account.DepositFunds(100.0);
        assertTrue(account.AccountStatus() == 100.0);
    }

    @Test
    public void test04() {
        account.DepositFunds(100);
        assertTrue(account.AccountStatus() == 100.0);
    }

    @Test
    public void test05() {
        account.DepositFunds(100);
        account.WithdrawFunds(40);
        assertTrue(account.AccountStatus() == 60.0);
    }

    @Test
    public void test06() {
        account.DepositFunds(100);
        account.WithdrawFunds(120);
        assertTrue(account.AccountStatus() == 100.0);
    }
}