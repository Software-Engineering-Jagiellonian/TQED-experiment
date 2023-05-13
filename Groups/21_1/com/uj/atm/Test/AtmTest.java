package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {

    private IAtm atm;
    private ICreditCard card;
    private IAccount account;
    private IAccount account2;


    @Before
    public void setup(){
        atm = new Atm();
        card = new CreditCard();
        account = new Account();
        account2 = new Account();
    }

    @Test
    public void checkPinAndLogIn() {
        Assert.assertTrue(atm.CheckPinAndLogIn(card, "0000"));
        Assert.assertFalse(atm.CheckPinAndLogIn(card, "1234"));
        Assert.assertFalse(atm.CheckPinAndLogIn(card, null));
    }

    @Test
    public void accountStatus() {
        Assert.assertEquals(0.0, atm.AccountStatus(account), 0.0);
        account.DepositFunds(100.0);
        Assert.assertEquals(100.0, atm.AccountStatus(account), 0.0);
        account.WithdrawFunds(50);
        Assert.assertEquals(50.0, atm.AccountStatus(account), 0.0);
    }

    @Test
    public void changePinCard() {
        Assert.assertTrue(atm.ChangePinCard(card, "0000", "1111", "1111"));
        Assert.assertFalse(atm.ChangePinCard(card, "0000", "1111", "1111"));
        Assert.assertFalse(atm.ChangePinCard(card, "1111", "1111", "0000"));
    }

    @Test
    public void depositFunds() {
        Assert.assertFalse(atm.DepositFunds(card, 100));
        Assert.assertFalse(atm.DepositFunds(card, -100));
        card.AddAccount(account);
        Assert.assertEquals(0.0, atm.AccountStatus(account), 0.0);
        Assert.assertTrue(atm.DepositFunds(card, 100));
        Assert.assertEquals(100.0, atm.AccountStatus(account), 0.0);
        Assert.assertFalse(atm.DepositFunds(card, -50));
        Assert.assertEquals(100.0, atm.AccountStatus(account), 0.0);
    }

    @Test
    public void withdrawFunds() {
        Assert.assertFalse(atm.WithdrawFunds(card, 100));
        Assert.assertFalse(atm.WithdrawFunds(card, -100));
        card.AddAccount(account);
        Assert.assertEquals(0.0, atm.AccountStatus(account), 0.0);
        account.DepositFunds(1000);
        Assert.assertEquals(1000.0, atm.AccountStatus(account), 0.0);
        Assert.assertTrue(atm.WithdrawFunds(card, 200));
        Assert.assertEquals(800.0, atm.AccountStatus(account), 0.0);
        Assert.assertFalse(atm.WithdrawFunds(card, -300));
        Assert.assertEquals(800.0, atm.AccountStatus(account), 0.0);
    }

    @Test
    public void transfer() {
        account.DepositFunds(1000);
        Assert.assertEquals(1000.0, atm.AccountStatus(account), 0.0);
        Assert.assertEquals(0.0, atm.AccountStatus(account2), 0.0);

        Assert.assertFalse(atm.Transfer(card, account2, 100.0));
        Assert.assertEquals(1000.0, atm.AccountStatus(account), 0.0);
        Assert.assertEquals(0.0, atm.AccountStatus(account2), 0.0);
        card.AddAccount(account);

        Assert.assertFalse(atm.Transfer(card, account2, -100.0));
        Assert.assertEquals(1000.0, atm.AccountStatus(account), 0.0);
        Assert.assertEquals(0.0, atm.AccountStatus(account2), 0.0);

        Assert.assertFalse(atm.Transfer(card, null, 100.0));
        Assert.assertEquals(1000.0, atm.AccountStatus(account), 0.0);

        Assert.assertTrue(atm.Transfer(card, account2, 300.0));
        Assert.assertEquals(700.0, atm.AccountStatus(account), 0.0);
        Assert.assertEquals(300.0, atm.AccountStatus(account2), 0.0);
    }
}