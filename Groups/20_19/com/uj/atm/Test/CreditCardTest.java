package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class CreditCardTest {

	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    // Constructor

    @Test // D
    public void shouldHaveDefaultPin_givenNewCardObjectCreated() {
        // given
        ICreditCard card = new CreditCard();
        String defaultPin = "0000";

        // when

        // then
        assertTrue(card.IsPinValid(defaultPin));
    }

    // Init

    @Test // D+Z
    public void shouldInitWithNewPass_givenNewPinIsCorrect() {
        // given
        ICreditCard card = new CreditCard();
        String newPin = "0111";

        // when
        card.Init(newPin, newPin);

        // then
        assertTrue(card.IsPinValid(newPin));
    }

    @Test(expected = IllegalArgumentException.class) // D+Z
    public void shouldThrowException_whenInit_givenNewPinIsNotEqualToNewPinConfirm() {
        // given
        ICreditCard card = new CreditCard();
        String newPin = "0111";
        String newPinConfirm = "2111";

        // when
        card.Init(newPin, newPinConfirm);

        // then
    }

    @Test(expected = IllegalArgumentException.class) // Z+I
    public void shouldThrowException_whenInit_givenNewPinIsLongerThan4() {
        // given
        ICreditCard card = new CreditCard();
        String newPin = "032323111";
        String newPinConfirm = "2111";

        // when
        card.Init(newPin, newPinConfirm);

        // then
    }

    // ChangePin

    @Test // D+Z
    public void shouldReturnTrue_whenChangePin_givenArgsAreCorrect() {
        // given
        ICreditCard card = new CreditCard();
        String oldPin = "0000";
        String newPin = "2111";
        String newPinConfirm = "2111";

        // when
        boolean isPinChanged = card.ChangePin(oldPin, newPin, newPinConfirm);

        // then
        assertTrue(isPinChanged);
    }

    @Test // D+Z
    public void shouldReturnFalse_whenChangePin_givenOldPinIsInvalid() {
        // given
        ICreditCard card = new CreditCard();
        String oldPin = "0030";
        String newPin = "2111";
        String newPinConfirm = "2111";

        // when
        boolean isPinChanged = card.ChangePin(oldPin, newPin, newPinConfirm);

        // then
        assertFalse(isPinChanged);
    }

    @Test // D
    public void shouldReturnFalse_whenChangePin_givenNewPinNotEqualsNewPinConfirm() {
        // given
        ICreditCard card = new CreditCard();
        String oldPin = "0030";
        String newPin = "2111";
        String newPinConfirm = "2121";

        // when
        boolean isPinChanged = card.ChangePin(oldPin, newPin, newPinConfirm);

        // then
        assertFalse(isPinChanged);
    }

    @Test // D+Z
    public void shouldReturnTrue_whenChangePin_givenCardInitToAnotherPin() {
        // given
        ICreditCard card = new CreditCard();
        String pin = "0030";
        String newPin = "2121";
        String newPinConfirm = "2121";

        // when
        card.Init(pin, pin);
        boolean isPinChanged = card.ChangePin(pin, newPin, newPinConfirm);

        // then
        assertTrue(isPinChanged);
    }

    @Test // D
    public void shouldReturnFalse_whenChangePin_givenNewPinContainsInvalidChars() {
        // given
        ICreditCard card = new CreditCard();
        String newPin = "aaaa";
        String newPinConfirm = "aaaa";
        String oldPin = "0000";

        // when
        boolean isPinChanged = card.ChangePin(oldPin, newPin, newPinConfirm);

        // then
        assertFalse(isPinChanged);
    }

    // isPinValid

    @Test // D
    public void shouldReturnTrue_whenIsPinValid_givenValidPin() {
        // given
        ICreditCard card = new CreditCard();
        String pin = "0000";

        // when
        boolean isPinValid = card.IsPinValid(pin);

        // then
        assertTrue(isPinValid);
    }

    @Test // D
    public void shouldReturnFalse_whenIsPinValid_givenPinNotEqualsToCardPin() {
        // given
        ICreditCard card = new CreditCard();
        String pin = "0030";

        // when
        boolean isPinValid = card.IsPinValid(pin);

        // then
        assertFalse(isPinValid);
    }

    @Test // D+I
    public void shouldReturnFalse_whenIsPinValid_givenPinIsTooLong() {
        // given
        ICreditCard card = new CreditCard();
        String pin = "00300";

        // when
        boolean isPinValid = card.IsPinValid(pin);

        // then
        assertFalse(isPinValid);
    }

    @Test // D+I
    public void shouldReturnFalse_whenIsPinValid_givenPinIsTooShort() {
        // given
        ICreditCard card = new CreditCard();
        String pin = "300";

        // when
        boolean isPinValid = card.IsPinValid(pin);

        // then
        assertFalse(isPinValid);
    }

    @Test // D
    public void shouldReturnFalse_whenIsPinValid_givenPinContainsInValidChars() {
        // given
        ICreditCard card = new CreditCard();
        String pin = "lo0l";

        // when
        boolean isPinValid = card.IsPinValid(pin);

        // then
        assertFalse(isPinValid);
    }

    // AddAccount

    @Test // Z
    public void shouldAddAccount_whenAddAccount_givenNoAccountSetBefore() {
        // given
        ICreditCard card = new CreditCard();
        IAccount account = new Account();

        // when
        card.AddAccount(account);

        // then
        assertThat(account, equalTo(card.GetAccount()));
    }

    @Test // Z+Z
    public void shouldAddAccount_whenAddAccount_givenAccountWasSetBefore() {
        // given
        ICreditCard card = new CreditCard();
        IAccount account1 = new Account();
        IAccount account2 = new Account();
        card.AddAccount(account1);

        // when
        card.AddAccount(account2);

        // then
        assertThat(account1, equalTo(card.GetAccount()));
    }

    // GetAccount

    @Test // Z+Z
    public void shouldReturnAccount_whenGetAccount_givenAccountWasSetBefore() {
        // given
        ICreditCard card = new CreditCard();
        IAccount account1 = new Account();
        card.AddAccount(account1);

        // when
        IAccount returnedAccount = card.GetAccount();

        // then
        assertThat(account1, equalTo(returnedAccount));
    }

    // DepositFunds

    @Test // D+Z
    public void shouldReturnTrue_whenDepositFunds_givenAccountWasSetBefore() {
        // given
        ICreditCard card = new CreditCard();
        IAccount account1 = new Account();
        card.AddAccount(account1);
        double amount = 2000.2;

        // when
        boolean areFundsDeposited = card.DepositFunds(amount);

        // then
        assertTrue(areFundsDeposited);
    }

    @Test // D+Z
    public void shouldReturnFalse_whenDepositFunds_givenAccountWasNotSetBefore() {
        // given
        ICreditCard card = new CreditCard();
        double amount = 2000.2;

        // when
        boolean areFundsDeposited = card.DepositFunds(amount);

        // then
        assertFalse(areFundsDeposited);
    }

    @Test // I+D
    public void shouldReturnFalse_whenDepositFunds_givenAmountIsZero() {
        // given
        ICreditCard card = new CreditCard();
        double amount = 0;

        // when
        boolean areFundsDeposited = card.DepositFunds(amount);

        // then
        assertFalse(areFundsDeposited);
    }

    @Test // I+Z
    public void shouldReturnFalse_whenDepositFunds_givenAmountIsNegative() {
        // given
        ICreditCard card = new CreditCard();
        double amount = -10;

        // when
        boolean areFundsDeposited = card.DepositFunds(amount);

        // then
        assertFalse(areFundsDeposited);
    }

    // WithdrawFunds

    @Test // D+Z
    public void shouldReturnTrue_whenWithdrawFunds_givenAccountHaveEnoughMoney() {
        // given
        ICreditCard card = new CreditCard();
        double amountOnCard = 23.;
        double amountToWithdraw = 10.;
        IAccount account1 = new Account();
        card.AddAccount(account1);
        card.DepositFunds(amountOnCard);

        // when
        boolean areFundsWithdrawn = card.WithdrawFunds(amountToWithdraw);

        // then
        assertEquals(amountOnCard - amountToWithdraw, card.GetAccount().AccountStatus(), 0);
        assertTrue(areFundsWithdrawn);
    }

    @Test // I
    public void shouldReturnTrue_whenWithdrawFunds_givenAllMoneyAreWithdrawn() {
        // given
        ICreditCard card = new CreditCard();
        double amountOnCard = 10;
        double amountToWithdraw = 10;
        IAccount account1 = new Account();
        card.AddAccount(account1);
        card.DepositFunds(amountOnCard);

        // when
        boolean areFundsWithdrawn = card.WithdrawFunds(amountToWithdraw);

        // then
        assertEquals(0, card.GetAccount().AccountStatus(), 0);
        assertTrue(areFundsWithdrawn);
    }

    @Test // I
    public void shouldReturnFalse_whenWithdrawFunds_givenAmountToWithdrawIsNegative() {
        // given
        ICreditCard card = new CreditCard();
        double amountOnCard = 10;
        double amountToWithdraw = -1;
        IAccount account1 = new Account();
        card.AddAccount(account1);
        card.DepositFunds(amountOnCard);

        // when
        boolean areFundsWithdrawn = card.WithdrawFunds(amountToWithdraw);

        // then
        assertEquals(amountOnCard, card.GetAccount().AccountStatus(), 0);
        assertFalse(areFundsWithdrawn);
    }

    @Test // D
    public void shouldReturnFalse_whenWithdrawFunds_givenAccountIsNotSet() {
        // given
        ICreditCard card = new CreditCard();
        double amountToWithdraw = 1;

        // when
        boolean areFundsWithdrawn = card.WithdrawFunds(amountToWithdraw);

        // then
        assertFalse(areFundsWithdrawn);
    }
}