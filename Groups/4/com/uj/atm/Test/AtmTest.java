package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

public class AtmTest {
    private IAtm iAtm;

    @Before
    public void init() {
        iAtm = new Atm();
    }

    /**
     * Test sprawdzajcy logowanie do konta
     * Wymiar: Z
     */
    @Test
    public void test1() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertTrue(iAtm.CheckPinAndLogIn(card, "1234"));
    }

    /**
     * Test sprawdzajcy logowanie do konta zlym pinem
     * Wymiar: Z
     */
    @Test
    public void test2() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertFalse(iAtm.CheckPinAndLogIn(card, "4321"));
    }

    /**
     * Test sprawdzajcy status konta po zalogowaniu sie
     * Wymiar: Z + Z
     */
    @Test
    public void test3() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertTrue(iAtm.CheckPinAndLogIn(card, "1234"));
        assertEquals(iAtm.AccountStatus(acc),0.0,0.0);
    }

    /**
     * Test sprawdzajcy status konta bedac niezalogowanym
     * Wymiar: Z
     */
    @Test(expected = SecurityException.class)
    public void test4() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        iAtm.AccountStatus(acc);
    }

    /**
     * Test sprawdzajcy zmiane pinu do karty
     * Wymiar: Z
     */
    @Test
    public void test5() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertTrue(iAtm.ChangePinCard(card,"1234","4321","4321"));
    }

    /**
     * Test sprawdzajcy wplate srodkow z karty bedac niezalogowanym
     * Wymiar: Z
     */
    @Test(expected = SecurityException.class)
    public void test6() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        iAtm.DepositFunds(card,15.0);
    }

    /**
     * Test sprawdzajcy wplate srodkow z karty o pustym saldzie po zalogowaniu
     * Wymiar: Z + Z
     */
    @Test
    public void test7() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertTrue(iAtm.CheckPinAndLogIn(card, "1234"));
        assertTrue(iAtm.DepositFunds(card,15.0));
    }

    /**
     * Test sprawdzajcy wyplate i wyplate srodkow bedac zalogowanym
     * Wymiar: Z + Z
     */
    @Test
    public void test8() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertTrue(iAtm.CheckPinAndLogIn(card, "1234"));
        assertTrue(iAtm.DepositFunds(card,15.0));
        assertTrue(iAtm.WithdrawFunds(card,15.0));
    }

    /**
     * Test sprawdzajcy wyplate majac nie wystarczajaca ilosc srodkow
     * Wymiar: Z
     */
    @Test
    public void test9() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertTrue(iAtm.CheckPinAndLogIn(card, "1234"));
        assertTrue(iAtm.DepositFunds(card,15.0));
        assertFalse(iAtm.WithdrawFunds(card,30.0));
    }

    /**
     * Test sprawdzajcy przelew srodkow z jednego konta do drugiego
     * Wymiar: Z
     */
    @Test
    public void test10() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        IAccount acc_dest = new Account();

        iAtm.CheckPinAndLogIn(card, "1234");
        iAtm.DepositFunds(card,25.0);

        assertTrue(iAtm.Transfer(card,acc_dest,20.0));
        assertEquals(acc_dest.AccountStatus(),20.0,0.0);
    }

    /**
     * Test sprawdzajcy przelew srodkow w przypadku braku srodkow
     * Wymiar: Z
     */
    @Test
    public void test11() {
        ICreditCard card = new CreditCard();
        card.Init("1234","1234");
        IAccount acc = new Account();
        card.AddAccount(acc);
        IAccount acc_dest = new Account();

        iAtm.CheckPinAndLogIn(card, "1234");
        iAtm.DepositFunds(card,25.0);

        assertFalse(iAtm.Transfer(card,acc_dest,30.0));
    }
}