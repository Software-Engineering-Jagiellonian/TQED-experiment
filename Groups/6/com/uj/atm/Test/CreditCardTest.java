package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static java.util.Objects.isNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CreditCardTest {

    private ICreditCard card;

    @Before
    public void beforeEach() {
        card = new CreditCard();
    }

    @Test
    public void shouldReturnTrueWhenInitializeCorrectPin() {
        try {
            card.Init("1234", "1234");
        } catch (IllegalArgumentException e) {
            System.out.println("LOG - Invalid pin");
            e.printStackTrace();
        }
    }

    @Test
    @SuppressWarnings({"java:S5960"})
    public void shouldThrowExceptionWhenInitializeIncorrectPin() {
        String testCase1 = "1234";
        String testCase2 = "123";
        String testCase3 = "12345";
        String abcd = "abcd";
        IllegalArgumentException ilAE = assertThrows(IllegalArgumentException.class, () -> card.Init(testCase1, testCase2 + "5"));
        assertFalse(isNull(ilAE));
        IllegalArgumentException ilAE2 = assertThrows(IllegalArgumentException.class, () -> card.Init(testCase1, testCase3));
        assertFalse(isNull(ilAE2));
        IllegalArgumentException ilAE3 = assertThrows(IllegalArgumentException.class, () -> card.Init(testCase3, testCase1));
        assertFalse(isNull(ilAE3));
        IllegalArgumentException ilA4 = assertThrows(IllegalArgumentException.class, () -> card.Init(testCase2, testCase2));
        assertFalse(isNull(ilA4));
        IllegalArgumentException ilA5 = assertThrows(IllegalArgumentException.class, () -> card.Init(testCase2 + "a", testCase2 + "a"));
        assertFalse(isNull(ilA5));
        IllegalArgumentException ilA6 = assertThrows(IllegalArgumentException.class, () -> card.Init(abcd, abcd));
        assertFalse(isNull(ilA6));
    }

    @Test
    public void shouldReturnTrueWhenCorrectChangePin() {
        card.Init("1234", "1234");
        boolean result = card.ChangePin("1234", "2345", "2345");
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenIncorrectChangePin() {
        card.Init("1234", "1234");
        boolean result = card.ChangePin("1235", "2345", "2345");
        assertFalse(result);
        result = card.ChangePin("123", "2345", "2345");
        assertFalse(result);
        result = card.ChangePin("12345", "2345", "2345");
        assertFalse(result);
        result = card.ChangePin("1234", "234", "234");
        assertFalse(result);
        result = card.ChangePin("1234", "23456", "23456");
        assertFalse(result);
        result = card.ChangePin("1234", "2345", "2344");
        assertFalse(result);
        result = card.ChangePin("123a", "2345", "2345");
        assertFalse(result);
        result = card.ChangePin("1234", "2a45", "2a45");
        assertFalse(result);
        result = card.ChangePin("1234", "abcd", "abcd");
        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenPinValid() {
        card.Init("1234", "1234");
        boolean result = card.IsPinValid("1234");
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenPinInvalid() {
        card.Init("1234", "1234");
        boolean result = card.IsPinValid("1233");
        assertFalse(result);
        result = card.IsPinValid("123");
        assertFalse(result);
        result = card.IsPinValid("12345");
        assertFalse(result);
        result = card.IsPinValid("123a");
        assertFalse(result);
        result = card.IsPinValid("abcd");
        assertFalse(result);
    }

    @Test
    public void shouldAddAccount() {
        IAccount account1 = new Account();
        account1.DepositFunds(100);
        IAccount account2 = new Account();
        account2.DepositFunds(150);
        card.AddAccount(account1);
        IAccount res = card.GetAccount();
        assertTrue(res == account1);
        card.AddAccount(account2);
        res = card.GetAccount();
        assertTrue(res == account1);
    }

    @Test
    public void shouldReturnNullWhenAddingNull() {
        card.AddAccount(null);
        IAccount res = card.GetAccount();
        assertTrue(isNull(res));
    }

    @Test
    public void shouldDepositFunds() {
        IAccount account = new Account();
        card.AddAccount(account);
        boolean res = card.DepositFunds(150.00);
        assertTrue(res);
        boolean res2 = card.DepositFunds(200);
        assertTrue(res2);
        double sum = card.GetAccount().AccountStatus();
        assertThat(sum, is(350d));
    }

    @Test
    public void shouldWithdrawFunds() {
        IAccount account = new Account();
        card.AddAccount(account);
        card.DepositFunds(150.00);
        boolean res = card.WithdrawFunds(100);
        assertTrue(res);
        double sum = card.GetAccount().AccountStatus();
        assertThat(sum, is(50d));
        res = card.WithdrawFunds(100);
        assertTrue(res);
        sum = card.GetAccount().AccountStatus();
        assertThat(sum, is(0d));
    }

}