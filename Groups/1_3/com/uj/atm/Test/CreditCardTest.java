package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    @Test
    public void InitDifferentPinsTest() {
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "01010";
        creditCard.Init(newPin, newPinConfirm);
    }
    @Test
    public void InitWrongPinTest() {
        CreditCard creditCard = new CreditCard();
        String newPin = "0101abcd";
        String newPinConfirm = "0101abcd";
        creditCard.Init(newPin, newPinConfirm);
    }
    @Test
    public void InitTest() {
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";
        creditCard.Init(newPin, newPinConfirm);
    }

    @Test
    public void ChangePinTest() {
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";

        creditCard.Init(newPin, newPinConfirm);

        String oldPin = newPin;

        newPin = "0151";
        newPinConfirm = "0151";
        assertTrue(creditCard.ChangePin(oldPin, newPin, newPinConfirm));

        /////////////////////////////////

        oldPin = newPin;

        newPin = "0abc";
        newPinConfirm = "0abc";
        assertFalse(creditCard.ChangePin(oldPin, newPin, newPinConfirm));

        /////////////////////////////////


        newPin = "01512";
        newPinConfirm = "01512";
        assertFalse(creditCard.ChangePin(oldPin, newPin, newPinConfirm));

        ////////////////////////////////.

        oldPin = newPin;

        newPin = "0101";
        newPinConfirm = "0101";
        assertFalse(creditCard.ChangePin(oldPin, newPin, newPinConfirm));

        newPin = "0123";
        newPinConfirm = "0123";
        assertFalse(creditCard.ChangePin(oldPin, newPin, newPinConfirm));
    }

    @Test
    public void isPinValidTest() {
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";
        creditCard.Init(newPin, newPinConfirm);

        assertTrue(creditCard.IsPinValid(newPin));
    }

    @Test
    public void AddAccountTest() {
        Account account = new Account();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";
        creditCard.Init(newPin, newPinConfirm);


        creditCard.AddAccount(account);

        Account account2 = new Account();

        creditCard.AddAccount(account2);

        assertSame(creditCard.GetAccount(), account);
    }

    @Test
    public void DepositFundsTest() {
        Account account = new Account();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";
        creditCard.Init(newPin, newPinConfirm);


        double amount = 100.0;
        assertFalse(creditCard.DepositFunds(amount));

        creditCard.AddAccount(account);

        assertTrue(creditCard.DepositFunds(amount));
    }

    @Test
    public void WithdrawFundsTest() {
        Account account = new Account();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";
        creditCard.Init(newPin, newPinConfirm);

        double amount = 100.0;

        creditCard.AddAccount(account);

        assertFalse(creditCard.WithdrawFunds(amount));

        creditCard.DepositFunds(amount);

        assertTrue(creditCard.WithdrawFunds(amount));
    }
}