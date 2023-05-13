package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    /**
     * D - poprawne dane inicjalizacji karty
     */
    @Test
    public void TestInitHappyPath() {
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");

        assertTrue(card.IsPinValid("1234"));
    }

    /**
     * D - niepoprawne dane inicjalizacji karty
     */
    @Test
    public void TestInitNotMatchingPins() {
        CreditCard card = new CreditCard();
        card.Init("1234", "2123");

        assertFalse(card.IsPinValid("1234"));
        assertFalse(card.IsPinValid("2123"));
    }

    /**
     * D - niepoprawne dane inicjalizacji karty
     */
    @Test
    public void TestInitNullPins() {
        CreditCard card = new CreditCard();
        card.Init(null, null);

        assertFalse(card.IsPinValid(null));
    }

    /**
     * D - niepoprawne dane inicjalizacji karty
     */
    @Test
    public void TestInitIllegalPinsLetters() {
        CreditCard card = new CreditCard();
        card.Init("abcd", "abcd");

        assertFalse(card.IsPinValid("abcd"));
    }

    /**
     * D - niepoprawne dane inicjalizacji karty
     */
    @Test
    public void TestInitIllegalPinsShort() {
        CreditCard card = new CreditCard();
        card.Init("123", "123");

        assertFalse(card.IsPinValid("123"));
    }

    /**
     * D - poprawne dane do zmiany pinu karty
     */
    @Test
    public void TestChangePinHappyPath() {
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");

        assertTrue(card.IsPinValid("1234"));
        boolean result = card.ChangePin("1234", "4321", "4321");
        assertTrue(result);
        assertTrue(card.IsPinValid("4321"));
        assertFalse(card.IsPinValid("1234"));
    }


    /**
     * D - niepoprawne dane do zmiany pinu karty - ten sam pin
     */
    @Test
    public void TestChangePinSamePin() {
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");

        assertTrue(card.IsPinValid("1234"));
        boolean result = card.ChangePin("1234", "1234", "1234");
        assertFalse(result);
        assertTrue(card.IsPinValid("1234"));
    }

    /**
     * D - niepoprawne dane do zmiany pinu karty - null zamist pinu
     */
    @Test
    public void TestChangePinNullPin() {
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");

        assertTrue(card.IsPinValid("1234"));
        boolean result = card.ChangePin("1234", "2323", null);
        assertFalse(result);
        assertTrue(card.IsPinValid("1234"));
    }

    /**
     * D - poprawne dane do dodania konta do karty
     */
    @Test
    public void TestAddAccountHappyPath() {
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);

        assertTrue(card.GetAccount() == account);
    }

    /**
     * D - niepoprawne dane do dodania konta do karty - null zamist konta
     */
    @Test
    public void TestAddAccountNullAccount() {
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");

        card.AddAccount(null);
        assertNull(card.GetAccount());

        card.AddAccount(account);
        assertSame(card.GetAccount(), account);
    }


    /**
     * D - proba dodania drugiego konta do karty
     */
    @Test
    public void TestAddAccountAndOtherAccount() {
        Account account = new Account();
        Account otherAccount = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");

        card.AddAccount(account);
        assertSame(card.GetAccount(), account);

        card.AddAccount(otherAccount);
        assertSame(card.GetAccount(), account);

        card.AddAccount(null);
        assertSame(card.GetAccount(), account);
    }

    /**
     * D - poprawne dane do wplacenia i wyplacenia pieniedzy na konto
     */
    @Test
    public void TestDepositFundsHappyPath() {
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        card.DepositFunds(500);
        assertEquals(500, card.GetAccount().AccountStatus(), 0.001);

        boolean status = card.WithdrawFunds(200);
        assertTrue(status);
        assertEquals(card.GetAccount().AccountStatus(), 300, 0.001);
    }


    /**
     * D + D - poprawne dane do wplacenia i niepoprawne dane wyplacenia
     */
    @Test
    public void TestDepositFundsWithdrawNegative() {
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        card.DepositFunds(500);
        assertEquals(500, card.GetAccount().AccountStatus(), 0.001);

        boolean status = card.WithdrawFunds(-200);
        assertFalse(status);
        assertEquals(card.GetAccount().AccountStatus(), 500, 0.001);
    }

    /**
     * D - poprawne dane do wplacenia i wyplacenia pieniedzy na konto
     */
    @Test
    public void TestDepositNegativeFunds() {
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        card.DepositFunds(500);
        assertEquals(500, card.GetAccount().AccountStatus(), 0.001);

        boolean status = card.DepositFunds(-200);
        assertFalse(status);
        assertEquals(card.GetAccount().AccountStatus(), 500, 0.001);
    }

}