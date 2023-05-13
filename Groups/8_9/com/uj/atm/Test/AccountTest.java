package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void shouldReturnBalanceWithdrawAfterCreate() { // Z
        IAccount account = new Account();
        double balance = account.WithdrawFunds(50.1);
        assertEquals(balance, 0, 0.001);
    }

    @Test
    public void shouldReturnZeroBalanceAfterCreate() {  //Z
        IAccount account = new Account();
        double balance = account.AccountStatus();
        assertEquals(balance, 0.0, 0.001);
    }

    @Test
    public void shouldReturnBalanceAfterDepositAndWithdraw() { //Z + Z + D
        IAccount account = new Account();
        account.DepositFunds(250.0);
        double balance = account.WithdrawFunds(149.1);
        assertEquals(balance, 100.9, 0.001);
    }

    @Test
    public void shouldReturnBalanceAfterAnotherDeposit() { // Z + Z + D
        IAccount account = new Account();
        account.DepositFunds(50.0);
        double balance = account.DepositFunds(231.1);
        assertEquals(balance, 281.1, 0.001);
    }

    @Test
    public void shouldReturnBalanceAfterWithdrawMoreMoneyThanYouHave() { //Z + D
        IAccount account = new Account();
        account.DepositFunds(10.0);
        double balance = account.WithdrawFunds(10.1);
        assertEquals(balance, 10, 0.001);
    }

    @Test
    public void shouldDepositMoneyAfterCreate() {  //Z
        IAccount account = new Account();
        double balance = account.DepositFunds(150.0);
        assertEquals(balance, 150.0, 0.001);
    }
}