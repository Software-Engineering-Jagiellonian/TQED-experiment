package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
    private Atm atm = new Atm();
    private double epsilon = 0.000001d;

//region CheckPingAndLogIn
    @Test
    public void checkPingAndLogInCorrect() {
        CreditCard creditCard = new CreditCard();
        Account account = new Account();
        String pinForCard = "1234";

        creditCard.AddAccount(account);
        creditCard.Init(pinForCard, pinForCard);

        assertTrue(atm.CheckPinAndLogIn(creditCard, pinForCard));
    }

    @Test
    public void checkPingAndLogInWhithOutCreditCard() {
        String pinForCard = "1234";

        assertFalse(atm.CheckPinAndLogIn(null, pinForCard));
    }

    @Test
    public void checkPingAndLogInWhithOutSettedAccount() {
        CreditCard creditCard = new CreditCard();
        String pinForCard = "1234";

        creditCard.Init(pinForCard, pinForCard);

        assertFalse(atm.CheckPinAndLogIn(creditCard, pinForCard));
    }
//endregion CheckPingAndLogIn

//region AccountStatus

    @Test
    public void accountStatusAccountIsNull() {
        double expected = 0.0;
        double actual = atm.AccountStatus(null);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void accountStatusJustCreated() {
        double expected = 0.0;
        Account account = new Account();
        double actual = atm.AccountStatus(account);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void accountStatusDepositWithdraw() {
        double expected = 0.0;
        Account account = new Account();
        account.DepositFunds(10);
        account.WithdrawFunds(10);
        double actual = atm.AccountStatus(account);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void accountStatusDeposit() {
        double expected = 10;
        Account account = new Account();
        account.DepositFunds(10);
        double actual = atm.AccountStatus(account);

        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void accountStatusWithdraw() {
        double expected = 0.0;
        Account account = new Account();
        account.WithdrawFunds(10);
        double actual = atm.AccountStatus(account);

        assertEquals(expected, actual, epsilon);
    }


//endregion AccountStatus

//region ChangePinCard

    @Test
    public void changePinCardCorrect() {
        String oldPin = "0000";
        String newPin = "1234";
        CreditCard creditCard = new CreditCard();

        assertTrue(atm.ChangePinCard(creditCard, oldPin, newPin, newPin));
    }

    @Test
    public void changePinCardCreditCardIsNull() {
        String oldPin = "0000";
        String newPin = "1234";
        CreditCard creditCard = null;

        assertFalse(atm.ChangePinCard(creditCard, oldPin, newPin, newPin));
    }

//endregion ChangePinCard

//region DepositFunds

    @Test
    public void depositFundsCorrect() {
        CreditCard creditCard = new CreditCard();
        Account account = new Account();
        creditCard.AddAccount(account);

        assertTrue(atm.DepositFunds(creditCard, 10));
    }

    @Test
    public void depositFundsWithoutConnectedAccount() {
        CreditCard creditCard = new CreditCard();

        assertFalse(atm.DepositFunds(creditCard, 10));
    }

    @Test
    public void depositFundsCreditCardIsNull() {
        CreditCard creditCard = null;

        assertFalse(atm.DepositFunds(creditCard, 10));
    }

//endregion DepositFunds

//region WithdrawFunds

    @Test
    public void withdrawFundsCorrect() {
        CreditCard creditCard = new CreditCard();
        Account account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(10);

        assertTrue(atm.WithdrawFunds(creditCard, 10));
    }

    @Test
    public void withdrawFundsWithoutAnoughAmount() {
        CreditCard creditCard = new CreditCard();
        Account account = new Account();
        creditCard.AddAccount(account);

        assertFalse(atm.WithdrawFunds(creditCard, 10));
    }

    @Test
    public void withdrawFundsWithoutConnectedAccount() {
        CreditCard creditCard = new CreditCard();

        assertFalse(atm.DepositFunds(creditCard, 10));
    }

    @Test
    public void withdrawFundsCreditCardIsNull() {
        CreditCard creditCard = null;

        assertFalse(atm.WithdrawFunds(creditCard, 10));
    }

//endregion WithdrawFunds

//region Transfer

    @Test
    public void TransferCorrect() {
        CreditCard creditCard = new CreditCard();
        Account accountRecipient = new Account();
        Account accountSender = new Account();
        double amount = 10;
        double expected1 = 0.0;
        double actual1;
        double expected2 = amount;
        double actual2;
        boolean trasferRsult = false;

        creditCard.AddAccount(accountSender);
        creditCard.DepositFunds(amount);
        trasferRsult = atm.Transfer(creditCard, accountRecipient,amount);
        actual1 = accountSender.AccountStatus();
        actual2 = accountRecipient.AccountStatus();

        assertTrue(trasferRsult);
        assertEquals(expected1, actual1, epsilon);
        assertEquals(expected2, actual2, epsilon);
    }

    @Test
    public void TransferCorrectFloatPoint() {
        CreditCard creditCard = new CreditCard();
        Account accountRecipient = new Account();
        Account accountSender = new Account();
        double amount = 0.5;
        double expected1 = 0.0;
        double actual1;
        double expected2 = amount;
        double actual2;
        boolean trasferRsult = false;

        creditCard.AddAccount(accountSender);
        creditCard.DepositFunds(amount);
        trasferRsult = atm.Transfer(creditCard, accountRecipient,amount);
        actual1 = accountSender.AccountStatus();
        actual2 = accountRecipient.AccountStatus();

        assertTrue(trasferRsult);
        assertEquals(expected1, actual1, epsilon);
        assertEquals(expected2, actual2, epsilon);
    }

    @Test
    public void TransferAccountRecipientIsNull() {
        CreditCard creditCard = new CreditCard();
        Account accountRecipient = null;
        Account accountSender = new Account();
        double amount = 10;
        double expected1 = amount;
        double actual1;
        boolean trasferRsult = false;

        creditCard.AddAccount(accountSender);
        creditCard.DepositFunds(amount);
        trasferRsult = atm.Transfer(creditCard, accountRecipient,amount);
        actual1 = accountSender.AccountStatus();

        assertFalse(trasferRsult);
        assertEquals(expected1, actual1, epsilon);
    }

    @Test
    public void TransferSameAccounts() {
        CreditCard creditCard = new CreditCard();
        Account accountRecipient = new Account();
        Account accountSender = accountRecipient;
        double amount = 10;
        double expected1 = amount;
        double actual1;
        boolean trasferRsult = false;

        creditCard.AddAccount(accountSender);
        creditCard.DepositFunds(amount);
        trasferRsult = atm.Transfer(creditCard, accountRecipient,amount);
        actual1 = accountSender.AccountStatus();

        assertFalse(trasferRsult);
        assertEquals(expected1, actual1, epsilon);
    }

    @Test
    public void TransferNotPossibletoWithdrawWithoutConnectedAccount() {
        CreditCard creditCard = new CreditCard();
        Account accountRecipient = new Account();
        double amount = 10;
        double expected2 = 0.0;
        double actual2;
        boolean trasferRsult;


        creditCard.DepositFunds(amount);
        trasferRsult = atm.Transfer(creditCard, accountRecipient,amount);

        actual2 = accountRecipient.AccountStatus();

        assertFalse(trasferRsult);
        assertEquals(expected2, actual2, epsilon);
    }
//endregion Transfer

}