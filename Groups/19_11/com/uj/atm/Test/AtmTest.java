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

    public static final String INIT_PIN = "1111";

    public static final String INVALID_INIT_PIN = "1112";

    public static final String NEW_PIN = "2222";

    @Test
    public void shouldNotLogInAccountNull() { //Z + Z

        //given
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();

        //when
        creditCard.Init(INIT_PIN, INIT_PIN);
        boolean result = atm.CheckPinAndLogIn(creditCard, INIT_PIN);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldLogInAccountCorrect() { //Z + Z + Z

        //given
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();

        //when
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);
        boolean result = atm.CheckPinAndLogIn(creditCard, INIT_PIN);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldNotLogInAccountPinInvalid() { //Z + Z + Z

        //given
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();

        //when
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);
        boolean result = atm.CheckPinAndLogIn(creditCard, INVALID_INIT_PIN);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldReturnZeroAfterCheckStatusNotLogged() { //D

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();

        //when
        double result = atm.AccountStatus(account);

        //then
        assertEquals(result, 0,0.001);
    }

    @Test
    public void shouldReturnStatusAfterCheckStatusLogged() { //Z + D

        //given
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();

        account.DepositFunds(50.00);
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);

        //when
        boolean logged = atm.CheckPinAndLogIn(creditCard, INIT_PIN);
        double result = atm.AccountStatus(account);

        //then
        assertTrue(logged);
        assertEquals(result, 50,0.001);
    }

    @Test
    public void shouldNotChangePinInvalidOldPin() { // Z

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);

        //when
        boolean result = atm.ChangePinCard(creditCard, INVALID_INIT_PIN, NEW_PIN, NEW_PIN);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldChangePin() { //Z

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);

        //when
        boolean result = atm.ChangePinCard(creditCard, INIT_PIN, NEW_PIN, NEW_PIN);

        //then
        assertTrue(result);
        assertTrue(atm.CheckPinAndLogIn(creditCard, NEW_PIN));
    }

    @Test
    public void shouldNotDepositFundsWithoutLog() { //D + D + Z

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);

        //when
        boolean result = atm.DepositFunds(creditCard, 50.0);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldDepositFundsWhenLogged() { //D + D + Z + Z

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);

        //when
        atm.CheckPinAndLogIn(creditCard, INIT_PIN);
        boolean result = atm.DepositFunds(creditCard, 50.0);

        //then
        assertTrue(result);
        assertEquals(atm.AccountStatus(account), 50, 0.001);
    }

    @Test
    public void shouldNotWithdrawFundsWithoutLog() { //D + Z + Z + Z

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);
        boolean firstDeposit = creditCard.DepositFunds(50.0);

        //when
        boolean result = atm.WithdrawFunds(creditCard, 40.0);

        //then
        assertTrue(firstDeposit);
        assertFalse(result);
    }

    @Test
    public void shouldWithdrawFundsWhenLogged() { //D + Z + Z + Z

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);
        creditCard.DepositFunds(100);

        //when
        atm.CheckPinAndLogIn(creditCard, INIT_PIN);
        boolean result = atm.WithdrawFunds(creditCard, 51.0);

        //then
        assertTrue(result);
        assertEquals(atm.AccountStatus(account), 49, 0.001);
    }

    @Test
    public void shouldWithdrawFundsWhenLoggedButMoreThanYouHave() { //D + Z + Z + Z

        //given
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account);
        creditCard.DepositFunds(100);

        //when
        atm.CheckPinAndLogIn(creditCard, INIT_PIN);
        boolean result = atm.WithdrawFunds(creditCard, 100.01);

        //then
        assertFalse(result);
        assertEquals(atm.AccountStatus(account), 100, 0.001);
    }

    @Test
    public void shouldNotTransferWhenNotLogged() { //D + Z + Z + Z

        //given
        IAtm atm = new Atm();
        IAccount account1 = new Account();
        IAccount account2 = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account1);
        creditCard.DepositFunds(100);

        //when
        boolean result = atm.Transfer(creditCard, account2,99.99);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldTransferWhenLogged() { //D + Z + Z + Z

        //given
        IAtm atm = new Atm();
        IAccount account1 = new Account();
        IAccount account2 = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account1);
        creditCard.DepositFunds(100);

        //when
        atm.CheckPinAndLogIn(creditCard, INIT_PIN);
        boolean result = atm.Transfer(creditCard, account2,99.99);

        //then
        assertTrue(result);
        assertEquals(atm.AccountStatus(account1), 0.01, 0.001);
        assertEquals(atm.AccountStatus(account2), 99.99, 0.001);
    }

    @Test
    public void shouldTransferWhenLoggedButMoreThanYouHave() { //D + Z + Z + Z

        //given
        IAtm atm = new Atm();
        IAccount account1 = new Account();
        IAccount account2 = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init(INIT_PIN, INIT_PIN);
        creditCard.AddAccount(account1);
        creditCard.DepositFunds(100);

        //when
        atm.CheckPinAndLogIn(creditCard, INIT_PIN);
        boolean result = atm.Transfer(creditCard, account2,100.01);

        //then
        assertFalse(result);
        assertEquals(atm.AccountStatus(account1), 100, 0.001);
        assertEquals(atm.AccountStatus(account2), 0, 0.001);
    }
}