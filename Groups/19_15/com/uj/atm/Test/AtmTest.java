package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {

    /**
     * D - poprawna kombinacja danych
     */
    @Test
    public void TestWithdrawHappyPath() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.WithdrawFunds(card, 200);
        assertTrue(status);
        assertEquals(atm.AccountStatus(account), 300, 0.001);
    }

    /**
     * D - niepoprawna kombinacja danych
     */
    @Test
    public void TestWithdrawTooMuch() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.WithdrawFunds(card, 600);
        assertFalse(status);
        assertEquals(atm.AccountStatus(account), 500, 0.001);
    }

    /**
     * D - niepoprawna kombinacja danych
     */
    @Test
    public void TestWithdrawNegative() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.WithdrawFunds(card, -5);
        assertFalse(status);
        assertEquals(atm.AccountStatus(account), 500, 0.001);
    }

    /**
     * D - niepoprawna kombinacja danych
     */
    @Test
    public void TestWithdrawZero() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.WithdrawFunds(card, 0);
        assertFalse(status);
        assertEquals(atm.AccountStatus(account), 500, 0.001);
    }

    /**
     * D - niepoprawna kombinacja danych do zalogowania
     */
    @Test
    public void TestWrongPinLogin() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.CheckPinAndLogIn(card, "4321");
        assertFalse(status);
    }

    /**
     * D - poprawna kombinacja danych do zalogowania
     */
    @Test
    public void TestCorrectPinLogin() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.CheckPinAndLogIn(card, "1234");
        assertTrue(status);
    }

    /**
     * D - poprawna kombinacja danych do zmiany pinu
     */
    @Test
    public void TestChangePinHappyPath() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.CheckPinAndLogIn(card, "1234");
        assertTrue(status);
        status = atm.ChangePinCard(card, "1234", "4567", "4567");
        assertTrue(status);
        status = atm.CheckPinAndLogIn(card, "4567");
        assertTrue(status);
    }

    /**
     * D + D - dane poprawne do zalogowania, niepoprawne powtorzenie kodu, kod nie powinien się zmienić
     */
    @Test
    public void TestChangePinWrongRepeat() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        String oldPin = "1234";
        card.Init(oldPin, oldPin);
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.CheckPinAndLogIn(card, oldPin);
        assertTrue(status);
        status = atm.ChangePinCard(card, oldPin, "4567", "1212");
        assertFalse(status);
        status = atm.CheckPinAndLogIn(card, oldPin);
        assertTrue(status);
    }

    /**
     * D + D - dane poprawne do zalogowania, poprawne powtorzenie kodu, kod dostępu powinien się zmienić, stary kod powinien nie zalogować
     */
    @Test
    public void TestChangePinAndUseOldPin() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        String oldPin = "1234";
        card.Init(oldPin, oldPin);
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.CheckPinAndLogIn(card, oldPin);
        assertTrue(status);
        String newPin = "2345";
        status = atm.ChangePinCard(card, oldPin, newPin, newPin);
        assertTrue(status);
        status = atm.CheckPinAndLogIn(card, oldPin);
        assertFalse(status);
    }

    /**
     * D + D + D + D - poprawne dane karty, konta, drugiego konta, przelewu
     */
    @Test
    public void TestTransferHappyPath() {
        Atm atm = new Atm();
        Account account = new Account();
        Account otherAccount = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.Transfer(card, otherAccount, 100);
        assertTrue(status);
        assertEquals(card.GetAccount().AccountStatus(), 400, 0.001);
        assertEquals(otherAccount.AccountStatus(), 100, 0.001);
    }

    /**
     * D + D + D + D - poprawne dane karty, konta, drugiego konta, ale przelew ponad stan konta
     */
    @Test
    public void TestTransferTooMuch() {
        Atm atm = new Atm();
        Account account = new Account();
        Account otherAccount = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.Transfer(card, otherAccount, 1000);
        assertFalse(status);
        assertEquals(card.GetAccount().AccountStatus(), 500, 0.001);
        assertEquals(otherAccount.AccountStatus(), 0, 0.001);
    }

    /**
     * D + D + D + D - poprawne dane karty, konta, przelewu, ale odbiorca nie istnieje
     */
    @Test
    public void TestTransferToNonexistingAccount() {
        Atm atm = new Atm();
        Account account = new Account();
        CreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.Transfer(card, null, 100);
        assertFalse(status);
        assertEquals(card.GetAccount().AccountStatus(), 500, 0.001);
    }

    /**
     * D + D + D + D - poprawne dane karty, drugiego konta, przelewu, ale karta nie jest zainicjalizowana
     */
    @Test
    public void TestTransferBadCard() {
        Atm atm = new Atm();
        Account account = new Account();
        Account otherAccount = new Account();
        CreditCard card = new CreditCard();
        card.AddAccount(account);
        account.DepositFunds(500);
        assertEquals(500, account.AccountStatus(), 0.001);

        boolean status = atm.Transfer(card, otherAccount, 100);
        assertFalse(status);
        assertEquals(account.AccountStatus(), 500, 0.001);
        assertEquals(otherAccount.AccountStatus(), 0, 0.001);
    }

}