package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	
	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    @Test // D+I
    public void shouldReturnZeroBalance_whenAccountStatus_givenObjectIsCreated() {
        // given
        IAccount account = new Account();

        // when
        double balance = account.AccountStatus();

        // then
        assertEquals(0, balance, 0);
    }

    // DepositFunds

    @Test // D+Z
    public void shouldReturnBalance_whenDepositFunds_givenFundsAreDeposited() {
        // given
        IAccount account = new Account();
        double amount = 10;

        // when
        double balance = account.DepositFunds(amount);

        // then
        assertEquals(amount, balance, 0);
    }

    @Test(expected = IllegalArgumentException.class) // Z+I
    public void shouldThrowException_whenDepositFunds_givenAmountIsNegative() {
        // given
        IAccount account = new Account();
        double amount = -10;

        // when
        double balance = account.DepositFunds(amount);

        // then
    }

    // WithdrawFunds

    @Test // Z+Z
    public void shouldReturnBalance_whenWithdrawFunds_givenFundsCanBeWithdrawn() {
        // given
        IAccount account = new Account();
        double depositAmount = 10;
        double withdrawAmount = 2;
        account.DepositFunds(depositAmount);

        // when
        double balance = account.WithdrawFunds(withdrawAmount);

        // then
        assertEquals(depositAmount - withdrawAmount, balance, 0);
    }

    @Test(expected = IllegalArgumentException.class) // Z+I
    public void shouldThrowException_whenWithdrawFunds_givenAmountToWithdrawIsBiggerThanBalance() {
        // given
        IAccount account = new Account();
        double depositAmount = 10;
        double withdrawAmount = 2000;
        account.DepositFunds(depositAmount);

        // when
        double balance = account.WithdrawFunds(withdrawAmount);

        // then
    }

    @Test(expected = IllegalArgumentException.class) // Z+I
    public void shouldThrowException_whenWithdrawFunds_givenAmountToWithdrawIsNegative() {
        // given
        IAccount account = new Account();
        double depositAmount = 10;
        double withdrawAmount = -2000;
        account.DepositFunds(depositAmount);

        // when
        double balance = account.WithdrawFunds(withdrawAmount);

        // then
    }
}