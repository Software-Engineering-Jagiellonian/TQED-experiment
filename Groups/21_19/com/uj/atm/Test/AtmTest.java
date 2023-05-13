package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    // CheckPinAndLogIn

    @Test //D+Z
    public void shouldLogInSuccessfully_givenDefaultPin() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        String pin = "0000";

        // when
        boolean res = atm.CheckPinAndLogIn(card, pin);

        // then
        assertTrue(res);
    }

    @Test //D+Z
    public void shouldFailLogIn_givenPinContainingIncorrectCharacters() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        String pin = "0t00";

        // when
        boolean res = atm.CheckPinAndLogIn(card, pin);

        // then
        assertFalse(res);
    }

    @Test //Z+I
    public void shouldFailLogIn_givenPinTooLong() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        String pin = "0212100";

        // when
        boolean res = atm.CheckPinAndLogIn(card, pin);

        // then
        assertFalse(res);
    }

    // ChangePinCard

    @Test //Z+Z
    public void shouldLogIn_givenChangedPin() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        String oldPin = "0000";
        String newPin = "1212";

        // when
        boolean isPinChanged = atm.ChangePinCard(card, oldPin, newPin, newPin);
        boolean res = atm.CheckPinAndLogIn(card, newPin);

        // then
        assertTrue(isPinChanged);
        assertTrue(res);
    }

    @Test //Z+I
    public void shouldFailToChangePin_givenNewPinIncorrect() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        String oldPin = "0000";
        String newPin = "12312";

        // when
        boolean isPinChanged = atm.ChangePinCard(card, oldPin, newPin, newPin);
        boolean res = atm.CheckPinAndLogIn(card, oldPin);

        // then
        assertFalse(isPinChanged);
        assertTrue(res);
    }

    @Test //D+Z
    public void shouldFailToChangePin_givenNewPinNotEqualsNewPinConfirmed() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        String oldPin = "0000";
        String newPin = "1212";
        String newPinConfirmed = "21212";

        // when
        boolean isPinChanged = atm.ChangePinCard(card, oldPin, newPin, newPinConfirmed);
        boolean res = atm.CheckPinAndLogIn(card, oldPin);

        // then
        assertFalse(isPinChanged);
        assertTrue(res);
    }

    // AccountStatus

    @Test // D+I
    public void shouldReturnZeroBalance_givenAccount() {
        // given
        IAccount account = new Account();
        IAtm atm = new Atm();

        // when
        double balance = atm.AccountStatus(account);

        // then
        assertEquals(0, balance, 0.0);
    }

    // DepositFunds

    @Test // D+Z
    public void shouldReturn34Balance_givenDepositToAccount() {
        // given
        IAccount account = new Account();
        IAtm atm = new Atm();
        double amount = 34;

        // when
        account.DepositFunds(amount);
        double balance = atm.AccountStatus(account);

        // then
        assertEquals(amount, balance, 0.0);
    }

    @Test //D+Z
    public void shouldDepositFunds_givenCard() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        IAccount account = new Account();
        double amount = 123.13;

        // when
        card.AddAccount(account);
        atm.DepositFunds(card, amount);
        double res = atm.AccountStatus(card.GetAccount());

        // then
        assertEquals(amount, res, 0);
    }

    // WithdrawFunds

    @Test // Z+I
    public void shouldNotWithdraw_givenWithdrawFundsWithZeroBalance() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        IAccount account = new Account();
        double amount = 123.13;

        // when
        card.AddAccount(account);
        boolean isWithdrawn = atm.WithdrawFunds(card, amount);
        double balance = atm.AccountStatus(card.GetAccount());

        // then
        assertEquals(0, balance, 0);
        assertFalse(isWithdrawn);
    }

    //  DepositFunds and WithdrawFunds

    @Test // Z+Z+I
    public void shouldReturnZeroBalance_givenAccount_whenDoingDepositAndWithdrawal() {
        // given
        IAccount account = new Account();
        IAtm atm = new Atm();

        // when
        account.DepositFunds(34);
        account.WithdrawFunds(34);
        double balance = atm.AccountStatus(account);

        // then
        assertEquals(0, balance, 0.0);
    }

    @Test //D+Z+Z+I
    public void shouldReturnZeroBalance_givenCardAndDepositWithdrawFunds() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        IAccount account = new Account();
        double amount = 123.13;

        // when
        card.AddAccount(account);
        atm.DepositFunds(card, amount);
        atm.WithdrawFunds(card, amount);
        double res = atm.AccountStatus(card.GetAccount());

        // then
        assertEquals(0, res, 0);
    }

    // Transfer

    @Test // D+Z
    public void shouldTransfer_givenCardHaveEnoughMoney() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        IAccount account = new Account();
        IAccount accountRecipient = new Account();
        double amount = 123.13;

        // when
        card.AddAccount(account);
        atm.DepositFunds(card, amount);
        boolean isTransferred = atm.Transfer(card, accountRecipient, amount);
        double balance = atm.AccountStatus(card.GetAccount());
        double balanceRecipient = atm.AccountStatus(accountRecipient);

        // then
        assertEquals(0, balance, 0);
        assertTrue(isTransferred);
        assertEquals(amount, balanceRecipient, 0);
    }

    @Test // Z+I
    public void shouldNotTransfer_givenCardHaveNotEnoughMoney() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        IAccount account = new Account();
        IAccount accountRecipient = new Account();
        double smallerAmount = 2;
        double amount = 123.13;

        // when
        card.AddAccount(account);
        atm.DepositFunds(card, smallerAmount);
        boolean isTransferred = atm.Transfer(card, accountRecipient, amount);
        double balance = atm.AccountStatus(card.GetAccount());
        double balanceRecipient = atm.AccountStatus(accountRecipient);

        // then
        assertEquals(smallerAmount, balance, 0);
        assertFalse(isTransferred);
        assertEquals(0, balanceRecipient, 0);
    }

    @Test //D+D+Z
    public void shouldNotTransfer_givenCardHaveNoAccountConnected() {
        // given
        ICreditCard card = new CreditCard();
        IAtm atm = new Atm();
        IAccount accountRecipient = new Account();
        double smallerAmount = 2;
        double amount = 123.13;

        // when
        atm.DepositFunds(card, smallerAmount);
        boolean isTransferred = atm.Transfer(card, accountRecipient, amount);
        double balanceRecipient = atm.AccountStatus(accountRecipient);

        // then
        assertFalse(isTransferred);
        assertEquals(0, balanceRecipient, 0);
    }

    @Test // Z+I+I
    public void shouldReturnZeroBalances_givenTransferZero() {
        // given
        ICreditCard card1 = new CreditCard();
        ICreditCard card2 = new CreditCard();
        IAccount account1 = new Account();
        IAccount account2 = new Account();
        IAtm atm = new Atm();

        card1.AddAccount(account1);
        card2.AddAccount(account2);
        double amount = 0;

        // when
        boolean isTransferred = atm.Transfer(card1, account2, amount);
        double balance1 = atm.AccountStatus(card1.GetAccount());
        double balance2 = atm.AccountStatus(card2.GetAccount());

        // then
        assertFalse(isTransferred);
        assertEquals(0, balance1, 0);
        assertEquals(0, balance2, 0);
    }

    @Test // D+D+Z
    public void shouldNotTransfer_givenTransferCardToAccountConnectedToThisCard() {
        // given
        ICreditCard card1 = new CreditCard();
        IAccount account1 = new Account();
        IAtm atm = new Atm();
        card1.AddAccount(account1);
        double amount = 20;
        card1.DepositFunds(amount);

        // when
        boolean isTransferred = atm.Transfer(card1, account1, amount);
        double balance1 = atm.AccountStatus(card1.GetAccount());

        // then
        assertFalse(isTransferred);
        assertEquals(amount, balance1, 0);
    }
}