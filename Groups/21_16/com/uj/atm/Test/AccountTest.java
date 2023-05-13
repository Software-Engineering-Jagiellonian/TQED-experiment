package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    /**
     * Test podstawowy, sprawdza czy nowo stworzone konto ma saldo równe 0. Innymi słowy czy na koncie nie znajdują
     * się jakiekolwiek pieniądze.
     * D - nowe konto
     */
    @Test
    public void test01() {
        IAccount account = new Account();
        double balance = account.AccountStatus();
        Assert.assertTrue(balance == 0);
    }

    /**
     * Test sprawdzający czy wpłata pieniędzy na konto powoduje zwrócenie poprawnej wartości nowego salda konta.
     * D + Z - konto i zmiana salda konta
     */
    @Test
    public void test02() {
        IAccount account = new Account();
        final double funds = 10540;
        double newBalance = account.DepositFunds(funds);
        Assert.assertTrue(funds == newBalance);
    }

    /**
     * Test sprawdzający czy wpłata pieniędzy na kotno powoduje zmianę wartości salda konta.
     * D + Z - konto i zmiana salda konta
     */
    @Test
    public void test03() {
        IAccount account = new Account();
        final double funds = 10540;
        account.DepositFunds(funds);
        double newBalance = account.AccountStatus();
        Assert.assertTrue(funds == newBalance);
    }

    /**
     * Test sprawdzający czy wpłacenie dużej kwoty powoduje niepoprawną pracę systemu.
     * D + Z + I - konto, wpłacenie pieniędzy, duża ilość pieniędzy
     */
    @Test
    public void test04() {
        IAccount account = new Account();
        final double hugeFunds = 424235434;
        account.DepositFunds(hugeFunds);
        double newBalance = account.AccountStatus();
        Assert.assertTrue(hugeFunds == newBalance);
    }

    /**
     * Test sprawdzający czy wielokrotne wpłacenie kwoty powoduje niepoprawną pracę systemu.
     * D + Z + C - konto, wpłacenie pieniędzy, wielokrotna akcja wpłaty
     */
    @Test
    public void test05() {
        IAccount account = new Account();

        final double funds1 = 10000;
        final double funds2 = 10000;
        final double funds3 = 10000;

        account.DepositFunds(funds1);
        double newBalance1 = account.AccountStatus();
        Assert.assertTrue(funds1 == newBalance1);

        account.DepositFunds(funds2);
        double newBalance2 = account.AccountStatus();
        Assert.assertTrue(funds1 + funds2 == newBalance2);

        account.DepositFunds(funds3);
        double newBalance3 = account.AccountStatus();
        Assert.assertTrue(funds1 + funds2 + funds3 == newBalance3);
    }

    /**
     * Test sprawdzający czy wielokrotne wpłacenie dużej kwoty powoduje niepoprawną pracę systemu.
     * D + Z + I + C
     */
    @Test
    public void test06() {
        IAccount account = new Account();
        final double hugeFunds = 1242354341;

        account.DepositFunds(hugeFunds);
        double newBalance1 = account.AccountStatus();
        Assert.assertTrue(hugeFunds == newBalance1);

        account.DepositFunds(hugeFunds);
        double newBalance2 = account.AccountStatus();
        Assert.assertTrue(hugeFunds + hugeFunds == newBalance2);

        account.DepositFunds(hugeFunds);
        double newBalance3 = account.AccountStatus();
        Assert.assertTrue(hugeFunds + hugeFunds + hugeFunds == newBalance3);
    }

    /**
     * Test sprawdzający czy dwa konta są od siebie niezależne podczas wpłaty pieniędzy
     * D + D + Z + Z
     */
    @Test
    public void test07() {
        IAccount account1 = new Account();
        IAccount account2 = new Account();

        final double funds1 = 10000;
        final double funds2 = 20000;

        account1.DepositFunds(funds1);
        double newBalance1 = account1.AccountStatus();
        Assert.assertTrue(funds1 == newBalance1);

        account2.DepositFunds(funds2);
        double newBalance2 = account2.AccountStatus();
        Assert.assertTrue(funds2 == newBalance2);
    }

    /**
     * Test sprawdzający czy dwa konta są od siebie niezależne podczas wypłaty pieniędzy
     * D + D + Z + Z
     */
    @Test
    public void test08() {
        IAccount account1 = new Account();
        IAccount account2 = new Account();

        final double funds1 = 10000;
        final double funds2 = 20000;

        account1.DepositFunds(funds1);
        double newBalance1 = account1.AccountStatus();
        Assert.assertTrue(funds1 == newBalance1);

        account2.DepositFunds(funds2);
        double newBalance2 = account2.AccountStatus();
        Assert.assertTrue(funds2 == newBalance2);

        account1.WithdrawFunds(funds1 / 10);
        double newBalance3 = account1.AccountStatus();
        Assert.assertTrue(funds1 - funds1 / 10 == newBalance3);

        account2.WithdrawFunds(funds2 / 10);
        double newBalance4 = account2.AccountStatus();
        Assert.assertTrue(funds2 - funds2 / 10 == newBalance4);
    }
}
