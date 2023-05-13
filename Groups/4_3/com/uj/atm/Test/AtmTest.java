package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
    @Test
    public void CheckPinAndLoginTest() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";
        creditCard.Init(newPin, newPinConfirm);

        assertTrue(atm.CheckPinAndLogIn(creditCard, newPin));

        assertFalse(atm.CheckPinAndLogIn(creditCard, "0111"));

    }

    @Test
    public void AccountStatusTest() {
        Atm atm = new Atm();
        Account account = new Account();

        assertEquals(0.0, atm.AccountStatus(account), 0.0);
    }

    @Test
    public void ChangePinCardTest() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";

        creditCard.Init(newPin, newPinConfirm);

        String oldPin = newPin;

        newPin = "0202";
        newPinConfirm = "0202";

        assertTrue(atm.ChangePinCard(creditCard,oldPin, newPin, newPinConfirm));

        oldPin = newPin;

        newPin = "02022";
        newPinConfirm = "02202";
        System.out.println(atm.ChangePinCard(creditCard, oldPin, newPin, newPinConfirm));
        assertFalse(atm.ChangePinCard(creditCard, oldPin, newPin, newPinConfirm));
    }

    @Test
    public void DepositFundsTest() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";

        creditCard.Init(newPin, newPinConfirm);

        double amount = 100.0;

        assertFalse(atm.DepositFunds(creditCard, amount));

        Account account = new Account();
        creditCard.AddAccount(account);

        assertTrue(atm.DepositFunds(creditCard, amount));
    }

    @Test
    public void WithdrawFunds() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";

        creditCard.Init(newPin, newPinConfirm);

        double amount = 100.0;
        assertFalse(atm.WithdrawFunds(creditCard, amount));

        Account account = new Account();
        creditCard.AddAccount(account);

        assertFalse(atm.WithdrawFunds(creditCard, amount));

        atm.DepositFunds(creditCard, amount);

        assertTrue(atm.WithdrawFunds(creditCard, amount));



    }

    @Test
    public void TransferTest() {
        Atm atm = new Atm();
        CreditCard creditCard = new CreditCard();
        String newPin = "0101";
        String newPinConfirm = "0101";
        creditCard.Init(newPin, newPinConfirm);
        Account account = new Account();
        Account accountRecipient = new Account();
        double amount = 100.0;

        assertFalse(atm.Transfer(creditCard, accountRecipient, amount));


        creditCard.AddAccount(account);
        atm.DepositFunds(creditCard, amount);

        assertFalse(atm.Transfer(creditCard, null, amount));


        assertTrue(atm.Transfer(creditCard, accountRecipient, amount));

    }
}