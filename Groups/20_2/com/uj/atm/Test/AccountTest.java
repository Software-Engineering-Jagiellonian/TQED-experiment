package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	Account account = new Account();

//	Przykłądowy test
//    @Test
//    public void test01() {
//        System.out.println("jestem fajnym testem?");
//        assertTrue(true);
//    }

    @Test
    public void testDefaultAccountStatus() {
        assertTrue(account.AccountStatus() == 0);
    }

    @Test
    public void testDepositFunds() {
        account.DepositFunds(100);
        account.DepositFunds(50);
        assertTrue(account.AccountStatus() == 150);
    }

    @Test
    public void testDepositNoFunds() {
        account.DepositFunds(0);
        assertTrue(account.AccountStatus() == 0);
    }

    @Test
    public void testWithdrawAFunds() {
        account.DepositFunds(150);
        account.WithdrawFunds(50);
        assertTrue(account.AccountStatus() == 100);
    }
}