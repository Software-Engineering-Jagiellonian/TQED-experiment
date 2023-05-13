package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	

    @Test
    public void shouldReturnZeroBalanceAfterCreate() {  //Z

        //given
        IAccount account = new Account();

        //when
        double balance = account.AccountStatus();

        //then
        assertEquals(balance, 0.0, 0.001);
    }

    @Test
    public void shouldDepositMoneyAfterCreate() {  //Z

        //given
        IAccount account = new Account();

        //when
        double balance = account.DepositFunds(50.0);

        //then
        assertEquals(balance, 50.0, 0.001);
    }

    @Test
    public void shouldReturnBalanceAfterAnotherDeposit() { // Z + Z + D

        //given
        IAccount account = new Account();

        //when
        account.DepositFunds(50.0);
        double balance = account.DepositFunds(51.1);

        //then
        assertEquals(balance, 101.1, 0.001);
    }

    @Test
    public void shouldReturnBalanceAfterDepositAndWithdraw() { //Z + Z + D

        //given
        IAccount account = new Account();

        //when
        account.DepositFunds(50.0);
        double balance = account.WithdrawFunds(49.1);

        //then
        assertEquals(balance, 0.9, 0.001);
    }

    @Test
    public void shouldReturnBalanceAfterWithdrawMoreMoneyThanYouHave() { //Z + D

        //given
        IAccount account = new Account();

        //when
        account.DepositFunds(50.0);
        double balance = account.WithdrawFunds(50.1);

        //then
        assertEquals(balance, 50, 0.001);
    }

    @Test
    public void shouldReturnBalanceWithdrawAfterCreate() { // Z+D

        //given
        IAccount account = new Account();

        //when
        double balance = account.WithdrawFunds(50.1);

        //then
        assertEquals(balance, 0, 0.001);
    }

}