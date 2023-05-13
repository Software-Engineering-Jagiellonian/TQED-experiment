package com.uj.atm.common;
import com.uj.atm.interfaces.IAccount;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {
    private IAccount account;

    @Before
    public void setUp() {
        account = new Account();
    }

    @Test
    public void test1() {
        assertEquals("AccountStatus test", 0.0, account.AccountStatus(), 0.0);
        assertEquals("DepositFunds test", 10.0, account.DepositFunds(10.), 0.0);
        assertEquals("DepositFunds test", 20.0, account.DepositFunds(10.), 0.0);
        assertEquals("WithdrawFunds test", 15.0, account.WithdrawFunds(5.), 0.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIIllegalArgumentException() {
        assertEquals("DepositFunds test - incorrect input", 0, account.DepositFunds(-10.), 0.0);
        assertEquals("WithdrawFunds test - incorrect input", 0, account.WithdrawFunds(-10.), 0.0);
    }


}
