package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    /**
     * D - basic verification data
     * all business logic is handled elsewhere, so there is nothing else to test here */
    @Test
    public void TestAccountHappyPath() {
        Account account = new Account();
        account.DepositFunds(200);
        assertEquals(account.AccountStatus(), 200, 0.001);
        account.WithdrawFunds(100);
        assertEquals(account.AccountStatus(), 100, 0.001);
    }
}