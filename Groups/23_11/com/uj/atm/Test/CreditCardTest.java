package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    private final static String DEFAULT_PIN = "0000";

    private final static String INIT_PIN = "1111";

    private final static String INVALID_INIT_PIN = "1112";

    private final static String INVALID_INIT_PIN_ALPHANUMERIC = "1a11";

    private final static String INVALID_INIT_PIN_LONGER = "11111";

    private final static String INVALID_INIT_PIN_SHORTER = "111";

    private final static String NEW_PIN = "2222";


    @Test
    public void shouldReturnDefaultPinAfterInit() { //D

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        boolean valid = creditCard.IsPinValid(DEFAULT_PIN);

        //then
        assertTrue(valid);
    }

    @Test
    public void shouldReturnDefaultNullAccountAfterInit() { //Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        creditCard.Init(INIT_PIN, INIT_PIN);

        //then
        assertNull(creditCard.GetAccount());
    }

    @Test
    public void shouldChangePinAfterInitAndChange() { //Z + Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.ChangePin(INIT_PIN, NEW_PIN, NEW_PIN);

        //then
        assertTrue(creditCard.IsPinValid(NEW_PIN));
    }

    @Test
    public void shouldNotChangeDifferentNewPinAndNewPinConfirm() { //Z + Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        creditCard.Init(INIT_PIN, INIT_PIN);
        boolean result = creditCard.ChangePin(INIT_PIN, NEW_PIN, DEFAULT_PIN);

        //then
        assertFalse(creditCard.IsPinValid(NEW_PIN));
        assertFalse(result);
    }

    @Test
    public void shouldChangePinWithoutInit() { //D + Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        boolean result = creditCard.ChangePin(DEFAULT_PIN, NEW_PIN, NEW_PIN);

        //then
        assertTrue(creditCard.IsPinValid(NEW_PIN));
        assertTrue(result);
    }

    @Test
    public void shouldNotChangePinInvalidOldPin() { //Z + Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        creditCard.Init(INIT_PIN, INIT_PIN);
        boolean result = creditCard.ChangePin(INVALID_INIT_PIN, NEW_PIN, NEW_PIN);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldNotValidPinWhenPinContainsNotOnlyDigit() { //D

        //given
        ICreditCard creditCard = new CreditCard();

        //when

        //then
        assertFalse(creditCard.IsPinValid(INVALID_INIT_PIN_ALPHANUMERIC));
    }

    @Test
    public void shouldNotValidPinWhenPinLongerThanFour() { //D

        //given
        ICreditCard creditCard = new CreditCard();

        //when

        //then
        assertFalse(creditCard.IsPinValid(INVALID_INIT_PIN_LONGER));
    }

    @Test
    public void shouldNotValidPinWhenPinShorterThanFour() {  //D

        //given
        ICreditCard creditCard = new CreditCard();

        //when

        //then
        assertFalse(creditCard.IsPinValid(INVALID_INIT_PIN_SHORTER));
    }

    @Test
    public void shouldAddAccountWhenEmpty() { //D + Z

        //given
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();

        //when
        creditCard.AddAccount(account);

        //then
        assertEquals(creditCard.GetAccount(), account);
    }

    @Test
    public void shouldReturnNullWhenAccountEmpty() { //D+Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        IAccount result = creditCard.GetAccount();

        //then
        assertNull(result);
    }

    @Test
    public void shouldNotDepositMoneyWhenAccountNotExist() { //D+Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        creditCard.DepositFunds(3.00);

        //then
        assertNull(creditCard.GetAccount());
    }

    @Test
    public void shouldNotWithdrawMoneyWhenAccountNotExist() { //D+Z

        //given
        ICreditCard creditCard = new CreditCard();

        //when
        creditCard.WithdrawFunds(3.00);

        //then
        assertNull(creditCard.GetAccount());
    }
}