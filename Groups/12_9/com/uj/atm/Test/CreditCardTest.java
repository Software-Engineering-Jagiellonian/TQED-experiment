package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    @Test
    public void shouldReturnTrueAfterInit() { //D
        ICreditCard creditCard = new CreditCard();
        boolean valid = creditCard.IsPinValid("0000");
        assertTrue(valid);
    }

    @Test
    public void shouldNotChangeDifferentNewPinAndNewPinConfirm() { //Z
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = creditCard.ChangePin("1234", "4321", "0000");
        assertFalse(creditCard.IsPinValid("4321"));
        assertFalse(result);
    }

    @Test
    public void shouldChangePinWithoutInit() { //D + Z
        ICreditCard creditCard = new CreditCard();
        boolean result = creditCard.ChangePin("0000", "4321", "4321");
        assertTrue(creditCard.IsPinValid("4321"));
        assertTrue(result);
    }

    @Test
    public void shouldNotValidPinWhenPinContainsNotOnlyDigit() { //D
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("1ABC"));
    }

    @Test
    public void shouldNotValidPinWhenPinLongerThanFour() { //D
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("12345"));
    }

    @Test
    public void shouldNotDepositMoneyWhenAccountNotExist() { //D+Z
        ICreditCard creditCard = new CreditCard();
        creditCard.DepositFunds(3.00);
        assertNull(creditCard.GetAccount());
    }

    @Test
    public void shouldReturnDefaultNullAccountAfterInit() { //Z
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        assertNull(creditCard.GetAccount());
    }

    @Test
    public void shouldChangePinAfterInitAndChange() { //Z + Z
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.ChangePin("1234", "4321", "4321");
        assertTrue(creditCard.IsPinValid("4321"));
    }



    @Test
    public void shouldNotWithdrawMoneyWhenAccountNotExist() { //D
        ICreditCard creditCard = new CreditCard();
        creditCard.WithdrawFunds(3.00);
        assertNull(creditCard.GetAccount());
    }

    @Test
    public void shouldNotValidPinWhenPinShorterThanFour() {  //D
        ICreditCard creditCard = new CreditCard();
        assertFalse(creditCard.IsPinValid("123"));
    }

    @Test
    public void shouldReturnNullWhenAccountEmpty() { //D + Z
        ICreditCard creditCard = new CreditCard();
        IAccount result = creditCard.GetAccount();
        assertNull(result);
    }

    @Test
    public void shouldAddAccountWhenEmpty() { //D + Z
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        assertEquals(creditCard.GetAccount(), account);
    }

}