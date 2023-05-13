package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
	IAtm atm;

    @Before
    public void BeforeEach() {
        atm = new Atm();
    }

    // D- null;null; A - LogIn
    @Test
    public void LogInNull2() {
        assertFalse(atm.CheckPinAndLogIn(null, null));
    }

    // D- null; A - LogIn
    @Test
    public void LogInNull() {
        assertFalse(atm.CheckPinAndLogIn(null, "1111"));
    }
    // D- wrong; A - LogIn
    @Test
    public void LogInWrongPin() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        assertFalse(atm.CheckPinAndLogIn(card, "1112"));
    }

    // A - LogIn
    @Test
    public void LogIn() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        assertTrue(atm.CheckPinAndLogIn(card, "1111"));
    }

    // A - AccountStatus
    @Test(expected = IllegalArgumentException.class)
    public void AccountStatusNoLogIn() {
        IAccount acc = new Account();
        atm.AccountStatus(acc);
    }

    // D- null, A - AccountStatus
    @Test(expected = IllegalArgumentException.class)
    public void AccountStatusNull() {
        atm.AccountStatus(null);
    }

    // D- wrongAccount, A - AccountStatus
    @Test(expected = IllegalArgumentException.class)
    public void AccountStatusWrong() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        atm.AccountStatus(new Account());
    }

    // D- Account, A - AccountStatus
    @Test
    public void AccountStatus() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        atm.CheckPinAndLogIn(card, "1111");
        assertEquals(atm.AccountStatus(acc), 0.0, 0.001);
    }

    //D - null, A - ChangePin
    @Test(expected = IllegalArgumentException.class)
    public void ChangePinNoCard() {
        atm.ChangePinCard(null, "1111", "1112", "1112");
    }
    //A - ChangePin
    @Test(expected = IllegalArgumentException.class)
    public void ChangePinNoLogIn() {
        ICreditCard card = new CreditCard();
        atm.ChangePinCard(card, "1111", "1112", "1112");
    }
    //D - wrong card, A - ChangePin
    @Test(expected = IllegalArgumentException.class)
    public void ChangePinWrongCard() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        atm.CheckPinAndLogIn(card, "1111");
        atm.ChangePinCard(new CreditCard(), "1111", "1112", "1112");
    }
    // A - ChangePin
    @Test
    public void ChangePin() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        atm.CheckPinAndLogIn(card, "1111");
        assertTrue(atm.ChangePinCard(card, "1111", "1112", "1112"));
    }

    //D - null, A - Deposit
    @Test(expected = IllegalArgumentException.class)
    public void DepositNoCard() {
        atm.DepositFunds(null, 2.0);
    }
    //A - Deposit
    @Test(expected = IllegalArgumentException.class)
    public void DepositNoLogIn() {
        ICreditCard card = new CreditCard();
        atm.DepositFunds(card, 2.0);
    }
    //D - wrong card, A - Deposit
    @Test(expected = IllegalArgumentException.class)
    public void DepositWrongCard() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        atm.CheckPinAndLogIn(card, "1111");
        atm.DepositFunds(new CreditCard(), 2.0);
    }
    // A - Deposit
    @Test
    public void Deposit() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        atm.CheckPinAndLogIn(card, "1111");
        assertTrue(atm.DepositFunds(card, 2.0));
        assertEquals(atm.AccountStatus(acc), 2.0, 0.001);
    }

    //D - null, A - Withdraw
    @Test(expected = IllegalArgumentException.class)
    public void WithdrawNoCard() {
        atm.WithdrawFunds(null, 0.0);
    }
    //A - Withdraw
    @Test(expected = IllegalArgumentException.class)
    public void WithdrawNoLogIn() {
        ICreditCard card = new CreditCard();
        atm.WithdrawFunds(card, 0.0);
    }
    //D - wrong card, A - Withdraw
    @Test(expected = IllegalArgumentException.class)
    public void WithdrawWrongCard() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        atm.CheckPinAndLogIn(card, "1111");
        atm.WithdrawFunds(new CreditCard(), 0.0);
    }
    // A - Withdraw
    @Test
    public void Withdraw() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        atm.CheckPinAndLogIn(card, "1111");
        assertTrue(atm.DepositFunds(card, 2.0));
        assertTrue(atm.WithdrawFunds(card, 1.0));
        assertEquals(atm.AccountStatus(acc), 1.0, 0.001);
    }

    //D - null;null, A - Transfer
    @Test(expected = IllegalArgumentException.class)
    public void TransferNoCard() {
        atm.Transfer(null, null, 0.0);
    }
    //D - null, A - Transfer
    @Test(expected = IllegalArgumentException.class)
    public void TransferNoAccount() {
        ICreditCard card = new CreditCard();
        atm.Transfer(card, null, 0.0);
    }
    //A - Transfer
    @Test(expected = IllegalArgumentException.class)
    public void TransferNoLogIn() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        IAccount acc2 = new Account();
        atm.Transfer(card, acc2, 0.0);
    }
    //D - wrong card, A - Withdraw
    @Test(expected = IllegalArgumentException.class)
    public void TransferWrongCard() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        atm.CheckPinAndLogIn(card, "1111");
        IAccount acc2 = new Account();
        atm.Transfer(new CreditCard(), acc2, 0.0);
    }
    // D - No cash; A - Transfer
    @Test
    public void TransferNoCash() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        IAccount acc2 = new Account();
        atm.CheckPinAndLogIn(card, "1111");
        assertTrue(atm.DepositFunds(card, 2.0));
        assertFalse(atm.Transfer(card, acc2, 3.0));
        assertEquals(acc.AccountStatus(), 2.0, 0.001);
        assertEquals(acc2.AccountStatus(), 0.0, 0.001);
    }

    // A - Transfer
    @Test
    public void Transfer() {
        ICreditCard card = new CreditCard();
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        IAccount acc2 = new Account();
        atm.CheckPinAndLogIn(card, "1111");
        assertTrue(atm.DepositFunds(card, 2.0));
        assertTrue(atm.Transfer(card, acc2, 1.0));
        assertEquals(acc.AccountStatus(), 1.0, 0.001);
        assertEquals(acc2.AccountStatus(), 1.0, 0.001);
    }

    // Time impact impossible
}