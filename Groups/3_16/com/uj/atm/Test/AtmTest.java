package com.uj.atm.Test;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class AtmTest {
    private final double funds = 10000;
    private final String pin = "1234";
    private final ICreditCard creditCard = new CreditCard();
    private final IAccount account = new Account();

    @Before
    public void setUp() {
        this.creditCard.Init(this.pin, this.pin);
        this.creditCard.AddAccount(this.account);
        this.account.DepositFunds(this.funds);
    }

    /**
     * Test sprawdzający czy podany pin jest poprawnym z tym karty kredytowej.
     * D - poprawny pin
     */
    @Test
    public void test01() {
        IAtm atm = new Atm();
        final boolean result = atm.CheckPinAndLogIn(this.creditCard, this.pin);
        assertEquals(result, true);
    }

    /**
     * Test sprawdzający czy podany pin jest poprawnym z tym karty kredytowej.
     * D + I - niepoprawny pin
     */
    @Test
    public void test02() {
        IAtm atm = new Atm();
        final boolean result = atm.CheckPinAndLogIn(this.creditCard, (new StringBuilder()).append(this.pin).reverse().toString());
        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy uda się zwrócić poprawnie fundusze konta.
     * Z - sprawdzenie funduszy
     */
    @Test
    public void test03() {
        IAtm atm = new Atm();
        double result = atm.AccountStatus(this.account);
        assertTrue(result == this.funds);
    }

    /**
     * Test sprawdzający czy uda się zmienić pin karty kredytowej.
     * D + D - stary pin, nowy pin
     */
    @Test
    public void test04() {
        IAtm atm = new Atm();
        String pin = "1000";
        boolean result = atm.ChangePinCard(this.creditCard, this.pin, pin, pin);
        assertTrue(result);
    }

    /**
     * Test sprawdzający czy nie uda się zmienić pin karty kredytowej.
     * D + D + I - stary pin, nowy niepoprawny pin
     */
    @Test
    public void test05() {
        IAtm atm = new Atm();
        String pin = "10000";
        boolean result = atm.ChangePinCard(this.creditCard, this.pin, pin, pin);
        assertFalse(result);
    }

    /**
     * Test sprawdzający czy uda się wpłacić pieniądze na kartę.
     * D + Z - kwota do wpłaty, wpłata na konto
     */
    @Test
    public void test06() {
        IAtm atm = new Atm();
        double funds = 10000;
        boolean result = atm.DepositFunds(this.creditCard, funds);
        assertTrue(result);
    }

    /**
     * Test sprawdzający czy uda się wpłacić pieniądze na nieistniejącą kartę.
     * D + I + Z - kwota do wpłaty, wpłata nieistniejące na konto
     */
    @Test
    public void test07() {
        IAtm atm = new Atm();
        double funds = 10000;
        boolean result = atm.DepositFunds(null, funds);
        assertFalse(result);
    }

    /**
     * Test sprawdzający czy uda się wypłacić pieniądze z karty.
     * D + Z - kwota do wypłaty, wypłata z konta
     */
    @Test
    public void test08() {
        IAtm atm = new Atm();
        double funds = 1000;
        boolean result = atm.WithdrawFunds(this.creditCard, funds);
        assertTrue(result);
    }

    /**
     * Test sprawdzający czy uda się wypłacić pieniądze z nieistniejącej karty.
     * D + Z - kwota do wypłaty, wypłata z nieistniejącego konta
     */
    @Test
    public void test09() {
        IAtm atm = new Atm();
        double funds = 1000;
        boolean result = atm.WithdrawFunds(null, funds);
        assertFalse(result);
    }

    /**
     * Test sprawdzający czy uda się przelać pieniądze z danej karty na dane konto.
     * D + D + D + Z - karta kredytowa, konto, kwota, przelew
     */
    @Test
    public void test10() {
        IAtm atm = new Atm();
        double funds = 1000;
        IAccount account1 = new Account();
        boolean result = atm.Transfer(this.creditCard, account1, funds);
        assertTrue(result);
    }

    /**
     * Test sprawdzający czy uda się przelać pieniądze z nieistniejącej karty na dane konto.
     * D + I + D + D + Z - nieistniejące karta kredytowa, konto, kwota, przelew
     */
    @Test
    public void test11() {
        IAtm atm = new Atm();
        double funds = 1000;
        IAccount account1 = new Account();
        boolean result = atm.Transfer(null, account1, funds);
        assertFalse(result);
    }

    /**
     * Test sprawdzający czy uda się przelać pieniądze z karty na nieistniejące konto.
     * D + D + I + D + Z - karta kredytowa, nieistniejące konto, kwota, przelew
     */
    @Test
    public void test12() {
        IAtm atm = new Atm();
        double funds = 1000;
        IAccount account1 = new Account();
        boolean result = atm.Transfer(this.creditCard, null, funds);
        assertFalse(result);
    }

    /**
     * Test sprawdzający czy uda się przelać zbyt wiele pieniądzy z danej karty na dane konto.
     * D + D + D + Z - karta kredytowa, konto, zbyt duża kwota, przelew
     */
    @Test
    public void test13() {
        IAtm atm = new Atm();
        double funds = this.funds + 1000;
        IAccount account1 = new Account();
        boolean result = atm.Transfer(this.creditCard, account1, funds);
        assertFalse(result);
    }
}