package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    ICreditCard card = new CreditCard();
	//Przykłądowy test
    @Test
    public void test01() {
        card.Init("0123", "1200");
        boolean newPinOfUninitialized = card.ChangePin("0123", "2455", "2455");
        boolean newPinOfUninitializedNull = false;
        try{
            newPinOfUninitializedNull = card.ChangePin(null, "1234", "1234");
        }catch (NullPointerException e){e.printStackTrace();}
        assertFalse(newPinOfUninitialized);
        assertFalse(newPinOfUninitializedNull);
    }

    @Test
    public void test02() {
        card.Init("abcd", "abcd");
        assertFalse(card.IsPinValid("abcd"));
    }

    @Test
    public void test03() {
        card.Init("12345", "12345");
        assertFalse(card.IsPinValid("12345"));
    }

    @Test
    public void test04() {
        card.Init("1234", "1234");
        assertTrue(card.IsPinValid("1234"));
    }

    @Test
    public void test05() {
        card.Init("123.4", "123.4");
        assertFalse(card.IsPinValid("123.4"));
    }

    @Test
    public void test06() {
        card.Init("1234", "1234");
        boolean success = card.ChangePin("1234", "12345", "12345");
        assertFalse(success);
        assertTrue(card.IsPinValid("1234"));
    }

    @Test
    public void test07() {
        card.Init("1234", "1234");
        boolean success = card.ChangePin("1234", "123.5", "123.5");
        assertFalse(success);
        assertTrue(card.IsPinValid("1234"));
    }

    @Test
    public void test08() {
        card.Init("1234", "1234");
        boolean success = card.ChangePin("1234", "abcd", "abcd");
        assertFalse(success);
        assertTrue(card.IsPinValid("1234"));
    }

    @Test
    public void test09() {
        card.Init("1234", "1234");
        boolean success = card.ChangePin("1233", "7890", "7890");
        assertFalse(success);
        assertTrue(card.IsPinValid("1234"));
    }

    @Test
    public void test10() {
        card.Init("1234", "1234");
        boolean success = card.ChangePin("1234", "7890", "7890");
        assertTrue(success);
        assertTrue(card.IsPinValid("7890"));
    }

    @Test
    public void test11() {
        card.Init("1234", "1234");
        boolean success = card.ChangePin("1234", "7891", "7890");
        assertFalse(success);
        assertTrue(card.IsPinValid("1234"));
    }

    @Test
    public void test12() {
        IAccount account = new Account();
        card.AddAccount(account);
        assertEquals(account, card.GetAccount());
    }

    @Test
    public void test13() {
        IAccount account = new Account();
        card.AddAccount(account);
        assertEquals(account, card.GetAccount());
        card.DepositFunds(100);
        card.WithdrawFunds(90);
        assertTrue(card.GetAccount().AccountStatus() == 10);
    }

    @Test
    public void test14() {
        IAccount account = new Account();
        card.AddAccount(account);
        assertEquals(account, card.GetAccount());
        card.DepositFunds(100);
        card.WithdrawFunds(90);
        assertTrue(card.GetAccount().AccountStatus() == 10);
        IAccount account1 = new Account();
        card.AddAccount(account1);
        assertEquals(card.GetAccount(), account);
        assertTrue(card.GetAccount().AccountStatus() == 10);
    }


}