package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CreditCardTest {

    private final ICreditCard sut = new CreditCard();

    // D + Z
    @Test
	public void Init_firstCallPinMatches_noException() {
        sut.Init("abc", "abc");
    }

    // D + Z
    @Test
    public void Init_firstCallPinDoesntMatch_exception() {
        assertThrows(
                IllegalArgumentException.class,
                () -> sut.Init("abc", "def"));
    }

    // D + Z
    @Test
    public void Init_callAfterSuccessfulInit_exception() {
        sut.Init("abc", "abc");
        
        assertThrows(
                IllegalStateException.class,
                () -> sut.Init("abc", "abc"));
    }

    // D + Z + I
    @Test
    public void Init_callAfterFailedInit_success() {
        try {
            sut.Init("abc", "def");
        } catch (IllegalArgumentException ignored) {}
        
        sut.Init("abc", "abc");
    }

    // D + Z
    @Test
    public void ChangePin_oldPinDoesntMatch_returnsFalse() {
        sut.Init("abc", "abc");
        
        boolean actual = sut.ChangePin("def", "xyz", "xyz");
        
        assertFalse(actual);
    }

    // D + Z
    @Test
    public void ChangePin_newPinsDontMatch_returnsFalse() {
        sut.Init("abc", "abc");
        
        boolean actual = sut.ChangePin("abc", "def", "xyz");
        
        assertFalse(actual);
    }

    // D + Z
    @Test
    public void ChangePin_allPinsValid_oldPinInvalid() {
        sut.Init("abc", "abc");
        
        sut.ChangePin("abc", "xyz", "xyz");
        
        assertFalse(sut.IsPinValid("abc"));
    }

    // D + Z
    @Test
    public void ChangePin_allPinsValid_newPinValid() {
        sut.Init("abc", "abc");

        sut.ChangePin("abc", "xyz", "xyz");
        
        assertTrue(sut.IsPinValid("xyz"));
    }

    // D + Z
    @Test
    public void ChangePin_notInitialized_exception() {
        assertThrows(
                IllegalStateException.class,
                () -> sut.ChangePin("abc", "xyz", "xyz"));
    }

    // D + Z
    @Test
    public void IsPinValid_validPin_returnsTrue() {
        sut.Init("abc", "abc");

        boolean actual = sut.IsPinValid("abc");
        
        assertTrue(actual);
    }

    // D + Z
    @Test
    public void IsPinValid_invalidPin_returnsFalse() {
        sut.Init("abc", "abc");
        
        boolean actual = sut.IsPinValid("xyz");
        
        assertFalse(actual);
    }

    // D + Z
    @Test
    public void IsPinValid_notInitialized_exception() {
        assertThrows(
                IllegalStateException.class,
                () -> sut.IsPinValid("abc"));
    }

    // D + Z
    @Test
    public void AddAccount_noAccountAdded_addsAccount() {
        IAccount account = new Account();
        sut.Init("abc", "abc");

        sut.AddAccount(account);
        
        // no equals overridden since we are unable to set any id via constructor
        IAccount actual = sut.GetAccount();
        assertSame(actual, account);
    }

    // D + Z
    @Test
    public void AddAccount_accountAlreadyAdded_exception() {
        IAccount account = new Account();
        sut.Init("abc", "abc");
        sut.AddAccount(account);
        
        assertThrows(
                IllegalStateException.class,
                () -> sut.AddAccount(account));
    }

    // D + Z
    @Test
    public void AddAccount_notInitialized_exception() {
        assertThrows(
                IllegalStateException.class,
                () -> sut.AddAccount(new Account()));
    }

    // D + Z
    @Test
    public void GetAccount_noAccountAddedYet_exception() {
        sut.Init("abc", "abc");
        
        assertThrows(
                IllegalStateException.class,
                sut::GetAccount);
    }

    // D + Z
    @Test
    public void GetAccount_accountAdded_returnsAccount() {
        sut.Init("abc", "abc");
        IAccount account = new Account();
        sut.AddAccount(account);
        
        IAccount actual = sut.GetAccount();
        
        assertSame(actual, account);
    }

    // D + Z
    @Test
    public void GetAccount_notInitialized_exception() {
        assertThrows(
                IllegalStateException.class,
                sut::GetAccount);
    }

    // D + Z
    @Test
    public void DepositFunds_noAccountAddedYet_returnsFalse() {
        sut.Init("abc", "abc");
        
        boolean actual = sut.DepositFunds(123D);
        
        assertFalse(actual);
    }

    // D + Z
    @Test
    public void DepositFunds_accountAdded_returnsTrueOnSuccess() {
        sut.Init("abc", "abc");
        IAccount account = new Account();
        sut.AddAccount(account);
        
        boolean actual = sut.DepositFunds(123D);
        
        assertTrue(actual);
    }

    // D + Z
    @Test
    public void DepositFunds_accountAdded_depositsAmount() {
        sut.Init("abc", "abc");
        IAccount account = new Account();
        sut.AddAccount(account);
        
        sut.DepositFunds(123D);
        
        double actual = account.AccountStatus();
        assertDoubleEquals(actual, 123D);
    }

    // D + Z
    @Test
    public void DepositFunds_notInitialized_exception() {
        assertThrows(
                IllegalStateException.class,
                () -> sut.DepositFunds(123D));
    }

    // D + Z
    @Test
    public void WithdrawFunds_noAccountAddedYet_returnsFalse() {
        sut.Init("abc", "abc");

        boolean actual = sut.WithdrawFunds(123D);

        assertFalse(actual);
    }

    // D + Z
    @Test
    public void WithdrawFunds_accountAdded_returnsTrueOnSuccess() {
        sut.Init("abc", "abc");
        IAccount account = new Account();
        sut.AddAccount(account);

        boolean actual = sut.WithdrawFunds(123D);

        assertTrue(actual);
    }

    // D + Z
    @Test
    public void WithdrawFunds_accountAdded_WithdrawsAmount() {
        sut.Init("abc", "abc");
        IAccount account = new Account();
        sut.AddAccount(account);

        sut.WithdrawFunds(123D);

        double actual = account.AccountStatus();
        assertDoubleEquals(actual, -123D);
    }

    // D + Z
    @Test
    public void WithdrawFunds_notInitialized_exception() {
        assertThrows(
                IllegalStateException.class,
                () -> sut.WithdrawFunds(123D));
    }

    private static void assertDoubleEquals(double actual, double expected) {
        assertEquals(actual, expected, 0.001);
    }
}