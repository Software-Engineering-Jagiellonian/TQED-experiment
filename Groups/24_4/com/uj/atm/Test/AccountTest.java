package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	private IAccount iAccount;

    @Before
    public void init() {
        this.iAccount = new Account();
    }

    /**
     * Test sprawdzajacy konto po utworzeniu
     * Wymiar: D
     */

    @Test
    public void test01() {
        assertEquals(iAccount.AccountStatus(),0.0,0.0);
    }

    /**
     * Test sprawdzajcy pojedyncze dodanie pieniedzy do konta
     * Wymiar: Z
     */
    @Test
    public void test02() {
        iAccount.DepositFunds(150.504334);
        assertEquals(iAccount.AccountStatus(),150.504334,0.0);
    }

    /**
     * Test sprawdzajcy pojedyncze dodanie pieniedzy do konta i pojedyncze wyplacenie pieniedzy z konta
     * Wymiar: Z + Z
     */
    @Test
    public void test03() {
        iAccount.DepositFunds(150.504334);
        iAccount.WithdrawFunds(76.435323);
        assertEquals(iAccount.AccountStatus(),74.069011,0.0);
    }

    /**
     * Test sprawdzajcy podwojna wplate pieniedzy do konta
     * Wymiar: Z + Z
     */
    @Test
    public void test04() {
        iAccount.DepositFunds(150.504334);
        iAccount.DepositFunds(76.435323);
        assertEquals(iAccount.AccountStatus(),226.939657,0.0);
    }

    /**
     * Test sprawdzajcy dodanie pieniedzy do konta i podwojne wyplacenie pieniedzy
     * Wymiar: Z + Z
     */
    @Test
    public void test05() {
        iAccount.DepositFunds(150.504334);
        iAccount.WithdrawFunds(70.103);
        iAccount.WithdrawFunds(70.2);
        assertEquals(iAccount.AccountStatus(),10.201334,0.0);
    }

    /**
     * Test sprawdzajcy dodanie pieniedzy do konta i wyplacenie pieniedzy z nieznaczaca reszta
     * Wymiar: Z + Z + I
     */
    @Test
    public void test06() {
        iAccount.DepositFunds(150.504334);
        iAccount.WithdrawFunds(40.000000000000001);
        assertEquals(iAccount.AccountStatus(),110.504334,0.0);
    }

    /**
     * Test sprawdzajcy wplate wartosci przekraczajacej maksymalna wartosc double
     * Wymiar: Z + I
     */

    @Test
    public void test07() {
        iAccount.DepositFunds(Double.MAX_VALUE);
        iAccount.DepositFunds(1000000.43534);
        assertEquals(iAccount.AccountStatus(),Double.MAX_VALUE,0.0);
    }

    /**
     * Test sprawdzajcy wplate dwoch skrajnych wartosci double
     * Wymiar: Z + I + I
     */
    @Test
    public void test08() {
        iAccount.DepositFunds(Double.MIN_VALUE);
        iAccount.DepositFunds(Double.MAX_VALUE);
        assertEquals(iAccount.AccountStatus(),Double.MAX_VALUE,0.0);
    }

    /**
     * Test sprawdzajcy wplate i wyplate na dwoch malych wartosciach
     * Wymiar: Z + Z
     */
    @Test
    public void test09() {
        iAccount.DepositFunds(0.03);
        iAccount.WithdrawFunds(0.02);
        assertEquals(iAccount.AccountStatus(),0.01,0.0);
    }

    /**
     * Test sprawdzajcy wplate i wyplate tej samej kwoty
     * Wymiar: Z + Z
     */
    @Test
    public void test10() {
        iAccount.DepositFunds(3.1464532576854);
        iAccount.WithdrawFunds(3.1464532576854);
        assertEquals(iAccount.AccountStatus(),0.00,0.0);
    }

    /**
     * Test sprawdzajcy wyplate kwoty wiekszej niz jest dostepna na koncie
     * Wymiar: Z
     */
    @Test(expected = ArithmeticException.class)
    public void test11() {
        iAccount.DepositFunds(150.0);
        iAccount.WithdrawFunds(200.0);
    }
}