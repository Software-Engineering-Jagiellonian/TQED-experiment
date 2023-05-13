package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    @Test
    public void beforeInitTest(){
        // Domyślan wartości pinu wg. README.md to "0000"
        ICreditCard creditCard = new CreditCard();
        Assert.assertTrue("Przed zainicjowaniem karty, jej pin powinien posiadać wartość domyślną zgodną z README.md", creditCard.IsPinValid("0000"));
    }

    @Test
    public void initAndValidTest(){
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Assert.assertTrue(creditCard.IsPinValid("1234"));
    }

    @Test
    public void failedLongInitAndValidTest(){
        ICreditCard creditCard = new CreditCard();;
        creditCard.Init("12345", "12345");
        Assert.assertFalse(creditCard.IsPinValid("12345"));
    }

    @Test
    public void failedConfirmInitAndValidTest(){
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1235");
        Assert.assertFalse(creditCard.IsPinValid("1234"));
    }

    @Test
    public void initAndFailedValidTest(){
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Assert.assertFalse(creditCard.IsPinValid("1235"));
    }

    @Test
    public void changePinTest(){
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean returnedStatus = creditCard.ChangePin("1234", "1235", "1235");
        boolean validNewPin = creditCard.IsPinValid("1235");
        Assert.assertTrue(returnedStatus && validNewPin);
    }

    @Test
    public void changePinFailedOldTest(){
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        boolean returnedStatus = creditCard.ChangePin("1233", "1235", "1235");
        boolean validNewPin = creditCard.IsPinValid("1235");
        Assert.assertFalse(returnedStatus || validNewPin);
    }

    @Test
    public void changePinFailedConfirmTest(){
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");;
        boolean returnedStatus = creditCard.ChangePin("1234", "1235", "1236");
        boolean validNewPin = creditCard.IsPinValid("1235");
        Assert.assertFalse(returnedStatus || validNewPin);
    }

    @Test
    public void addAndGetAccountTest(){
        IAccount account = new Account();
        ICreditCard creditCard = new CreditCard();;
        creditCard.AddAccount(account);
        Assert.assertEquals(account, creditCard.GetAccount());
    }

    @Test
    public void tryToChangeAndGetAccountTest(){
        IAccount account = new Account();
        IAccount secondaryAccount = new Account();
        ICreditCard creditCard = new CreditCard();
        creditCard.AddAccount(account);
        creditCard.AddAccount(secondaryAccount);
        Assert.assertEquals(account, creditCard.GetAccount());
    }

    @Test
    public void notInitAccountDepositTest(){
        ICreditCard creditCard = new CreditCard();
        Assert.assertFalse(creditCard.DepositFunds(100));
    }

    @Test
    public void notInitAccountWithdrawTest(){
        ICreditCard creditCard = new CreditCard();
        Assert.assertFalse(creditCard.WithdrawFunds(100));
    }

    // Poniższe testy sprawdzają analogiczne przypadki użycia do tych pochodzących z interfejsu IAccount, z tą różnicą,
    // że owe metody wywoływane są z poziomu interfejsu ICreditCard
    @Test
    public void depositPositiveTest(){
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        double amount = 100.32;
        double balance = 100.32;
        boolean result = creditCard.DepositFunds(amount);
        Assert.assertTrue("Amount not added to balance correctly", balance == account.AccountStatus() && result);
    }

    @Test
    public void depositNegativeTest(){
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        double amount = -100;
        double balance = 0;
        boolean result = creditCard.DepositFunds(amount);
        Assert.assertTrue("DepositFunds method shouldn't accept negative amount", balance == account.AccountStatus() && !result);
    }

    @Test
    public void depositMoreThanWithdrawTest(){
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(200.50);
        double amount = 50.25;
        double balance = 150.25;
        boolean result = creditCard.WithdrawFunds(amount);
        Assert.assertTrue("Amount not reduced to balance correctly", balance == account.AccountStatus() && result);
    }

    @Test
    public void depositThenWithdrawNegativeValueTest(){
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(200);
        double amount = -50;
        double balance = 200;
        boolean result = creditCard.WithdrawFunds(amount);
        Assert.assertTrue("Withdraw method shouldn't accept negative amount", balance == account.AccountStatus() && !result);
    }

    @Test
    public void withdrawMoreThanDepositTest(){
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(100);
        double amount = 100.01;
        double balance = 100;
        boolean result = creditCard.WithdrawFunds(amount);
        Assert.assertTrue("Withdraw method should be less than account balance", balance == account.AccountStatus() && !result);
    }

    @Test
    public void accountStatusAfterDepositAndWithdraw(){
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        creditCard.DepositFunds(359.05);
        creditCard.WithdrawFunds(18.01);
        double balance = 341.04;
        Assert.assertEquals(balance, account.AccountStatus(), 0);
    }
}