package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {
    CreditCard defaultCard = new CreditCard();

	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    @Test
    public void InitCard() {
        defaultCard.Init("2222", "2222");
    }

    @Test
    public void changePin() {
        boolean success = defaultCard.ChangePin("0000", "1111", "1111");
        assertTrue(success);
    }

    @Test
    public void createPinTwoTimes() {
        assertTrue(defaultCard.ChangePin("0000", "1111", "1111"));
        assertTrue(defaultCard.ChangePin("1111", "2222", "2222"));
    }

    @Test
    public void isPinValid() {
        assertTrue(defaultCard.IsPinValid("0000"));
        assertFalse(defaultCard.IsPinValid("2222"));
        assertFalse(defaultCard.IsPinValid("222"));
        assertFalse(defaultCard.IsPinValid("22222"));
        assertFalse(defaultCard.IsPinValid("22a2"));
        assertFalse(defaultCard.IsPinValid(""));
    }

    @Test
    public void changePinIncorrectOldPin() {
        boolean success = defaultCard.ChangePin("0001", "1111", "1111");
        assertFalse(success);
    }

    @Test
    public void changePinSuccessThanIncorrectOldPin() {
        assertTrue(defaultCard.ChangePin("0000", "1111", "1111"));
        assertFalse(defaultCard.ChangePin("0000", "2222", "2222"));
    }

    @Test
    public void changePinIncorrectNewPinMismatch() {
        assertFalse(defaultCard.ChangePin("0000", "1111", "1112"));
    }

    @Test
    public void changePinSuccessThanIncorrectNewPinMismatch() {
        defaultCard.ChangePin("0000", "1111", "1111");
        assertFalse(defaultCard.ChangePin("0000", "2222", "2223"));
    }

    @Test
    public void changePinToTheSamePin() {
        defaultCard.ChangePin("0000", "0000", "0000");
    }

    @Test
    public void addDefaultAccount() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        assertEquals(defaultCard.GetAccount(), account);
    }

    @Test
    public void addNullAccount() {
        defaultCard.AddAccount(null);
        assertNull(defaultCard.GetAccount());
    }

    @Test
    public void doNotAddAccount() {
        assertNull(defaultCard.GetAccount());
    }

    @Test
    public void addAccountWithFunds() {
        IAccount account = new Account();
        account.DepositFunds(200.0);
        defaultCard.AddAccount(account);
        assertEquals(defaultCard.GetAccount(), account);
    }

    @Test
    public void addTwoAccounts() {
        IAccount account1 = new Account();
        IAccount account2 = new Account();

        account1.DepositFunds(200.0);
        account2.DepositFunds(300.0);

        defaultCard.AddAccount(account1);
        defaultCard.AddAccount(account2);

        assertEquals(defaultCard.GetAccount(), account1);
    }

    @Test
    public void depositFundsWithoutAddingAccount() {
        boolean success = defaultCard.DepositFunds(200);
        assertFalse(success);
    }

    @Test
    public void depositFunds() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        boolean success = defaultCard.DepositFunds(200);
        assertTrue(success);
    }

    @Test
    public void depositFundsThanWithDraw() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        defaultCard.DepositFunds(200);
        boolean success = defaultCard.WithdrawFunds(100);
        assertTrue(success);
    }

    @Test
    public void depositFundsThanWithDrawNotEnoughFunds() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        defaultCard.DepositFunds(100);
        boolean success = defaultCard.WithdrawFunds(200);
        assertFalse(success);
    }

    @Test
    public void depositNoFunds() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        boolean success = defaultCard.DepositFunds(0);
        assertTrue(success);
    }

    @Test
    public void withdrawNoFunds() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        boolean success = defaultCard.WithdrawFunds(0);
        assertTrue(success);
    }

    @Test
    public void withdrawFundsWithoutAddingAccount() {
        boolean success = defaultCard.WithdrawFunds(0);
        assertFalse(success);
    }

    @Test
    public void withdrawFundsThanDeposit() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        boolean success = defaultCard.WithdrawFunds(100);
        defaultCard.DepositFunds(200);
        assertFalse(success);
        assertEquals(200, defaultCard.GetAccount().AccountStatus(), 1e-8);
    }

    @Test
    public void depositFundsThanChangePin() {
        IAccount account = new Account();
        defaultCard.AddAccount(account);
        defaultCard.DepositFunds(200);
        defaultCard.ChangePin("0000", "1111", "1111");
        assertEquals(200, defaultCard.GetAccount().AccountStatus(), 1e-8);
    }
}