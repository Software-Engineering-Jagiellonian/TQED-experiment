package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class AtmTest {
    ICreditCard defaultCard = new CreditCard();
    IAccount defaultAccount = new Account();
    IAtm atm = new Atm();

	//Przykłądowy test
    //@Test
    //public void test01() {
    //    System.out.println("jestem fajnym testem?");
    //    assertTrue(true);
    //}

    @Test
    public void checkPinAndLogIn() {
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
    }

    @Test
    public void checkPinAndLogInTwoTimesSamePin() {
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
    }

    @Test
    public void checkPinAndLogInTwoTimesDifferentPin() {
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertFalse(atm.CheckPinAndLogIn(defaultCard, "2211"));
    }

    @Test
    public void checkPinAndLogInThanDepositFunds() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 200));
    }

    @Test
    public void checkPinAndLogInThanDepositFundsAfter1Second() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ignored) {}
        assertTrue(atm.DepositFunds(defaultCard, 200));
    }

    @Test
    public void checkPinAndLogInThanDepositFundsAfter3Second() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ignored) {}
        assertFalse(atm.DepositFunds(defaultCard, 200));
    }

    @Test
    public void checkPinAndLogInThanDepositFundsWithoutAddingAccount() {
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertFalse(atm.DepositFunds(defaultCard, 200));
    }

    @Test
    public void tryToDepositFundsWithoutLoggingIn() {
        defaultCard.AddAccount(defaultAccount);
        assertFalse(atm.DepositFunds(defaultCard, 200));
    }

    @Test
    public void tryToDepositFundsWithDifferentCard() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 200));

        ICreditCard card = new CreditCard();
        card.AddAccount(defaultAccount);
        assertFalse(atm.DepositFunds(card, 200));
    }

    @Test
    public void checkPinAndLogInThanDepositFundsThanWithdrawFunds() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 200));
        assertTrue(atm.WithdrawFunds(defaultCard, 100));
    }

    @Test
    public void checkPinAndLogInThanWithdrawFunds() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertFalse(atm.WithdrawFunds(defaultCard, 100));
    }

    @Test
    public void checkPinAndLogInThanDepositFundsThanWithdrawTooMuchFunds() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 200));
        assertFalse(atm.WithdrawFunds(defaultCard, 300));
    }

    @Test
    public void checkPinAndLogInThanDepositFundsThanWithdrawFundsAfter1Second() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 200));
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ignored) {}
        assertTrue(atm.WithdrawFunds(defaultCard, 100));
    }

    @Test
    public void checkPinAndLogInThanDepositFundsThanWithdrawFundsAfter3Second() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 200));
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ignored) {}
        assertFalse(atm.WithdrawFunds(defaultCard, 100));
    }

    @Test
    public void logInThanTransfer() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 100));

        IAccount targetAccount = new Account();
        assertTrue(atm.Transfer(defaultCard, targetAccount, 50));
    }

    @Test
    public void logInThanTransferNegative() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 100));

        IAccount targetAccount = new Account();
        assertFalse(atm.Transfer(defaultCard, targetAccount, -50));
    }

    @Test
    public void logInThanTransferTooMuch() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 100));

        IAccount targetAccount = new Account();
        assertFalse(atm.Transfer(defaultCard, targetAccount, 150));
    }

    @Test
    public void TransferWithoutLoggingIn() {
        defaultCard.AddAccount(defaultAccount);
        assertFalse(atm.DepositFunds(defaultCard, 100));

        IAccount targetAccount = new Account();
        assertFalse(atm.Transfer(defaultCard, targetAccount, 150));
    }

    @Test
    public void logInDepositFundsWithDrawFundsTransfer() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 100));
        assertTrue(atm.WithdrawFunds(defaultCard, 50));

        IAccount targetAccount = new Account();
        assertTrue(atm.Transfer(defaultCard, targetAccount, 50));
    }

    @Test
    public void logInDepositFundsWithDrawFundsTransferNotEnough() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 100));
        assertTrue(atm.WithdrawFunds(defaultCard, 80));

        IAccount targetAccount = new Account();
        assertFalse(atm.Transfer(defaultCard, targetAccount, 50));
    }

    @Test
    public void logInDepositFundsTransferAfter3Second() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 100));

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ignored) {}
        IAccount targetAccount = new Account();
        assertFalse(atm.Transfer(defaultCard, targetAccount, 10));
    }

    @Test
    public void logInDepositFundsChangePinTransfer() {
        defaultCard.AddAccount(defaultAccount);
        assertTrue(atm.CheckPinAndLogIn(defaultCard, "0000"));
        assertTrue(atm.DepositFunds(defaultCard, 100));

        atm.ChangePinCard(defaultCard, "0000", "1111", "1111");

        IAccount targetAccount = new Account();
        assertFalse(atm.Transfer(defaultCard, targetAccount, 10));
    }

    @Test
    public void accountStatus() {
        IAccount account = new Account();
        atm.AccountStatus(account);
    }
}