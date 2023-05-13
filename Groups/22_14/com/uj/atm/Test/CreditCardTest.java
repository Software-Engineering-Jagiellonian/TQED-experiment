package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    @Test
    public void test01() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertTrue(creditCard.IsPinValid("1234"));
    }
    @Test
    public void test02() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.ChangePin("1234", "1235", "2222");
        assertTrue(creditCard.IsPinValid("1234"));
    }
    @Test
    public void test03() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "124");
        assertFalse(creditCard.IsPinValid("1234"));
    }
    @Test
    public void test04() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("124", "124");
        assertFalse(creditCard.IsPinValid("1234"));
    }
    @Test
    public void test05() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.ChangePin("1234", "2222", "2222");
        assertTrue(creditCard.IsPinValid("2222"));
    }
    @Test
    public void test06() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.ChangePin("1234", "222a", "222a");
        assertTrue(creditCard.IsPinValid("1234"));
    }
    @Test
    public void test07() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        assertEquals(account, creditCard.GetAccount());
    }
    @Test
    public void test08() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(80);
        assertEquals(80, creditCard.GetAccount().AccountStatus(), 0.0);
    }
    @Test
    public void test09() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(80);
        creditCard.WithdrawFunds(10);
        assertEquals(70, creditCard.GetAccount().AccountStatus(), 0.0);
    }
    @Test
    public void test10() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init(null , null);
        creditCard.Init("1234", "1234");
        creditCard.ChangePin("1234", null, "2222");
        assertTrue(creditCard.IsPinValid("1234"));
        Account account = new Account();
        Account account2 = new Account();
        creditCard.AddAccount(account);
        creditCard.AddAccount(account2);
        assertEquals(account, creditCard.GetAccount());
    }
    @Test
    public void test11() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = creditCard.DepositFunds(80);
        assertFalse(result);
        result = creditCard.WithdrawFunds(80);
        assertFalse(result);
    }
    @Test
    public void test12() {
        CreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("1234"));
    }
}
