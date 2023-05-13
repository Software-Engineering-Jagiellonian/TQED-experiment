package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    //D+Z
    @Test
    public void accountStatus_shouldReturnZeroBalanceAfterCreation() {
        IAccount account = new Account();
        assertEquals(0, account.AccountStatus(), 0);
    }

    //D+Z
    @Test
    public void depositFunds_shouldReturnProperBalanceAfterDeposit() {
        IAccount account = new Account();
        assertEquals(12.23, account.DepositFunds(12.23), 0);
        assertEquals(12.23, account.AccountStatus(), 0);
    }

    //D+Z
    @Test
    public void withdrawFunds_shouldReturnProperBalanceAfterWithdrawal() {
        IAccount account = new Account();
        account.DepositFunds(12.23);
        assertEquals(12.01, account.WithdrawFunds(0.22), 0);
        assertEquals(12.01, account.AccountStatus(), 0);
    }

    //D+Z
    @Test
    public void withdrawFunds_shouldNotChangeBalanceAfterWithdrawalWithAmountGreaterThanBalance() {
        IAccount account = new Account();
        account.DepositFunds(12.23);
        assertEquals(12.23, account.WithdrawFunds(100), 0);
        assertEquals(12.23, account.AccountStatus(), 0);
    }

    //D+Z+I
    @Test
    public void withdrawFunds_shouldNotChangeBalanceAfterWithdrawalWithZeroAmount() {
        IAccount account = new Account();
        account.DepositFunds(12.23);
        assertEquals(12.23, account.WithdrawFunds(0), 0);
        assertEquals(12.23, account.AccountStatus(), 0);
    }

    //D+Z+I
    @Test
    public void withdrawFunds_shouldNotChangeBalanceAfterWithdrawalWithNegativeAmount() {
        IAccount account = new Account();
        account.DepositFunds(12.23);
        assertEquals(12.23, account.WithdrawFunds(-10), 0);
        assertEquals(12.23, account.AccountStatus(), 0);
    }

    //D+Z
    @Test
    public void withdrawFunds_shouldChangeBalanceToZeroAfterWithdrawalWithAmountEqualBalance() {
        IAccount account = new Account();
        account.DepositFunds(12.23);
        assertEquals(0, account.WithdrawFunds(12.23), 0);
        assertEquals(0, account.AccountStatus(), 0);
    }
}