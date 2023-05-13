package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	
	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    @Test
    public void accountStatusInitTest(){
        IAccount account = new Account();
        double balance = 0;
        Assert.assertEquals("Init account balance should be equal 0", balance, account.AccountStatus(), 0);
    }

    @Test
    public void depositPositiveTest(){
        IAccount account = new Account();
        double amount = 100.32;
        double balance = 100.32;
        Assert.assertEquals("Amount not added to balance correctly", balance, account.DepositFunds(amount),0);
    }

    @Test
    public void depositNegativeTest(){
        IAccount account = new Account();
        double amount = -100;
        double balance = 0;
        Assert.assertEquals("DepositFunds method shouldn't accept negative amount", balance, account.DepositFunds(amount),0);
    }

    @Test
    public void depositMoreThanWithdrawTest(){
        IAccount account = new Account();
        account.DepositFunds(200.50);
        double amount = 50.25;
        double balance = 150.25;
        Assert.assertEquals("Amount not reduced to balance correctly", balance, account.WithdrawFunds(amount),0);
    }

    @Test
    public void depositThenWithdrawNegativeValueTest(){
        IAccount account = new Account();
        account.DepositFunds(200);
        double amount = -50;
        double balance = 200;
        Assert.assertEquals("Withdraw method shouldn't accept negative amount", balance, account.WithdrawFunds(amount),0);
    }

    @Test
    public void withdrawMoreThanDepositTest(){
        IAccount account = new Account();
        account.DepositFunds(100);
        double amount = 100.01;
        double balance = 100;
        Assert.assertEquals("Withdraw method shouldn't accept amount greater than account balance", balance, account.WithdrawFunds(amount),0);
    }

    @Test
    public void accountStatusAfterDepositAndWithdraw(){
        IAccount account = new Account();
        account.DepositFunds(359.05);
        account.WithdrawFunds(18.01);
        double balance = 341.04;
        Assert.assertEquals(balance, account.AccountStatus(), 0);
    }
}