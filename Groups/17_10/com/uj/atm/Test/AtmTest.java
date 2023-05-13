package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtmTest {
	
    private final IAtm sut = new Atm();

    // D + Z
    @Test
    public void CheckPinAndLogin_invalidPin_returnsFalse() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("abcd", "abcd");
        
        boolean actual = sut.CheckPinAndLogIn(creditCard, "xyzz");
        
        assertFalse(actual);
    }

    // D + Z
    @Test
    public void CheckPinAndLogin_validPin_returnsTrue() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");

        boolean actual = sut.CheckPinAndLogIn(creditCard, "1234");

        assertTrue(actual);
    }

    // D + Z
    @Test
    public void CheckPinAndLogin_pinTooShort_returnsFalse() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("123", "123");
        
        boolean actual = sut.CheckPinAndLogIn(creditCard, "123");
        
        assertFalse(actual);
    }

    // D + Z
    @Test
    public void CheckPinAndLogin_pinTooLong_returnsFalse() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("12345", "12345");

        boolean actual = sut.CheckPinAndLogIn(creditCard, "12345");

        assertFalse(actual);
    }

    // D + Z
    @Test
    public void CheckPinAndLogin_pinWithOneNonDigit_returnsFalse() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("123a", "123a");

        boolean actual = sut.CheckPinAndLogIn(creditCard, "123a");

        assertFalse(actual);
    }

    // D + Z
    @Test
    public void CheckPinAndLogin_pinWithMultipleNonDigits_returnsFalse() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("abc4", "abc4");

        boolean actual = sut.CheckPinAndLogIn(creditCard, "abc4");

        assertFalse(actual);
    }

    // D + Z
    @Test
    public void AccountStatus_returnsAccountStatus() {
        IAccount account = new Account();
        account.DepositFunds(123D);
        
        double actual = sut.AccountStatus(account);
        
        assertDoubleEquals(actual, 123D);
    }

    // D + Z
    @Test
    public void ChangePinCard_changesPin() {
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        
        sut.ChangePinCard(card, "abc", "xyz", "xyz");
        
        boolean actual = card.IsPinValid("xyz");
        assertTrue(actual);
    }

    // D + Z
    @Test
    public void DepositFunds_returnsTrueOnSuccess() {
        IAccount account = new Account();
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        card.AddAccount(account);
        
        boolean actual = sut.DepositFunds(card, 123D);

        assertTrue(actual);
    }

    // D + Z
    @Test
    public void DepositFunds_deposits() {
        IAccount account = new Account();
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        card.AddAccount(account);

        sut.DepositFunds(card, 123D);

        double actual = account.AccountStatus();
        assertDoubleEquals(actual, 123D);
    }

    // D + Z
    @Test
    public void WithdrawFunds_returnsTrueOnSuccess() {
        IAccount account = new Account();
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        card.AddAccount(account);

        boolean actual = sut.WithdrawFunds(card, 123D);

        assertTrue(actual);
    }

    // D + Z
    @Test
    public void WithdrawFunds_withdraws() {
        IAccount account = new Account();
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        card.AddAccount(account);

        sut.WithdrawFunds(card, 123D);

        double actual = account.AccountStatus();
        assertDoubleEquals(actual, -123D);
    }

    // D + Z
    @Test
    public void Transfer_returnsTrueOnSuccess() {
        IAccount recipientAccount = new Account();
        IAccount sourceAccount = new Account();
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        card.AddAccount(sourceAccount);
        
        boolean actual = sut.Transfer(card, recipientAccount, 123D);
        
        assertTrue(actual);
    }

    // D + Z
    @Test
    public void Transfer_withdrawsFromSource() {
        IAccount recipientAccount = new Account();
        IAccount sourceAccount = new Account();
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        card.AddAccount(sourceAccount);

        sut.Transfer(card, recipientAccount, 123D);
        
        double actual = sourceAccount.AccountStatus();
        assertDoubleEquals(actual, -123D);
    }

    // D + Z
    @Test
    public void Transfer_depositsToRecipient() {
        IAccount recipientAccount = new Account();
        IAccount sourceAccount = new Account();
        ICreditCard card = new CreditCard();
        card.Init("abc", "abc");
        card.AddAccount(sourceAccount);

        sut.Transfer(card, recipientAccount, 123D);

        double actual = recipientAccount.AccountStatus();
        assertDoubleEquals(actual, 123D);
    }

    private static void assertDoubleEquals(double actual, double expected) {
        assertEquals(actual, expected, 0.001);
    }
}