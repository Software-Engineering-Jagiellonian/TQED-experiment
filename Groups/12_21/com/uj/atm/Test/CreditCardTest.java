package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    //D+Z
    @Test
    public void ChangePin_shouldReturnTrueForNewValidPin() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertTrue(creditCard.ChangePin("1234", "4312", "4312"));
    }

    //D+Z
    @Test
    public void ChangePin_shouldReturnFalseForZeroPinAfterInit() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.ChangePin("0000", "0000", "0000"));
    }

    //D+Z+Z
    @Test
    public void ChangePin_shouldReturnFalseForOldPin() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertFalse(creditCard.ChangePin("1234", "1234", "1234"));
    }

    //D+Z
    @Test
    public void ChangePin_shouldReturnFalseForWrongPinConfirmation() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.ChangePin("1234", "1234", "1243"));
    }

    //D+Z
    @Test
    public void ChangePin_shouldReturnFalseForOldPinNotEqualToCurrentOne() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.ChangePin("0001", "1234", "1243"));
    }

    //D+Z+Z
    @Test
    public void ChangePin_shouldReturnFalseForNotValidNewPin() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertFalse(creditCard.ChangePin("1234", "54A3", "54A3"));
    }

    //D+Z
    @Test
    public void IsPinValid_shouldReturnTrueForValidInput() {
        ICreditCard creditCard = new CreditCard();
        assertTrue(creditCard.IsPinValid("1234"));
    }

    //D+Z
    @Test
    public void IsPinValid_shouldReturnFalseForNonNumericInput() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("1e4"));
    }

    //D+Z
    @Test
    public void IsPinValid_shouldReturnFalseForTooLongInput() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("12345"));
    }

    //D+Z
    @Test
    public void IsPinValid_shouldReturnFalseForTooShortInput() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("000"));
    }

    //D+Z+I
    @Test
    public void IsPinValid_shouldReturnFalseForNullInput() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid(null));
    }

    //D+Z+I
    @Test
    public void IsPinValid_shouldReturnFalseForEmptyInput() {
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid(""));
    }

    //D+Z
    @Test
    public void AddAccount_shouldAddAccountToNotConnectedCreditCardToAnyAccount() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        assertEquals(123.45, creditCard.GetAccount().AccountStatus(), 0);
    }

    //D+Z+Z
    @Test
    public void AddAccount_shouldNotChangeAccountWhenAccountIsAssignedToCard() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);

        IAccount newAccount = new Account();
        newAccount.DepositFunds(321.54);
        creditCard.AddAccount(newAccount);
        assertEquals(123.45, creditCard.GetAccount().AccountStatus(), 0);
    }

    //D+Z
    @Test
    public void GetAccount_returnNullWhenCardNotConnectedToAnyAccount() {
        ICreditCard creditCard = new CreditCard();
        assertNull(creditCard.GetAccount());
    }

    //D+Z+Z
    @Test
    public void DepositFunds_returnTrueWhenDepositChangeAccountBalance() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        assertTrue(creditCard.DepositFunds(123.45));
    }

    //D+Z+Z
    @Test
    public void WithdrawFunds_returnTrueWhenWithdrawalChangeAccountBalance() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        assertTrue(creditCard.WithdrawFunds(123.45));
    }

    //D+Z+Z+Z+I
    @Test
    public void WithdrawFunds_returnTrueWhenWithdrawalChangeAccountBalanceTwice() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        assertTrue(creditCard.WithdrawFunds(23.45));
        assertTrue(creditCard.WithdrawFunds(100));
    }

}