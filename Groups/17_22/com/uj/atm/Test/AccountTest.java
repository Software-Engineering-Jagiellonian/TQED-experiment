package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void DebitIncreaseAfterDeposit() {
        Account account = new Account();
        double debit = account.DepositFunds(150);
        assertEquals(150.0, debit, 0);
    }

    @Test
    public void DebitIsZeroAfterInitialize() {
        Account account = new Account();
        assertEquals(0, account.AccountStatus(), 0);
    }

    @Test
    public void DebitDecreaseAfterWithdraw() {
        Account account = new Account();
        account.DepositFunds(150);
        double debit = account.WithdrawFunds(50);
        assertEquals(100.0, debit, 0);
    }

    @Test
    public void DebitDoesNotDecreaseBecauseAmountIsGreaterThanAccountStatus() {
        Account account = new Account();
        account.DepositFunds(50);
        double debit = account.WithdrawFunds(100);
        assertEquals(50, debit, 0);
    }

}