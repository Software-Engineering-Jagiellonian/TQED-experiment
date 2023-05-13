package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
  @Test
  public void CheckPinAndLogInSuccessTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertTrue(atm.CheckPinAndLogIn(creditCard, "1234"));
  }

  @Test
  public void CheckPinAndLogInNullCardTest(){
    IAtm atm = new Atm();
    assertFalse(atm.CheckPinAndLogIn(null, "1234"));
  }

  @Test
  public void CheckPinAndLogInNullPinTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.CheckPinAndLogIn(creditCard, null));
    assertTrue(creditCard.IsPinValid("1234"));
  }

  @Test
  public void CheckPinAndLogInIncorrectPinTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.CheckPinAndLogIn(creditCard, "7890"));
    assertTrue(creditCard.IsPinValid("1234"));
  }


  @Test
  public void AccountStatusTest(){
    IAtm atm = new Atm();
    IAccount account = new Account();
    account.DepositFunds(30.0);
    assertEquals(30.0, atm.AccountStatus(account), 0.0);
  }

  @Test
  public void AccountStatusNullTest(){
    IAtm atm = new Atm();
    Assert.assertThrows(IllegalArgumentException.class, () -> atm.AccountStatus(null));
  }

  @Test
  public void AccountStatusInitialTest(){
    IAtm atm = new Atm();
    IAccount account = new Account();
    assertEquals(0.0, atm.AccountStatus(account), 0.0);
  }

  @Test
  public void ChangePinCardSuccessTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertTrue(atm.ChangePinCard(creditCard, "1234", "7890", "7890"));
  }

  @Test
  public void ChangePinCardNullTest(){
    IAtm atm = new Atm();
    assertFalse(atm.ChangePinCard(null, "0000", "7890", "7890"));
  }

  @Test
  public void ChangePinCardWithoutInitialSuccessTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    assertTrue(atm.ChangePinCard(creditCard, "0000", "7890", "7890"));
  }

  @Test
  public void ChangePinIncorrectOldTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.ChangePinCard(creditCard, "3234", "7890", "7890"));
  }

  @Test
  public void ChangePinConfirmNotMatchTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.ChangePinCard(creditCard, "1234", "7890", "7899"));
  }

  @Test
  public void ChangePinTooLongTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.ChangePinCard(creditCard, "1234", "78901", "78901"));
  }

  @Test
  public void ChangePinTooShortTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.ChangePinCard(creditCard, "1234", "789", "789"));
  }

  @Test
  public void ChangePinWithLettersTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.ChangePinCard(creditCard, "1234", "D890", "D890"));
  }

  @Test
  public void ChangePinWithSpecialCharactersTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("1234", "1234");
    assertFalse(atm.ChangePinCard(creditCard, "1234", "!890", "!890"));
  }

  @Test
  public void DepositFundsSuccessTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    creditCard.Init("0000", "0000");
    IAccount account = new Account();
    creditCard.AddAccount(account);
    assertTrue(atm.DepositFunds(creditCard, 20.0));
    assertEquals(atm.AccountStatus(account), 20.0, 0.0);
    assertTrue(atm.DepositFunds(creditCard, 30.0));
    assertEquals(atm.AccountStatus(account), 50.0, 0.0);
  }

  @Test
  public void DepositFundsNullTest(){
    IAtm atm = new Atm();
    assertFalse(atm.DepositFunds(null, 20.0));
  }

  @Test
  public void DepositFundsCardWithNoAccountTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    assertFalse(atm.DepositFunds(creditCard, 20.0));
  }

  @Test
  public void DepositFundsNegativeValueTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    assertFalse(atm.DepositFunds(creditCard, -20.0));
  }

  @Test
  public void DepositFundsIncorrectPrecisionValueTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    assertFalse(atm.DepositFunds(creditCard, 20.003));
    assertEquals(atm.AccountStatus(account), 0.00, 0.00);
  }


  @Test
  public void WithdrawFundsSuccessTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 20.0);
    assertTrue(atm.WithdrawFunds(creditCard, 10.0));
    assertEquals(atm.AccountStatus(account), 10.00, 0.00);


  }

  @Test
  public void WithdrawFundsNullTest(){
    IAtm atm = new Atm();
    assertFalse(atm.WithdrawFunds(null, 10.0));
  }


  @Test
  public void WithdrawFundsCardWithNoAccountTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    assertFalse(atm.WithdrawFunds(creditCard, 10.0));
  }

  @Test
  public void WithdrawFundsNotEnoughMoneyTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 20.0);
    assertFalse(atm.WithdrawFunds(creditCard, 30.0));
    assertEquals(atm.AccountStatus(account), 20.00,0.00);
  }

  @Test
  public void WithdrawFundsAllMoneyTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 20.0);
    assertTrue(atm.WithdrawFunds(creditCard, 20.0));
    assertEquals(atm.AccountStatus(account), 0.00,0.00);
  }

  @Test
  public void WithdrawFundsMultipleTimesFailTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 100.0);
    assertTrue(atm.WithdrawFunds(creditCard, 30.0));
    assertFalse(atm.WithdrawFunds(creditCard, 80.0));
    assertEquals(atm.AccountStatus(account), 70.00,0.00);
  }

  @Test
  public void WithdrawFundsMultipleTimesTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 100.0);
    assertTrue(atm.WithdrawFunds(creditCard, 30.0));
    assertTrue(atm.WithdrawFunds(creditCard, 40.0));
    assertEquals(atm.AccountStatus(account), 30.00,0.00);
  }


  @Test
  public void WithdrawFundsNegativeValueTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 100.0);
    assertFalse(atm.WithdrawFunds(creditCard, -20.0));
  }

  @Test
  public void WithdrawFundsIncorrectPrecisionValueTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 100.0);
    assertFalse(atm.WithdrawFunds(creditCard, 40.999));
    assertEquals(atm.AccountStatus(account), 100.00, 0.00);
  }

  @Test
  public void WithdrawFundsNoDepositTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    assertFalse(atm.WithdrawFunds(creditCard, 10.00));
    assertEquals(atm.AccountStatus(account), 0.00, 0.00);
  }


  @Test
  public void TransferSuccessTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    IAccount accountRecipient = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 50.00);
    assertTrue(atm.Transfer(creditCard, accountRecipient, 30.00));
    assertEquals(atm.AccountStatus(accountRecipient), 30.00, 0.00);
    assertEquals(atm.AccountStatus(account), 20.00, 0.00);
  }

  @Test
  public void TransferNullCardTest(){
    IAtm atm = new Atm();
    IAccount accountRecipient = new Account();
    assertFalse(atm.Transfer(null, accountRecipient, 30.00));
    assertEquals(atm.AccountStatus(accountRecipient), 0.00, 0.00);
  }

  @Test
  public void TransferNullRecipientTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 50.00);
    assertFalse(atm.Transfer(creditCard, null, 30.00));
    assertEquals(atm.AccountStatus(account), 50.00, 0.00);
  }

  @Test
  public void TransferCardNoAccountRecipientTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    atm.DepositFunds(creditCard, 50.00);
    IAccount accountRecipient = new Account();
    assertFalse(atm.Transfer(creditCard, accountRecipient, 30.00));
    assertEquals(atm.AccountStatus(accountRecipient), 0.00, 0.00);
  }

  @Test
  public void TransferNotEnoughFundsTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    IAccount accountRecipient = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 50.00);
    assertFalse(atm.Transfer(creditCard, accountRecipient, 80.00));
    assertEquals(atm.AccountStatus(accountRecipient), 0.00, 0.00);
    assertEquals(atm.AccountStatus(account), 50.00, 0.00);
  }

  @Test
  public void TransferMultipleTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    IAccount accountRecipient = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 50.00);
    assertTrue(atm.Transfer(creditCard, accountRecipient, 10.00));
    assertTrue(atm.Transfer(creditCard, accountRecipient, 20.00));
    assertEquals(atm.AccountStatus(accountRecipient), 30.00, 0.00);
    assertEquals(atm.AccountStatus(account), 20.00, 0.00);
  }

  @Test
  public void TransferMultipleFailedTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    IAccount accountRecipient = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 50.00);
    assertTrue(atm.Transfer(creditCard, accountRecipient, 10.00));
    assertFalse(atm.Transfer(creditCard, accountRecipient, 45.00));
    assertEquals(atm.AccountStatus(accountRecipient), 10.00, 0.00);
    assertEquals(atm.AccountStatus(account), 40.00, 0.00);
  }

  @Test
  public void TransferNegativeAmountTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    IAccount accountRecipient = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 50.00);
    assertFalse(atm.Transfer(creditCard, accountRecipient, -30.00));
    assertEquals(atm.AccountStatus(accountRecipient), 0.00, 0.00);
    assertEquals(atm.AccountStatus(account), 50.00, 0.00);
  }

  @Test
  public void TransferIncorrectPrecisionAmountTest(){
    IAtm atm = new Atm();
    ICreditCard creditCard = new CreditCard();
    IAccount account = new Account();
    IAccount accountRecipient = new Account();
    creditCard.Init("0000", "0000");
    creditCard.AddAccount(account);
    atm.DepositFunds(creditCard, 50.00);
    assertFalse(atm.Transfer(creditCard, accountRecipient, 30.009));
    assertEquals(atm.AccountStatus(accountRecipient), 0.00, 0.00);
    assertEquals(atm.AccountStatus(account), 50.00, 0.00);
  }
}