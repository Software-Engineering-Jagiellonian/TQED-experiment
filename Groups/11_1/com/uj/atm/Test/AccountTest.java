package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

    private IAccount account;

    @Before
    public void setup(){
        this.account = new Account();
    }

    @Test
    public void accountStatus() {
        // probably redundant
        Assert.assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    @Test
    public void depositFunds() {
        Assert.assertEquals(0.0, account.AccountStatus(), 0.0);
        Assert.assertEquals(50.0, account.DepositFunds(50.0), 0.0);
        Assert.assertEquals(150.0, account.DepositFunds(100.0), 0.0);

        Assert.assertThrows(Throwable.class, ()-> account.DepositFunds(0.0));

        Assert.assertThrows(Throwable.class, ()-> account.DepositFunds(-200.0));
    }

    @Test
    public void withdrawFunds() {
        Assert.assertEquals(0.0, account.AccountStatus(), 0.0);
        account.DepositFunds(1000.0);
        Assert.assertEquals(1000.0, account.AccountStatus(), 0.0);

        account.WithdrawFunds(100);
        Assert.assertEquals(900.0, account.AccountStatus(), 0.0);

        Assert.assertThrows(Throwable.class, ()-> account.WithdrawFunds(1000.0));

        Assert.assertThrows(Throwable.class, ()-> account.WithdrawFunds(-100.0));
    }
}