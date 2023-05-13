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

    IAtm ATM = new Atm();
    ICreditCard card = new CreditCard();

    @Test
    public void test01() {
        IAccount account = new Account();
        account.DepositFunds(100);
        assertEquals(ATM.AccountStatus(account), 0.0, 0.0);
    }

    @Test
    public void test02() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        ATM.CheckPinAndLogIn(card,"1234");
        account.DepositFunds(100);
        assertEquals(ATM.AccountStatus(account), 0, 0.0);
    }

    @Test
    public void test03() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        card.AddAccount(account);
        ATM.CheckPinAndLogIn(card,"1234");
        account.DepositFunds(100);
        assertEquals(ATM.AccountStatus(account), 100, 0.0);
    }

    @Test
    public void test04() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        card.AddAccount(account);
        ATM.CheckPinAndLogIn(card,"1234");
        ATM.ChangePinCard(card, "1234", "0987", "0987");
        card.IsPinValid("0987");
    }

    @Test
    public void test05() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        card.AddAccount(account);
        ATM.CheckPinAndLogIn(card,"1234");
        ATM.DepositFunds(card, 123);
        assertEquals(account.AccountStatus(), 123, 0);
    }

    @Test
    public void test06() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        card.AddAccount(account);
        ATM.CheckPinAndLogIn(card,"1234");
        ATM.DepositFunds(card, 123);
        ATM.WithdrawFunds(card, 23);
        assertEquals(account.AccountStatus(), 100, 0);
    }

    @Test
    public void test07() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        card.AddAccount(account);
        ATM.CheckPinAndLogIn(card,"1234");
        ATM.WithdrawFunds(card, 23);
        ATM.DepositFunds(card, 123);
        assertEquals(account.AccountStatus(), 123, 0);
    }

    @Test
    public void test08() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        card.AddAccount(account);
        ATM.CheckPinAndLogIn(card,"1234");
        ATM.DepositFunds(card, 123);
        IAccount account1 = new Account();
        account1.DepositFunds(100);
        assertTrue(ATM.Transfer(card, account1, 100));
        assertEquals(account1.AccountStatus(), 200, 0.0);
        assertEquals(account.AccountStatus(), 23, 0.0);
    }

    @Test
    public void test09() {
        IAccount account = new Account();
        card.Init("1234", "1234");
        card.AddAccount(account);
        ATM.CheckPinAndLogIn(card,"1234");
        ATM.DepositFunds(card, 100);
        IAccount account1 = new Account();
        account1.DepositFunds(123);
        assertFalse(ATM.Transfer(card, account1, 100.01));
        assertEquals(account1.AccountStatus(), 123, 0.0);
        assertEquals(account.AccountStatus(), 100, 0.0);
    }


}