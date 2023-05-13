package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

  @Test
  public void AccountStatusTest(){
    IAccount account = new Account();
    account.DepositFunds(30.0);
    assertEquals(30.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void AccountStatusEmptyAccountTest(){
    IAccount account = new Account();
    assertEquals(0.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void WithdrawFundsTest(){
    IAccount account = new Account();
    account.DepositFunds(30.0);
    assertEquals(20.0, account.WithdrawFunds(10), 0.0);
    assertEquals(20.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void WithdrawFundsTooHighAmountTest(){
    IAccount account = new Account();
    account.DepositFunds(30.0);
    Assert.assertThrows(IllegalArgumentException.class, () -> account.WithdrawFunds(40));
    assertEquals(30.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void WithdrawFundsNegativeAmountTest(){
    IAccount account = new Account();
    Assert.assertThrows(IllegalArgumentException.class, () -> account.DepositFunds(-30.0));
    assertEquals(0.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void WithdrawFundZeroAmountTest(){
    IAccount account = new Account();
    account.DepositFunds(20.0);
    Assert.assertThrows(IllegalArgumentException.class, () -> account.DepositFunds(0.0));
    assertEquals(20.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void WithdrawFundsAmountIncorrectPrecisionTest(){
    IAccount account = new Account();
    Assert.assertThrows(IllegalArgumentException.class, () -> account.DepositFunds(30.001));
    assertEquals(0.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void DepositFundsNegativeNumberTest(){
    IAccount account = new Account();
    Assert.assertThrows(IllegalArgumentException.class, () -> account.WithdrawFunds(-40.0));
    assertEquals(0.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void DepositFundsZeroAmountTest(){
    IAccount account = new Account();
    Assert.assertThrows(IllegalArgumentException.class, () -> account.WithdrawFunds(0.0));
    assertEquals(0.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void DepositFundsTest(){
    IAccount account = new Account();
    assertEquals(50.0, account.DepositFunds(50.0), 0.0);
    assertEquals(50.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void DepositFundsMultipleCallsTest(){
    IAccount account = new Account();
    account.DepositFunds(30.0);
    account.DepositFunds(70.0);
    assertEquals(100.0, account.AccountStatus(), 0.0);
  }

  @Test
  public void DepositFundsIncorrectPrecisionTest(){
    IAccount account = new Account();
    Assert.assertThrows(IllegalArgumentException.class, () -> account.WithdrawFunds(40.001));
    assertEquals(0.0, account.AccountStatus(), 0.0);
  }
}