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


    @Test
    public void shouldLogInAccountCorrect() { //Z + Z + Z
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.Init("1234", "1234");
        creditCard.AddAccount(account);
        boolean result = atm.CheckPinAndLogIn(creditCard, "1234");
        assertTrue(result);
    }

    @Test
    public void shouldNotLogInAccountPinInvalid() { //Z + Z + Z
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.Init("1234", "1234");
        creditCard.AddAccount(account);
        boolean result = atm.CheckPinAndLogIn(creditCard, "1235");
        assertFalse(result);
    }

    @Test
    public void shouldReturnZeroAfterCheckStatusNotLogged() { //D
        IAtm atm = new Atm();
        IAccount account = new Account();
        double result = atm.AccountStatus(account);
        assertEquals(result, 0, 0.001);
    }

    @Test
    public void shouldReturnStatusAfterCheckStatusLogged() { //Z + D
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(50.00);
        creditCard.Init("1234", "1234");
        creditCard.AddAccount(account);
        boolean logged = atm.CheckPinAndLogIn(creditCard, "1234");
        double result = atm.AccountStatus(account);
        assertTrue(logged);
        assertEquals(result, 50, 0.001);
    }

    @Test
    public void shouldNotChangePinInvalidOldPin() { // Z
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean result = atm.ChangePinCard(creditCard, "1235", "4321", "4321");
        assertFalse(result);
    }

    @Test
    public void shouldWithdrawFundsWhenLoggedButMoreThanYouHave() { //D + Z + Z + Z
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.AddAccount(account);
        creditCard.DepositFunds(100);
        atm.CheckPinAndLogIn(creditCard, "1234");
        boolean result = atm.WithdrawFunds(creditCard, 100.01);
        assertFalse(result);
        assertEquals(atm.AccountStatus(account), 100, 0.001);
    }

    @Test
    public void shouldTransferWhenLogged() { //D + Z + Z + D
        IAtm atm = new Atm();
        IAccount account1 = new Account();
        IAccount account2 = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.AddAccount(account1);
        creditCard.DepositFunds(100);
        atm.CheckPinAndLogIn(creditCard, "1234");
        boolean result = atm.Transfer(creditCard, account2, 99.99);
        assertTrue(result);
        assertEquals(atm.AccountStatus(account1), 0.01, 0.001);
        assertEquals(atm.AccountStatus(account2), 99.99, 0.001);
    }

    @Test
    public void shouldChangePin() { //Z
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.AddAccount(account);
        boolean result = atm.ChangePinCard(creditCard, "1234", "4321", "4321");
        assertTrue(result);
        assertTrue(atm.CheckPinAndLogIn(creditCard, "4321"));
    }

    @Test
    public void shouldWithdrawFundsWhenLogged() { //D + Z + Z + Z
        IAtm atm = new Atm();
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        creditCard.AddAccount(account);
        creditCard.DepositFunds(100);
        atm.CheckPinAndLogIn(creditCard, "1234");
        boolean result = atm.WithdrawFunds(creditCard, 51.0);
        assertTrue(result);
        assertEquals(atm.AccountStatus(account), 49, 0.001);
    }

}