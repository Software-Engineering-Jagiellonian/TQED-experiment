package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AccountTest {

    private IAccount account;

    @Before
    public void beforeEach() {
        account = new Account();
    }

    @Test
    public void shouldReturnCorrectAccountStatus() {
        account.DepositFunds(120);
        double status = account.AccountStatus();
        assertThat(status, is(120d));
        account.DepositFunds(-20);
        status = account.AccountStatus();
        assertThat(status, is(120d));
    }

    @Test
    public void shouldAddFundsToAccount() {
        double addedFunds = account.DepositFunds(120);
        double status = account.AccountStatus();
        assertThat(addedFunds, is(status));
    }

    @Test
    public void shouldWithDrawFunds() {
        account.DepositFunds(120.40);
        Double withDrawnFunds = Math.round(account.WithdrawFunds(120) * 100.0) / 100.0;
        assertThat(withDrawnFunds, is(0.4));
    }

    @Test
    public void shouldReturnZeroWhenAmountBiggerThanStatus() {
        account.DepositFunds(120);
        double result = account.WithdrawFunds(150);
        assertThat(result, is(0d));
    }
}