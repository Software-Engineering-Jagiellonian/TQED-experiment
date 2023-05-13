package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {
    private IAccount setupAccount() {
        IAccount account = new Account();
        account.DepositFunds(100.0);

        return account;
    }

    private ICreditCard setupCreditCard() {
        ICreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(setupAccount());

        return card;
    }

    // D
    @Test
    public void defaultPin() {
        ICreditCard card = new CreditCard();

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCard() {
        ICreditCard card = new CreditCard();
        card.Init("1234", "1234");

        assertTrue(card.IsPinValid("1234"));
    }

    // D + Z + I
    @Test
    public void initializeCardTwice() {
        ICreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.Init("4321", "4321");

        assertTrue(card.IsPinValid("1234"));
    }

    // D + Z
    @Test
    public void initializeCardWrongConfirmation() {
        ICreditCard card = new CreditCard();
        card.Init("1234", "1235");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCardLonger() {
        ICreditCard card = new CreditCard();
        card.Init("12345", "12345");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCardShorter() {
        ICreditCard card = new CreditCard();
        card.Init("123", "123");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCardEmpty() {
        ICreditCard card = new CreditCard();
        card.Init("", "");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCardNegative() {
        ICreditCard card = new CreditCard();
        card.Init("-123", "-123");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCardLetter() {
        ICreditCard card = new CreditCard();
        card.Init("A123", "A123");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCardLetterDiacritic() {
        ICreditCard card = new CreditCard();
        card.Init("Ą123", "Ą123");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z
    @Test
    public void initializeCardAllLetters() {
        ICreditCard card = new CreditCard();
        card.Init("ABCD", "ABCD");

        assertTrue(card.IsPinValid("0000"));
    }

    // D + Z + Z
    @Test
    public void initAndChangePassword() {
        ICreditCard card = new CreditCard();

        card.Init("1234", "1234");
        assertTrue(card.ChangePin("1234", "4321", "4321"));

        assertTrue(card.IsPinValid("4321"));
    }

    // D + Z + Z
    @Test
    public void changePasswordAndInit() {
        ICreditCard card = new CreditCard();

        assertTrue(card.ChangePin("0000", "4321", "4321"));
        card.Init("1234", "1234");

        assertTrue(card.IsPinValid("4321"));
    }

    // D + Z + Z
    @Test
    public void changePasswordWithoutInit() {
        ICreditCard card = new CreditCard();

        assertTrue(card.ChangePin("0000", "1234", "1234"));
        assertTrue(card.IsPinValid("1234"));
    }

    // D + Z
    @Test
    public void changePasswordWrongOldPassword() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1111", "4321", "4321"));

        assertTrue(card.IsPinValid("1234"));
    }

    // D + Z
    @Test
    public void changePasswordSame() {
        ICreditCard card = setupCreditCard();

        assertTrue(card.ChangePin("1234", "1234", "1234"));

        assertTrue(card.IsPinValid("1234"));
    }

    // D + Z + I
    @Test
    public void changePasswordTwice() {
        ICreditCard card = setupCreditCard();

        assertTrue(card.ChangePin("1234", "1111", "1111"));
        assertTrue(card.ChangePin("1111", "4321", "4321"));

        assertTrue(card.IsPinValid("4321"));
    }

    // D + Z + I
    @Test
    public void changePasswordTwiceSameOldPin() {
        ICreditCard card = setupCreditCard();

        assertTrue(card.ChangePin("1234", "1111", "1111"));
        assertFalse(card.ChangePin("1234", "4321", "4321"));

        assertTrue(card.IsPinValid("1111"));
    }

    // D + Z
    @Test
    public void changePasswordWrongConfirmation() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "1111", "4321"));
    }

    // D + Z
    @Test
    public void changePasswordLonger() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "12345", "12345"));
    }

    // D + Z
    @Test
    public void changePasswordShorter() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "123", "123"));
    }

    // D + Z
    @Test
    public void changePasswordEmpty() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "", ""));
    }

    // D + Z
    @Test
    public void changePasswordNegative() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "-123", "-123"));
    }

    // D + Z
    @Test
    public void changePasswordLetter() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "A123", "A123"));
    }

    // D + Z
    @Test
    public void changePasswordLetterDiacritic() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "Ą123", "Ą123"));
    }

    // D + Z
    @Test
    public void changePasswordAllLetters() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.ChangePin("1234", "ABCD", "ABCD"));
    }

    // D + Z + I
    @Test
    public void changePasswordMultipleTimes() {
        ICreditCard card = new CreditCard();
        card.Init("1000", "1000");

        for (int i = 1000; i < 1234; ++i) {
            assertTrue(card.ChangePin(Integer.toString(i), Integer.toString(i + 1), Integer.toString(i + 1)));
        }
        assertTrue(card.IsPinValid("1234"));
    }

    // D
    @Test
    public void defaultLinkedAccount() {
        ICreditCard card = new CreditCard();

        assertNull(card.GetAccount());
    }

    // D
    @Test
    public void linkAccount() {
        ICreditCard card = new CreditCard();
        IAccount account = setupAccount();

        card.AddAccount(account);
        assertSame(card.GetAccount(), account);
    }

    // D + D
    @Test
    public void doesNotLinkAnotherAccount() {
        ICreditCard card = new CreditCard();
        IAccount account = setupAccount();
        IAccount anotherAccount = setupAccount();

        card.AddAccount(account);
        card.AddAccount(anotherAccount);
        assertSame(card.GetAccount(), account);
    }

    // D + I
    @Test
    public void linkSameAccountTwice() {
        ICreditCard card = new CreditCard();
        IAccount account = setupAccount();

        card.AddAccount(account);
        card.AddAccount(account);
        assertSame(card.GetAccount(), account);
    }

    // D + D
    @Test
    public void doesNotUnlinkAccount() {
        ICreditCard card = new CreditCard();
        IAccount account = setupAccount();

        card.AddAccount(account);
        card.AddAccount(null);
        assertSame(card.GetAccount(), account);
    }

    // D + Z
    @Test
    public void depositsToLinkedAccount() {
        ICreditCard card = new CreditCard();
        IAccount account = setupAccount();

        card.AddAccount(account);
        assertTrue(card.DepositFunds(100.0));
        assertEquals(200.0, account.AccountStatus(), 0.0);
    }

    // D + Z
    @Test
    public void withdrawsFromLinkedAccount() {
        ICreditCard card = new CreditCard();
        IAccount account = setupAccount();

        card.AddAccount(account);
        assertTrue(card.WithdrawFunds(100.0));
        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    // Z
    @Test
    public void doesNotDepositWithoutAccount() {
        ICreditCard card = new CreditCard();

        assertFalse(card.DepositFunds(100.0));
    }

    // Z + I
    @Test
    public void doesNotDepositZero() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.DepositFunds(0.0));
    }

    // Z + I
    @Test
    public void doesNotDepositNegative() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.DepositFunds(-100.0));
    }

    // Z
    @Test
    public void doesNotWithdrawWithoutAccount() {
        ICreditCard card = new CreditCard();

        assertFalse(card.WithdrawFunds(100.0));
    }

    // Z + I
    @Test
    public void doesNotWithdrawZero() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.WithdrawFunds(0.0));
    }

    // Z + I
    @Test
    public void doesNotWithdrawNegative() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.WithdrawFunds(-100.0));
    }

    // D + Z
    @Test
    public void doesNotWithdrawMoreThanBalance() {
        ICreditCard card = setupCreditCard();

        assertFalse(card.WithdrawFunds(200.0));
    }

    // D + Z + I
    @Test
    public void multipleCardsOneAccount() {
        ICreditCard card1 = new CreditCard();
        ICreditCard card2 = new CreditCard();
        IAccount account = setupAccount();

        card1.AddAccount(account);
        card2.AddAccount(account);
        assertSame(card1.GetAccount(), card2.GetAccount());

        assertTrue(card1.DepositFunds(300.0));
        assertTrue(card2.WithdrawFunds(100.0));
        assertFalse(card1.WithdrawFunds(400.0));

        assertEquals(300.0, account.AccountStatus(), 0.0);
    }
}
