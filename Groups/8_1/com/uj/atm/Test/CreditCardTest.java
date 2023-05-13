package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreditCardTest {

    private ICreditCard card;
    private IAccount account;

    @Before
    public void setup(){
        card = new CreditCard();
        account = new Account();
    }

    @Test
    public void init() {
        Assert.assertThrows(Throwable.class, ()-> card.Init(null, "0000"));
        Assert.assertThrows(Throwable.class, ()-> card.Init("0000", null));
        Assert.assertThrows(Throwable.class, ()-> card.Init(null, null));
        Assert.assertThrows(Throwable.class, ()-> card.Init("0000", "1111"));
        Assert.assertThrows(Throwable.class, ()-> card.Init("000", "000"));
        Assert.assertThrows(Throwable.class, ()-> card.Init("000a", "000a"));
        Assert.assertTrue(card.IsPinValid("0000"));
        card.Init("1111", "1111");
        Assert.assertTrue(card.IsPinValid("1111"));

        // probably this test should be; however, nothing tells us that card can not be inited 2 times
        // Assert.assertThrows(Throwable.class, ()-> card.Init("2222", "2222"));
    }

    @Test
    public void changePin() {
        Assert.assertFalse(this.card.ChangePin(null, null, null));
        Assert.assertFalse(this.card.ChangePin(null, "0000", "0000"));
        Assert.assertFalse(this.card.ChangePin(null, "1111", "0000"));
        Assert.assertFalse(this.card.ChangePin("0000", "1", "0"));

        card.Init("1111", "1111");

        Assert.assertFalse(this.card.ChangePin(null, null, null));
        Assert.assertFalse(this.card.ChangePin(null, "2222", "2222"));
        Assert.assertFalse(this.card.ChangePin(null, "1111", "2222"));
        Assert.assertFalse(this.card.ChangePin("0000", "2222", "2222"));
        Assert.assertFalse(this.card.ChangePin("1111", "1111", "0000"));

        Assert.assertTrue(this.card.ChangePin("1111", "2222", "2222"));
        Assert.assertFalse(this.card.ChangePin("1111", "0000", "0000"));

        Assert.assertTrue(this.card.ChangePin("2222", "1111", "1111"));

        Assert.assertTrue(card.IsPinValid("1111"));
}

    @Test
    public void isPinValid() {
        Assert.assertFalse(card.IsPinValid("0"));
        Assert.assertFalse(card.IsPinValid(null));
        Assert.assertTrue(card.IsPinValid("0000"));

        card.Init("1111", "1111");

        Assert.assertTrue(card.IsPinValid("1111"));
        Assert.assertFalse(card.IsPinValid("0000"));

        Assert.assertFalse(card.IsPinValid(null));
    }

    @Test
    public void Account() {
        Assert.assertThrows(Throwable.class, ()-> this.card.GetAccount());
        this.card.AddAccount(this.account);
        Assert.assertThrows(Throwable.class, ()-> this.card.AddAccount(this.account));
        Assert.assertEquals(this.account, this.card.GetAccount());
    }

    @Test
    public void depositFunds() {
        Assert.assertFalse( this.card.DepositFunds(-10));
        Assert.assertFalse( this.card.DepositFunds(0));
        Assert.assertFalse( this.card.DepositFunds(10));

        this.card.AddAccount(this.account);

        Assert.assertFalse( this.card.DepositFunds(-10));
        Assert.assertFalse( this.card.DepositFunds(0.0));

        Assert.assertTrue( this.card.DepositFunds(100.0));
        Assert.assertTrue( this.card.DepositFunds(50.0));

        Assert.assertEquals(150.0, this.card.GetAccount().AccountStatus(), 0.0);
    }

    @Test
    public void withdrawFunds() {
        Assert.assertFalse( this.card.WithdrawFunds(-100));
        Assert.assertFalse( this.card.WithdrawFunds(0));
        Assert.assertFalse( this.card.WithdrawFunds(100));

        this.card.AddAccount(this.account);

        Assert.assertFalse( this.card.WithdrawFunds(-100));
        Assert.assertFalse( this.card.WithdrawFunds(0));
        Assert.assertFalse( this.card.WithdrawFunds(100));

        this.card.DepositFunds(1000);

        Assert.assertFalse( this.card.WithdrawFunds(-100));
        Assert.assertFalse( this.card.WithdrawFunds(0));

        Assert.assertTrue( this.card.WithdrawFunds(150.0));

        Assert.assertFalse( this.card.WithdrawFunds(1000));

        Assert.assertEquals(850.0, this.card.GetAccount().AccountStatus(), 0.0);
    }
}