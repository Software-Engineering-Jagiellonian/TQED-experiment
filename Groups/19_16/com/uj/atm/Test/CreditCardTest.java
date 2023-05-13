package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class CreditCardTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Test sprawdzający czy udało się poprawnie zainicjalizować kartę.
     * D  - nowy pin do konta
     */
    @Test
    public void test01() {
        ICreditCard creditCard = new CreditCard();
        final String pin = "1234";
        creditCard.Init(pin, pin);
        assertEquals("", outputStreamCaptor.toString().replaceAll("\\r\\n", ""));
    }

    /**
     * Test sprawdzający czy nie udało się zainicjalizować kartę.
     * D  - nowy pin do konta będący wartością null
     */
    @Test
    public void test02() {
        ICreditCard creditCard = new CreditCard();
        final String pin = null;
        creditCard.Init(pin, pin);
        assertEquals("Nie udalo sie zmienic pinu", outputStreamCaptor.toString().replaceAll("\\r\\n", ""));
    }

    /**
     * Test sprawdzający czy nie udało się zainicjalizować kartę.
     * D + I  - nowy pin do konta będący zbyt długi
     */
    @Test
    public void test03() {
        ICreditCard creditCard = new CreditCard();
        final String pin = "12345";
        creditCard.Init(pin, pin);
        assertEquals("Nie udalo sie zmienic pinu", outputStreamCaptor.toString().replaceAll("\\r\\n", ""));
    }

    /**
     * Test sprawdzający czy nie udało się zainicjalizować kartę.
     * D + I  - nowy pin do konta zawierający litery
     */
    @Test
    public void test04() {
        ICreditCard creditCard = new CreditCard();
        final String pin = "abcd";
        creditCard.Init(pin, pin);
        assertEquals("Nie udalo sie zmienic pinu", outputStreamCaptor.toString().replaceAll("\\r\\n", ""));
    }

    /**
     * Test sprawdzający czy udało się poprawnie zmienić pin do karty.
     * D + D  - stary pin do konta, nowy pin do konta
     */
    @Test
    public void test05() {
        ICreditCard creditCard = new CreditCard();
        final String oldPin = "0000";
        final String pin = "1234";
        final boolean result = creditCard.ChangePin(oldPin, pin, pin);
        assertEquals(result, true);
    }

    /**
     * Test sprawdzający czy niepoprawny pin zmienia dane karty.
     * D + D  - stary pin do konta, nowy pin do konta równy null
     */
    @Test
    public void test06() {
        ICreditCard creditCard = new CreditCard();
        final String oldPin = "0000";
        final String pin = null;
        final boolean result = creditCard.ChangePin(oldPin, pin, pin);
        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy niepoprawny pin zmienia dane karty.
     * D + D + I  - stary pin do konta, nowy zbyt długi pin do konta
     */
    @Test
    public void test07() {
        ICreditCard creditCard = new CreditCard();
        final String oldPin = "0000";
        final String pin = "12345";
        final boolean result = creditCard.ChangePin(oldPin, pin, pin);
        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy niepoprawny pin zmienia dane karty.
     * D + D + I  - stary pin do konta, nowy pin do konta zawierający litery
     */
    @Test
    public void test08() {
        ICreditCard creditCard = new CreditCard();
        final String oldPin = "0000";
        final String pin = "abcd";
        final boolean result = creditCard.ChangePin(oldPin, pin, pin);
        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy niepoprawny pin zmienia dane karty.
     * D + I + D  - stary pin do konta różny od starego, nowy pin do konta
     */
    @Test
    public void test09() {
        ICreditCard creditCard = new CreditCard();
        final String oldPin = "0001";
        final String pin = "1234";
        final boolean result = creditCard.ChangePin(oldPin, pin, pin);
        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy uda się wielokrotna zmiana kodu pin.
     * D + D + C - stary pin do konta, nowy różny od starego pin
     */
    @Test
    public void test10() {
        ICreditCard creditCard = new CreditCard();
        final String oldPin = "0000";
        final String pin1 = "1000";
        final String pin2 = "2000";
        final String pin3 = "3000";

        final boolean result1 = creditCard.ChangePin(oldPin, pin1, pin1);
        assertEquals(result1, true);

        final boolean result2 = creditCard.ChangePin(pin1, pin2, pin2);
        assertEquals(result2, true);

        final boolean result3 = creditCard.ChangePin(pin2, pin3, pin3);
        assertEquals(result3, true);
    }

    /**
     * Test sprawdzający czy poprawny kod pin.
     * D  - poprawny pin do konta
     */
    @Test
    public void test11() {
        ICreditCard creditCard = new CreditCard();
        final String pin = "0000";
        final boolean result = creditCard.IsPinValid(pin);
        assertEquals(result, true);
    }

    /**
     * Test sprawdzający czy niepoprawny kod pin.
     * D + I  - niepoprawny pin do konta
     */
    @Test
    public void test12() {
        ICreditCard creditCard = new CreditCard();
        final String pin = "1234";
        final boolean result = creditCard.IsPinValid(pin);
        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy uda się powiązać kartę z poprawnym kontem.
     * D - poprawne konto
     */
    @Test
    public void test13() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        assertEquals(account, creditCard.GetAccount());
    }

    /**
     * Test sprawdzający czy uda się powiązać kartę z niepoprawnym kontem.
     * D - niepoprawne konto
     */
    @Test
    public void test14() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = null;
        creditCard.AddAccount(account);
        assertEquals(account, creditCard.GetAccount());
    }

    /**
     * Test sprawdzający czy uda się wpłacić daną kwotę na konto poprzez kartę.
     * D + Z - kwota, wpłata na konto
     */
    @Test
    public void test15() {
        final double funds = 10000;
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);

        final boolean result = creditCard.DepositFunds(funds);

        assertEquals(result, true);
    }

    /**
     * Test sprawdzający czy uda się wpłacić dużą kwotę na konto poprzez kartę.
     * D + I + Z - duża kwota, wpłata na konto
     */
    @Test
    public void test16() {
        final double funds = 656464534;
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);

        final boolean result = creditCard.DepositFunds(funds);

        assertEquals(result, true);
    }

    /**
     * Test sprawdzający czy uda się wpłacić daną kwotę wiele razy na konto poprzez kartę.
     * D + Z + C - kwota, wielokrotna wpłata na konto
     */
    @Test
    public void test17() {
        final double funds = 10000;
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);

        final boolean result1 = creditCard.DepositFunds(funds);
        final boolean result2 = creditCard.DepositFunds(funds);
        final boolean result3 = creditCard.DepositFunds(funds);
        final boolean result4 = creditCard.DepositFunds(funds);
        final boolean result5 = creditCard.DepositFunds(funds);

        assertEquals(result1, true);
        assertEquals(result2, true);
        assertEquals(result3, true);
        assertEquals(result4, true);
        assertEquals(result5, true);
    }

    /**
     * Test sprawdzający czy uda się wpłacić daną kwotę na nieistniejące konto poprzez kartę.
     * D + I + Z - kwota, wpłata na niepoprawne konto
     */
    @Test
    public void test18() {
        final double funds = 10000;
        ICreditCard creditCard = new CreditCard();
        IAccount account = null;
        creditCard.AddAccount(account);

        final boolean result = creditCard.DepositFunds(funds);

        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy uda się wypłacić daną kwotę z konta poprzez kartę.
     * D + Z - kwota, wypłata z konta
     */
    @Test
    public void test19() {
        final double funds = 10000;
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);

        creditCard.DepositFunds(funds);
        final boolean result = creditCard.WithdrawFunds(funds / 10);

        assertEquals(result, true);
    }

    /**
     * Test sprawdzający czy uda się wypłacić daną kwotę wiele razy z konta poprzez kartę.
     * D + Z + C - kwota, wielokrtona wypłata z konta
     */
    @Test
    public void test20() {
        final double funds = 10000;
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);

        creditCard.DepositFunds(funds);
        final boolean result1 = creditCard.WithdrawFunds(funds / 10);
        final boolean result2 = creditCard.WithdrawFunds(funds / 10);
        final boolean result3 = creditCard.WithdrawFunds(funds / 10);
        final boolean result4 = creditCard.WithdrawFunds(funds / 10);
        final boolean result5 = creditCard.WithdrawFunds(funds / 10);

        assertEquals(result1, true);
        assertEquals(result2, true);
        assertEquals(result3, true);
        assertEquals(result4, true);
        assertEquals(result5, true);
    }

    /**
     * Test sprawdzający czy uda się wypłacić zbyt dużą kwotę z konta poprzez kartę.
     * D + I + Z - zbyt duża kwota, wypłata z konta
     */
    @Test
    public void test21() {
        final double funds = 10000;
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);

        creditCard.DepositFunds(funds);
        final boolean result = creditCard.WithdrawFunds(funds * 10);

        assertEquals(result, false);
    }

    /**
     * Test sprawdzający czy uda się wypłacić daną kwotę z nieistniejącego konta poprzez kartę.
     * D + I + Z - kwota, wypłata z nieistniejącego konta
     */
    @Test
    public void test22() {
        final double funds = 10000;
        ICreditCard creditCard = new CreditCard();
        IAccount account = null;
        creditCard.AddAccount(account);

        creditCard.DepositFunds(funds);
        final boolean result = creditCard.WithdrawFunds(funds / 10);

        assertEquals(result, false);
    }
}