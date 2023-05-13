package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    @Test
    public void InitAssignDefaultPinBecausePinIsShorterThaFour() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("123", "123");
        assertTrue(creditCard.IsPinValid("0000"));
    }

    @Test
    public void InitDoesNotChangePinBecausePinContainsLetter() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("123f", "123f");
        assertTrue(creditCard.IsPinValid("0000"));
    }

    @Test
    public void ChangeDefaultPinWorksProperly() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("gg", "gg");
        boolean result = creditCard.ChangePin("0000", "1234", "1234");
        assertTrue(result);
    }

    @Test
    public void ChangePinReturnsFalseBecauseCardIsNotInitialize() {
        CreditCard creditCard = new CreditCard();
        boolean result = creditCard.ChangePin("0000", "1234", "1234");
        assertFalse(result);
    }

    @Test
    public void ChangePinReturnsFalseBecauseNewPinIsNotInCorrectFormat() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = creditCard.ChangePin("1234", "123f", "123f");
        assertFalse(result);
    }

    @Test
    public void ChangePinReturnsFalseBecauseConfirmPinIsDifferent() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = creditCard.ChangePin("1234", "1235", "1236");
        assertFalse(result);
    }

    @Test
    public void IsPinValidReturnFalseBecauseCreditCardIsNotInitialize() {
        CreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("0000"));
    }

    @Test
    public void IsPinValidReturnTrue() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertTrue(creditCard.IsPinValid("1234"));
    }

    @Test
    public void IsPinValidReturnFalse() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertFalse(creditCard.IsPinValid("1235"));
    }

    @Test
    public void GetAccountReturnNull() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertNull(creditCard.GetAccount());
    }

    @Test
    public void GetAccountWorkProperly() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        assertEquals(account, creditCard.GetAccount());
    }

    @Test
    public void GetAccountCanNotReturnSecondlyAssignAccount() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Account account = new Account();
        Account secondAccount = new Account();
        creditCard.AddAccount(account);
        creditCard.AddAccount(secondAccount);
        assertNotEquals(secondAccount, creditCard.GetAccount());
    }

    @Test
    public void DepositFundsReturnsFalseBecauseAccountIsNotAssign() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = creditCard.DepositFunds(100);
        assertFalse(result);
    }

    @Test
    public void DepositFundsReturnsFalseBecauseCardIsNotInitialize() {
        CreditCard creditCard = new CreditCard();
        Account account = new Account();
        creditCard.AddAccount(account);
        boolean result = creditCard.DepositFunds(100);
        assertFalse(result);
    }

    @Test
    public void DepositFundsWorksProperly() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        boolean result = creditCard.DepositFunds(100);
        assertTrue(result);
    }

    @Test
    public void WithdrawFundsReturnsFalseBecauseAccountIsNotAssign() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = creditCard.WithdrawFunds(100);
        assertFalse(result);
    }

    @Test
    public void WithdrawFundsReturnsFalseBecauseAccountAmountIsSmaller() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.DepositFunds(100);
        boolean result = creditCard.WithdrawFunds(150);
        assertFalse(result);
    }

    @Test
    public void WithdrawFundsReturnsFalseBecauseCardIsNotInitialize() {
        CreditCard creditCard = new CreditCard();
        Account account = new Account();
        creditCard.AddAccount(account);
        boolean result = creditCard.WithdrawFunds(100);
        assertFalse(result);
    }

    @Test
    public void WithdrawFundsWorksProperly() {
        CreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Account account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(100);
        boolean result = creditCard.WithdrawFunds(100);
        assertTrue(result);
    }
}