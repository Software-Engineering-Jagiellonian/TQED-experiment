package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
    @Test
    public void test01() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = atm.CheckPinAndLogIn(creditCard, "1234");
        assertTrue(result);
    }
    @Test
    public void test02() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = atm.CheckPinAndLogIn(creditCard, "0000");
        assertFalse(result);
    }
    @Test
    public void test03() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        atm.CheckPinAndLogIn(creditCard, "1234");
        atm.ChangePinCard(creditCard,"1234", "0000", "0000");
        boolean result = atm.CheckPinAndLogIn(creditCard, "0000");
        assertTrue(result);
    }
    @Test
    public void test04() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        atm.CheckPinAndLogIn(creditCard, "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        atm.DepositFunds(creditCard, 10);
        assertEquals(10, creditCard.GetAccount().AccountStatus(), 0.0);
    }
    @Test
    public void test05() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        atm.CheckPinAndLogIn(creditCard, "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(20);
        atm.WithdrawFunds(creditCard, 10);
        assertEquals(10, atm.AccountStatus(account), 0.0);
    }
    @Test
    public void test06() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        atm.CheckPinAndLogIn(creditCard, "1234");
        Account account = new Account();
        Account account2 = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(20);
        account2.DepositFunds(59);
        atm.Transfer(creditCard, account2, 20);
        assertEquals(79, account2.AccountStatus(), 0.0);
    }
    @Test
    public void test07() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        atm.CheckPinAndLogIn(creditCard, "1234");
        Account account = new Account();
        Account account2 = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(20);
        account2.DepositFunds(59);
        boolean result = atm.Transfer(creditCard, account2, -20);
        assertEquals(59, account2.AccountStatus(), 0.0);
        assertFalse(result);
    }
    @Test
    public void test08() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        atm.CheckPinAndLogIn(creditCard, "1234");
        Account account = new Account();
        Account account2 = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(10);
        account2.DepositFunds(59);
        boolean result = atm.Transfer(creditCard, account2, 20);
        assertEquals(59, account2.AccountStatus(), 0.0);
        assertFalse(result);
    }
}
