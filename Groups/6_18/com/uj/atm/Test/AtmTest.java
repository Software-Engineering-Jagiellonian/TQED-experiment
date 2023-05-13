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
	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    @Test
    public void checkPinAndLogInTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        String pin = "0000";
        Assert.assertTrue("Chcek pin with default 0000 password", atm.CheckPinAndLogIn(creditCard, pin));
    }

    @Test
    public void failedCheckPinAndLogInTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        String pin = "1234";
        Assert.assertFalse("", atm.CheckPinAndLogIn(creditCard, pin));
    }

    @Test
    public void nullCardCheckPinAndLogInTest(){
        IAtm atm = new Atm();
        String pin = "0000";
        Assert.assertFalse("Chcek pin with default 0000 password", atm.CheckPinAndLogIn(null, pin));
    }

    @Test
    public void accountStatusTest(){
        IAtm atm = new Atm();
        IAccount account = new Account();
        double deposit = 123.45;
        account.DepositFunds(deposit);
        Assert.assertEquals("Account status should be equal 123.45", deposit, atm.AccountStatus(account), 0.0);
    }

    @Test
    public void positiveTransferTest(){
        IAtm atm = new Atm();
        ICreditCard card = new CreditCard();
        IAccount account = new Account();
        card.AddAccount(account);
        atm.DepositFunds(card, 140);
        IAccount accountRecipient = new Account();
        double amount = 123;
        boolean transferStatus = atm.Transfer(card, accountRecipient, amount);
        double accountStatus = atm.AccountStatus(account);
        double accountRecipientStatus = accountRecipient.AccountStatus();
        Assert.assertTrue("Correct transfer from one account to another", transferStatus && accountStatus == 17 && accountRecipientStatus == 123);
    }

    @Test
    public void negativeTransferTest(){
        IAtm atm = new Atm();
        ICreditCard card = new CreditCard();
        IAccount account = new Account();
        card.AddAccount(account);
        atm.DepositFunds(card, 140);
        IAccount accountRecipient = new Account();
        double amount = -123;
        boolean transferStatus = atm.Transfer(card, accountRecipient, amount);
        double accountStatus = atm.AccountStatus(account);
        double accountRecipientStatus = accountRecipient.AccountStatus();
        Assert.assertTrue("Negative value shouldn't be accept. Any account state shouldn't be changed", !transferStatus && accountStatus == 140 && accountRecipientStatus == 0);
    }

    @Test
    public void moreThanAccountTransferTest(){
        IAtm atm = new Atm();
        ICreditCard card = new CreditCard();
        IAccount account = new Account();
        card.AddAccount(account);
        atm.DepositFunds(card, 140);
        IAccount accountRecipient = new Account();
        double amount = 140.01;
        boolean transferStatus = atm.Transfer(card, accountRecipient, amount);
        double accountStatus = atm.AccountStatus(account);
        double accountRecipientStatus = accountRecipient.AccountStatus();
        Assert.assertTrue("Amount greater than account state shouldn't be accept. Any account state shouldn't be changed", !transferStatus && accountStatus == 140 && accountRecipientStatus == 0);
    }

    @Test
    public void nullAccountStatusTest(){
        IAtm atm = new Atm();
        double deposit = 123.45;
        Assert.assertEquals("Null account status shouldn't be returned. Default value 0.0", 0.0, atm.AccountStatus(null), 0.0);
    }

    @Test
    public void nullCardChangePinCardTest(){
        IAtm atm = new Atm();
        boolean changedPin = atm.ChangePinCard(null, "0000", "1234", "1234");
        Assert.assertFalse("Null card pin shouldn't be possible to change", changedPin);
    }

    @Test
    public void nullCardDepositFundsTest(){
        IAtm atm = new Atm();
        boolean deposited = atm.DepositFunds(null, 100);
        Assert.assertFalse("Null card account state shouldn't be possible to change", deposited);
    }

    @Test
    public void nullCardWithdrawFundsTest(){
        IAtm atm = new Atm();
        boolean withdrawStatus = atm.WithdrawFunds(null, 100);
        Assert.assertFalse("Null card account state shouldn't be possible to change", withdrawStatus);
    }

    @Test
    public void nullCardTransferTest(){
        IAtm atm = new Atm();
        IAccount accountRecipient = new Account();
        boolean transferStatus = atm.Transfer(null, accountRecipient, 100);
        double accountRecipientStatus = accountRecipient.AccountStatus();
        Assert.assertFalse("Null card transfer shouldn't be possible to change", transferStatus && accountRecipientStatus == 0);
    }

    @Test
    public void nullAccountRecipientTransferTest(){
        IAtm atm = new Atm();
        ICreditCard card = new CreditCard();
        IAccount account = new Account();
        card.AddAccount(account);
        atm.DepositFunds(card, 140);
        double amount = 123;
        boolean transferStatus = atm.Transfer(card, null, amount);
        double accountStatus = atm.AccountStatus(account);
        Assert.assertTrue("Correct transfer from one account to another", !transferStatus && accountStatus == 140);
    }

    @Test
    public void cardWithNullAccountTransferTest(){
        IAtm atm = new Atm();
        ICreditCard card = new CreditCard();
        atm.DepositFunds(card, 140);
        IAccount accountRecipient = new Account();
        double amount = 123;
        boolean transferStatus = atm.Transfer(card, accountRecipient, amount);
        double accountRecipientStatus = accountRecipient.AccountStatus();
        Assert.assertTrue("Transfer from card without account to recipient shouldn't be possible", !transferStatus && accountRecipientStatus == 0);
    }



    // Poniższe testy sprawdzają analogiczne przypadki użycia do tych pochodzących z interfejsu ICreditCard, z tą różnicą,
    // że owe metody wywoływane są z poziomu interfejsu IAtm.
    @Test
    public void changePinCardTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean returnedStatus = atm.ChangePinCard(creditCard, "1234", "1235", "1235");
        boolean validNewPin = atm.CheckPinAndLogIn(creditCard, "1235");
        Assert.assertTrue(returnedStatus && validNewPin);
    }

    @Test
    public void changePinCardFailedOldTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean returnedStatus = atm.ChangePinCard(creditCard,"1233", "1235", "1235");
        boolean validNewPin = atm.CheckPinAndLogIn(creditCard, "1235");
        Assert.assertFalse(returnedStatus || validNewPin);
    }

    @Test
    public void changePinCardFailedConfirmTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean returnedStatus = atm.ChangePinCard(creditCard, "1234", "1235", "1236");
        boolean validNewPin = creditCard.IsPinValid("1235");
        Assert.assertFalse(returnedStatus || validNewPin);
    }

    // Poniższe testy sprawdzają analogiczne przypadki użycia do tych pochodzących z interfejsu IAccount, z tą różnicą,
    // że owe metody wywoływane są z poziomu interfejsu IAtm
    @Test
    public void depositPositiveTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        double amount = 100.32;
        double balance = 100.32;
        boolean result = atm.DepositFunds(creditCard, amount);
        Assert.assertTrue("Amount not added to balance correctly", balance == account.AccountStatus() && result);
    }

    @Test
    public void depositNegativeTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        double amount = -100;
        double balance = 0;
        boolean result = atm.DepositFunds(creditCard, amount);
        Assert.assertTrue("DepositFunds method shouldn't accept negative amount", balance == account.AccountStatus() && !result);
    }

    @Test
    public void depositMoreThanWithdrawTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        atm.DepositFunds(creditCard, 200.50);
        double amount = 50.25;
        double balance = 150.25;
        boolean result = atm.WithdrawFunds(creditCard, amount);
        Assert.assertTrue("Amount not reduced to balance correctly", balance == account.AccountStatus() && result);
    }

    @Test
    public void depositThenWithdrawNegativeValueTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        atm.DepositFunds(creditCard, 200);
        double amount = -50;
        double balance = 200;
        boolean result = atm.WithdrawFunds(creditCard, amount);
        Assert.assertTrue("Withdraw method shouldn't accept negative amount", balance == account.AccountStatus() && !result);
    }

    @Test
    public void withdrawMoreThanDepositTest(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        atm.DepositFunds(creditCard, 100);
        double amount = 100.01;
        double balance = 100;
        boolean result = atm.WithdrawFunds(creditCard, amount);
        Assert.assertTrue("Withdraw method should be less than account balance", balance == account.AccountStatus() && !result);
    }

    @Test
    public void accountStatusAfterDepositAndWithdraw(){
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        atm.DepositFunds(creditCard,359.05);
        atm.WithdrawFunds(creditCard, 18.01);
        double balance = 341.04;
        Assert.assertEquals(balance, account.AccountStatus(), 0);
    }
}