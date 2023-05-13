package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void test01() {
        Account account = new Account();
        assertEquals(0, account.AccountStatus(), 0.0);
    }
    @Test
    public void test02() {
        Account account = new Account();
        account.DepositFunds(80);
        assertEquals(80, account.AccountStatus(), 0.0);
    }
    @Test
    public void test03() {
        Account account = new Account();
        account.WithdrawFunds(80);
        assertEquals(-80, account.AccountStatus(), 0.0);
    }
    @Test
    public void test04() {
        Account account = new Account();
        account.DepositFunds(80.4);
        account.DepositFunds(80.5);
        account.DepositFunds(80.1);
        account.WithdrawFunds(80);
        account.WithdrawFunds(80);
        account.WithdrawFunds(80);
        assertEquals(1, account.AccountStatus(), 0.0);
    }
    @Test
    public void test05() {
        Account account = new Account();
        account.DepositFunds(-80.4);
        account.DepositFunds(-80.5);
        account.DepositFunds(-80.1);
        account.WithdrawFunds(-80);
        account.WithdrawFunds(-80);
        account.WithdrawFunds(-80);
        assertEquals(0, account.AccountStatus(), 0.0);
    }
}
