package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {
    private CreditCard card = new CreditCard();

    @Test
    public void testDefaultAccount() {
        IAccount account = card.GetAccount();
        assertNull(account);
    }
    @Test
    public void testInitValid() {
        String newPin = "1234";
        String newPinConfirm = "1234";

        boolean thrown = false;

        try {
            card.Init(newPin, newPinConfirm);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        assertFalse(thrown);

    }

    @Test
    public void testInitInvalid() {
        String[][] pinPairs = {
                {"1234", "1235"},
                {"123", "123"},
                {"12345", "12345"},
                {"1234a", "1234a"},
                {"1234", "123"},
                {"abcd", "abcd"},
                {"12 34", "12 34"},
                {"", ""},
                {"----", "----"},
        };

        for( String[] pair : pinPairs){
            String newPin = pair[0];
            String newPinConfirm = pair[1];

            boolean thrown = false;

            try {
                card.Init(newPin, newPinConfirm);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }

            assertTrue(thrown);
        }
    }

    @Test
    public void testChangePinValid() {
        String defaultPin = "0000";
        String newPin = "1234";
        String newPinConfirm = "1234";

        boolean firstChange = card.ChangePin(defaultPin, newPin, newPinConfirm);
        assertTrue(firstChange);

        String nextPin = "1111";
        String nextPinConfirm = "1111";

        boolean secondChange = card.ChangePin(newPin, nextPin, nextPinConfirm);
        assertTrue(secondChange);
    }

    @Test
    public void testChangePinInvalidOld() {
        String oldPin = "0001";
        String newPin = "1234";
        String newPinConfirm = "1234";

        boolean result = card.ChangePin(oldPin, newPin, newPinConfirm);
        assertFalse(result);
    }

    @Test
    public void testChangePinInvalidConf() {
        String oldPin = "0000";
        String newPin = "1234";
        String newPinConfirm = "1235";

        boolean result = card.ChangePin(oldPin, newPin, newPinConfirm);
        assertFalse(result);
    }

    @Test
    public void testValidPins() {
        String[] valid_pins = {"0000", "0123", "9999", "5437"};
        String oldPin = "0000";
        for (String pin : valid_pins){
            card.ChangePin(oldPin, pin, pin);
            oldPin = pin;
            assertTrue(card.IsPinValid(pin));
        }
    }

    @Test
    public void testInvalidPins() {
        String[] invalid_pins = {"00000", "012", "9999999", "abcd", "190a", "", "a", ";", "abcdef", "0000a", "a0000", "ABCD", "12345", "00 00", "1234", "9999", "----"};
        for (String pin : invalid_pins){
            assertFalse(card.IsPinValid(pin));
        }
    }
    @Test
    public void testAddAccount() {
        assertEquals(card.GetAccount(), null);

        IAccount account1 = new Account();
        card.AddAccount(account1);
        assertEquals(card.GetAccount(), account1);

        IAccount account2 = new Account();
        card.AddAccount(account2);
        assertEquals(card.GetAccount(), account1);
    }

    @Test
    public void testDepositFundsNoCard() {
        double amount = 100;
        assertFalse(card.DepositFunds(amount));
    }

    @Test
    public void testDepositNegativeFunds() {
        double amount = -100;
        IAccount account = new Account();
        card.AddAccount(account);
        assertFalse(card.DepositFunds(amount));
    }

    @Test
    public void testDepositFundsCorrect() {
        double amount = 100;
        IAccount account = new Account();
        card.AddAccount(account);
        assertTrue(card.DepositFunds(amount));
    }


    @Test
    public void testWithdrawFundsNoCard() {
        double amount = 100;
        assertFalse(card.WithdrawFunds(amount));
    }

    @Test
    public void testWithdrawNegativeFunds() {
        IAccount account = new Account();
        card.AddAccount(account);
        card.DepositFunds(1000);

        double amount = -100;
        assertFalse(card.WithdrawFunds(amount));
    }

    @Test
    public void testWithdrawNotSufficientFunds() {
        IAccount account = new Account();
        card.AddAccount(account);
        card.DepositFunds(100);

        double amount = 150;
        assertFalse(card.WithdrawFunds(amount));
    }

    @Test
    public void testWithdrawFundsCorrect() {
        IAccount account = new Account();
        card.AddAccount(account);
        card.DepositFunds(250);

        assertTrue(card.WithdrawFunds(150));
        assertTrue(card.WithdrawFunds(100));
        assertFalse(card.WithdrawFunds(1e-6));
    }

    @Test
    public void testWithdrawMultipleTransactions() {
        IAccount account = new Account();
        card.AddAccount(account);
        card.DepositFunds(250);

        assertTrue(card.WithdrawFunds(150));
        assertTrue(card.WithdrawFunds(100));
        assertFalse(card.WithdrawFunds(1e-6));

        card.DepositFunds(100);
        assertTrue(card.WithdrawFunds(80));
        assertFalse(card.WithdrawFunds(30));

        card.DepositFunds(10);
        assertTrue(card.WithdrawFunds(30));
        assertFalse(card.WithdrawFunds(1e-6));
    }

}