package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AtmTest {

    private IAtm atm;
    private IAccount account;
    private IAccount account2;
    private ICreditCard card;
    @Before
    public void setUp() {
        atm = new Atm();
        account = new Account();
        account2 = new Account();
        card = new CreditCard();
        card.AddAccount(account);
    }

    @Test
    public void test1() {
        assertEquals("AccountStatus - correct input", 0.0, atm.AccountStatus(account), 0);

        assertEquals("CheckPinAndLogIn - correct input", true, atm.CheckPinAndLogIn(card, "0000"));
        assertEquals("CheckPinAndLogIn - incorrect input", false, atm.CheckPinAndLogIn(card, "0001"));
        assertEquals("CheckPinAndLogIn - incorrect input", false, atm.CheckPinAndLogIn(card, "abdc"));

        assertEquals("ChangePinCard - correct input ", true, atm.ChangePinCard(card, "0000", "1233", "1233"));
        assertEquals("ChangePinCard - incorrect input", false, atm.ChangePinCard(card, "1233", "1233", "1234"));
        assertEquals("ChangePinCard - incorrect input", false, atm.ChangePinCard(card, "1233", "1233", "abcd"));
        assertEquals("ChangePinCard - incorrect input", false, atm.ChangePinCard(card, "1233", "accc", "abcd"));

        assertEquals("DepositFunds - correct input?", 10.0, atm.DepositFunds(card, 10.), 0);
        assertEquals("DepositFunds - incorrect input", 10.0, atm.DepositFunds(card, -10.), 0);
        assertEquals("DepositFunds - incorrect input", 0.0, atm.AccountStatus(account), 10.);

        assertEquals("WithdrawFunds - correct input", true, atm.WithdrawFunds(card, 2.2));
        assertEquals("WithdrawFunds - correct input", 7.8, atm.AccountStatus(account), 0);
        assertEquals("WithdrawFunds - correct input", false, atm.WithdrawFunds(card, -12.));
        assertEquals("WithdrawFunds - correct input", 7.8, atm.AccountStatus(account), 0);

        assertEquals("Transfer - correct input", true, atm.Transfer(card, account2, 1.2));
        assertEquals("Transfer - check status", 6.6, atm.AccountStatus(account), 0);
        assertEquals("Transfer - check status", 1.2, atm.AccountStatus(account2), 0);

        assertEquals("Transfer - incorrect input", false, atm.Transfer(card, account2, -1.2));
        assertEquals("Transfer - check status", 6.6, atm.AccountStatus(account), 0);
        assertEquals("Transfer - check status", 1.2, atm.AccountStatus(account2), 0);

        assertEquals("Transfer - incorrect input", false, atm.Transfer(card, account2, 100.2));
        assertEquals("Transfer - check status", 6.6, atm.AccountStatus(account), 0);
        assertEquals("Transfer - check status", 1.2, atm.AccountStatus(account2), 0);

        IAtm atm2 = new Atm();
        ICreditCard card2 = new CreditCard();
        IAccount account2 = new Account();
        try{
            atm.CheckPinAndLogIn(card2, "1112");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue("CheckPinAndLogIn test",true);
        }

        try{
            atm.AccountStatus(account2);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue("CheckPinAndLogIn test",true);
        }
        try{
            atm.ChangePinCard(card, "0000", "0001", "0001");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue("ChangePinCard test",true);
        }
        try{
            atm.DepositFunds(card, 10);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue("DepositFunds test",true);
        }
        try{
            atm.WithdrawFunds(card, 10);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue("WithdrawFunds test",true);
        }
    }


}
