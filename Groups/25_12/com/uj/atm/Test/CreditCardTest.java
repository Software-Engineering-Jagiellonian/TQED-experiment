package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

  @Test
  public void InitSuccessTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertTrue(creditCard.IsPinValid("1234"));
  }

  @Test
  public void InitNotMatchingPinsTest() {
    ICreditCard creditCard = new CreditCard();
    Assert.assertThrows(IllegalArgumentException.class, () -> creditCard.Init("1234", "1237"));
    assertFalse(creditCard.IsPinValid("1234"));
  }

  @Test
  public void InitNullTest() {
    ICreditCard creditCard = new CreditCard();
    Assert.assertThrows(IllegalArgumentException.class, () -> creditCard.Init(null, null));
  }

  @Test
  public void InitTooLongPinTest() {
    ICreditCard creditCard = new CreditCard();
    Assert.assertThrows(IllegalArgumentException.class, () -> creditCard.Init("12345", "12345"));
    assertFalse(creditCard.IsPinValid("12345"));
  }

  @Test
  public void InitTooShortPinTest() {
    ICreditCard creditCard = new CreditCard();
    Assert.assertThrows(IllegalArgumentException.class, () -> creditCard.Init("123", "123"));
    assertFalse(creditCard.IsPinValid("123"));
  }

  @Test
  public void InitPinWithLetters() {
    ICreditCard creditCard = new CreditCard();
    Assert.assertThrows(IllegalArgumentException.class, () -> creditCard.Init("123A", "123A"));
    assertFalse(creditCard.IsPinValid("123A"));
  }

  @Test
  public void InitPinWithSpecialCharacters() {
    ICreditCard creditCard = new CreditCard();
    Assert.assertThrows(IllegalArgumentException.class, () -> creditCard.Init("123!", "123!"));
    assertFalse(creditCard.IsPinValid("123!"));
  }

  @Test
  public void MultipleInitsTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    Assert.assertThrows(IllegalStateException.class, () -> creditCard.Init("5678", "5678"));
  }

  @Test
  public void ChangePinSuccessTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertTrue(creditCard.ChangePin("0000", "1234", "1234"));
    assertTrue(creditCard.IsPinValid("1234"));
  }

  @Test
  public void ChangePinNullTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.ChangePin("0000", null, null));
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void ChangePinTooShortTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.ChangePin("0000", "123", "123"));
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void ChangePinTooLongTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.ChangePin("0000", "12345", "12345"));
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void ChangePinWithLetters() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.ChangePin("0000", "123b", "1234b"));
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void ChangePinWithSpecialCharacters() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.ChangePin("0000", "123,", "123,"));
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void ChangePinWithNoInit() {
    ICreditCard creditCard = new CreditCard();
    assertFalse(creditCard.ChangePin("0000", "123,", "123,"));
  }

  @Test
  public void ChangePinIncorrectOldTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.ChangePin("0001", "1234", "1234"));
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void ChangePinConfirmNotMatchTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.ChangePin("0001", "1234", "2234"));
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void IsPinValidInitialTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertTrue(creditCard.IsPinValid("0000"));
  }

  @Test
  public void IsPinValidNoInitialTest() {
    ICreditCard creditCard = new CreditCard();
    assertFalse(creditCard.IsPinValid("0000"));
  }

  @Test
  public void IsPinValidNullTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.IsPinValid(null));
  }

  @Test
  public void IsPinValidInvalidTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(creditCard.IsPinValid("0000"));
  }

  @Test
  public void AddAccountTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    assertEquals(creditCard.GetAccount(), account);
  }

  @Test
  public void AddAccountNoInitializedTest() {
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    Assert.assertThrows(IllegalStateException.class, () -> creditCard.AddAccount(account));
  }

  @Test
  public void AddAccountNullTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    Assert.assertThrows(IllegalArgumentException.class, () -> creditCard.AddAccount(null));
  }

  @Test
  public void AddMultipleAccountsTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account1 = new Account();
    IAccount account2 = new Account();
    creditCard.AddAccount(account1);
    Assert.assertThrows(IllegalStateException.class, () -> creditCard.AddAccount(account2));
  }

  @Test
  public void GetAccountTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    assertEquals(account, creditCard.GetAccount());
  }

  @Test
  public void DepositFundsSuccessTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    assertTrue(creditCard.DepositFunds(20.0));
    assertEquals(account.AccountStatus(), 20.0, 0.00);
  }

  @Test
  public void DepositFundsNoAccountTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.DepositFunds(20.0));
  }

  @Test
  public void DepositFundsIncorrectPrecisionTest() {
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    creditCard.DepositFunds(20.0);
    assertFalse(creditCard.DepositFunds(10.001));
  }


  @Test
  public void DepositFundNegativeNumberAccountTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    assertFalse(creditCard.DepositFunds(-20.0));
  }


  @Test
  public void WithdrawFundsSuccessTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    creditCard.DepositFunds(20.0);
    assertTrue(creditCard.WithdrawFunds(10.0));
  }


  @Test
  public void WithdrawFundsNotEnoughMoneyTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    creditCard.DepositFunds(20.0);
    assertFalse(creditCard.WithdrawFunds(50.0));
  }

  @Test
  public void WithdrawFundsNegativeValueTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    creditCard.DepositFunds(20.0);
    assertFalse(creditCard.WithdrawFunds(-10.0));
  }

  @Test
  public void WithdrawFundsIncorrectPrecisionTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    creditCard.DepositFunds(20.0);
    assertFalse(creditCard.WithdrawFunds(10.001));
  }


  @Test
  public void WithdrawFundsInitialTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    assertFalse(creditCard.WithdrawFunds(1.0));
  }

  @Test
  public void WithdrawFundsNoAccountTest() {
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertFalse(creditCard.WithdrawFunds(10.0));
  }
}