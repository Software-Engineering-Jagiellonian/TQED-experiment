package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {
    private CreditCard creditCard = new CreditCard();
    String defaultCardPin = "0000";

//region Init
    @Test
    public void initCorrectPins() {
        try{
            creditCard.Init("1234","1234");
        }
        catch (Exception e){
            throw e;
        }
    }


//endregion Init

//region ChangePin
    @Test
    public void changePinCorrect() {
        String newPin = "5678";
        assertTrue(creditCard.ChangePin(defaultCardPin, newPin, newPin));
    }
    @Test
    public void changePinIncorrectOldPin() {
        String newPin = "5678";
        assertFalse(creditCard.ChangePin(newPin, newPin, newPin));
    }
    @Test
    public void changePinIncorrectNewPin() {
        String newPin = "5678";
        assertFalse(creditCard.ChangePin(defaultCardPin, defaultCardPin, newPin));
    }
    @Test
    public void changePinIncorrectConfirmPin() {
        String newPin = "5678";
        assertFalse(creditCard.ChangePin(defaultCardPin, newPin, defaultCardPin));
    }
    @Test
    public void changePinNewPinLong() {
        String newPin = "56789";
        assertFalse(creditCard.ChangePin(defaultCardPin, newPin, newPin));
    }

//endregion ChangePi

//region IsPinValid

    @Test
    public void isPinValidCorrect() {
        assertTrue(creditCard.IsPinValid(defaultCardPin));
    }

    @Test
    public void isPinValidAnotherPin() {
        String anotherPin = "5678";

        assertFalse(creditCard.IsPinValid(anotherPin));
    }

    @Test
    public void isPinValidPinLonger() {
        String longerPin = "12345";

        assertFalse(creditCard.IsPinValid(longerPin));
    }

    @Test
    public void isPinValidPinShorter() {
        String shorterPin = "123";

        assertFalse(creditCard.IsPinValid(shorterPin));
    }

    @Test
    public void isPinValidAfterInit() {
        String newPin = "1234";
        creditCard.Init(newPin, newPin);

        assertTrue(creditCard.IsPinValid(newPin));
    }
//endregion IsPinValid

//region AddAccount

    @Test
    public void addAccount() {
        Account account = new Account();
        try{
            creditCard.AddAccount(account);
        }
        catch (Exception e){
            throw e;
        }
    }

//endregion AddAccount

//region GetAccount

    @Test
    public void getAccountCorrect() {
        Account account = new Account();
        creditCard.AddAccount(account);

        assertEquals(account,creditCard.GetAccount());
    }

    @Test
    public void getAccountFail() {
        Account account = new Account();
        Account account1 = new Account();
        creditCard.AddAccount(account);

        assertNotEquals(account1,creditCard.GetAccount());
    }

//endregion GerAccount

//region DepositFunds

    @Test
    public void depositFundsCorrectInt() {
        Account account = new Account();
        creditCard.AddAccount(account);

        assertTrue(creditCard.DepositFunds(100));
    }

    @Test
    public void depositFundsCorrectFloatPoin() {
        Account account = new Account();
        creditCard.AddAccount(account);

        assertTrue(creditCard.DepositFunds(0.5));
    }

    @Test
    public void depositFundsWithoutAccount() {
        assertFalse(creditCard.DepositFunds(0.5));
    }

    @Test
    public void depositFundsZero() {
        Account account = new Account();
        creditCard.AddAccount(account);
        assertTrue(creditCard.DepositFunds(0.0));
    }

//endregion DepositFunds

//region WithdrawFunds

    @Test
    public void withdrawFundsCorrect() {
        Account account = new Account();
        creditCard.AddAccount(account);
        account.DepositFunds(100);

        assertTrue(creditCard.WithdrawFunds(50));
    }

    @Test
    public void withdrawFundsCorrectFloatPoint() {
        Account account = new Account();
        creditCard.AddAccount(account);
        account.DepositFunds(100);

        assertTrue(creditCard.WithdrawFunds(0.5));
    }

    @Test
    public void withdrawFundsWithoutAccount() {
        assertFalse(creditCard.WithdrawFunds(0.5));
    }

    @Test
    public void withdrawFundsMoreThanAmount() {
        Account account = new Account();
        creditCard.AddAccount(account);
        account.DepositFunds(100);

        assertFalse(creditCard.WithdrawFunds(200));
    }

    @Test
    public void withdrawFundsSmallMoreThanAmount() {
        Account account = new Account();
        creditCard.AddAccount(account);

        assertFalse(creditCard.WithdrawFunds(0.0000000000000000000000001));
    }

//endregion WithdrawFunds

}