package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    private CreditCard creditCard;

    @Before
    public void setUp() {
        this.creditCard = new CreditCard();
    }

	@Test
    public void testAddNullAccount() {
        creditCard.AddAccount(null);
        assertNull(creditCard.GetAccount());
    }

    @Test
    public void testGetAccount() {
        IAccount account = new Account();
        creditCard.AddAccount(account);
        assertEquals(creditCard.GetAccount(), account);
    }

    @Test
    public void testAddValidAccount() {
        IAccount account = new Account();
        creditCard.AddAccount(account);
        assertEquals(account, creditCard.GetAccount());
    }

    @Test
    public void testValidDefaultPin() {
        assertTrue(creditCard.IsPinValid("1234"));
    }

    @Test
    public void testInvalidDefaultPin() {
        assertFalse(creditCard.IsPinValid("1111"));
    }

    @Test
    public void testChangePinBeforeInit() {
        creditCard.ChangePin("1234", "1808", "1808");
        assertFalse(creditCard.IsPinValid("1808"));
    }

    @Test
    public void testChangePinAfterInit() {
        creditCard.Init("1234", "1234");
        creditCard.ChangePin("1234", "1808", "1808");
        assertTrue(creditCard.IsPinValid("1808"));
    }

    @Test
    public void testInitPin() {
        creditCard.Init("1808", "1808");
        assertTrue(creditCard.IsPinValid("1808"));
    }

    @Test
    public void testInitWithInvalidPin() {
        creditCard.Init("11111", "11111");
        assertFalse(creditCard.IsPinValid("11111"));
    }

    @Test
    public void testInitWithNotMatchedPins() {
        creditCard.Init("1234", "5678");
        assertTrue(creditCard.IsPinValid("1234"));
    }

    @Test
    public void TestInitPinTwiceWithDifferentPins() {
        creditCard.Init("1808", "1808");
        creditCard.Init("0000", "0000");
        assertTrue(creditCard.IsPinValid("1808"));
    }

    @Test(expected = NumberFormatException.class)
    public void testInitPinWithLetters() {
        creditCard.Init("abcd", "abcd");
    }

    @Test
    public void testDepositFundsWithoutAccount() {
        assertFalse(creditCard.DepositFunds(1.0));
    }

    @Test
    public void testDepositFunds() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(new Account());
        assertTrue(creditCard.DepositFunds(1.0));
        assertEquals(1.0, creditCard.GetAccount().AccountStatus(), 0.0);
    }

    @Test
    public void testWithdrawFundsWithoutAccount() {
        assertFalse(creditCard.WithdrawFunds(1.0));
    }

    @Test
    public void testWithdrawFundsWithEmptyAccount() {
        creditCard.AddAccount(new Account());
        assertFalse(creditCard.WithdrawFunds(1.0));
    }

    @Test
    public void testWithdrawFunds() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(new Account());
        creditCard.DepositFunds(2.0);
        creditCard.WithdrawFunds(1.0);
        assertEquals(1.0, creditCard.GetAccount().AccountStatus(), 0.0);
    }
}