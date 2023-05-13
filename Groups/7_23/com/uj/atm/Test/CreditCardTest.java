package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreditCardTest {
    private IAccount account;
    private ICreditCard card;

    @Before
    public void setUp() {
        account = new Account();
        card = new CreditCard();
        card.Init("0001", "0001");
        card.AddAccount(account);
    }

    @Test
    public void test1() {
        assertEquals("ChangePin - correct input", true, card.ChangePin("0001", "0021", "0021"));
        assertEquals("ChangePin - incorrect oldPin", false, card.ChangePin("0001", "0021", "0021"));
        assertEquals("ChangePin - incorrect newPinConfirm", false, card.ChangePin("0021", "0022", "0021"));
        assertEquals("ChangePin - too long newPin", false, card.ChangePin("0021", "00222", "00222"));

        assertEquals("IsPinValid - incorrect input", false, card.IsPinValid("0001"));
        assertEquals("IsPinValid - to long input", false, card.IsPinValid("000101"));
        assertEquals("IsPinValid - correct input", true, card.IsPinValid("0021"));


        assertEquals("GetAccount - correct input", account, card.GetAccount());

        assertEquals("DepositFunds - correct input", true, card.DepositFunds(10));
        assertEquals("DepositFunds - incorrect input", false, card.DepositFunds(-10));
        assertEquals("DepositFunds - check balance", 10, account.AccountStatus(), 0);

        assertEquals("WithdrawFunds - correct input", true, card.WithdrawFunds(10));
        assertEquals("WithdrawFunds - incorrect input", false, card.WithdrawFunds(-10));
        assertEquals("WithdrawFunds - incorrect input - grater than Balance", false, card.WithdrawFunds(100));
        assertEquals("WithdrawFunds - check balanc", 0, account.AccountStatus(), 0);

        ICreditCard card2 = new CreditCard();
        try{
            card2.AddAccount(null);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue("AddAccount and GetAccount test",card2.GetAccount() == null);
        }

        try{
            card2.Init("111", "112");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue("Init test",true);
        }


    }

}
