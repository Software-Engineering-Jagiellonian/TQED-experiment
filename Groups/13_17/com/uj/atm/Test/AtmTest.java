package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {

    private Account account = new Account();
    private CreditCard card = new CreditCard();
    private Atm atm = new Atm();

    @Test
    public void testCheckPinAndLogIn() {
        assertTrue(atm.CheckPinAndLogIn(card, "0000"));
        assertFalse(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.CheckPinAndLogIn(card, "0001"));
        assertFalse(atm.CheckPinAndLogIn(card, "1000"));
        assertFalse(atm.CheckPinAndLogIn(card, ""));
        assertFalse(atm.CheckPinAndLogIn(card, "****"));
        assertFalse(atm.CheckPinAndLogIn(card, "00000"));
    }

    @Test
    public void testAccountStatus() {
        assertEquals(atm.AccountStatus(account), 0, 1e-9);
        account.DepositFunds(100);
        assertEquals(atm.AccountStatus(account), 100, 1e-9);
        account.DepositFunds(50);
        assertEquals(atm.AccountStatus(account), 150, 1e-9);
        account.WithdrawFunds(75);
        assertEquals(atm.AccountStatus(account), 75, 1e-9);
    }

    @Test
    public void testChangePinCard() {
        assertTrue(atm.ChangePinCard(card, "0000", "1234", "1234"));
        assertTrue(atm.ChangePinCard(card, "1234", "0101", "0101"));
        assertFalse(atm.ChangePinCard(card, "0101", "12345", "12345"));
        assertFalse(atm.ChangePinCard(card, "0101", "123", "123"));
        assertFalse(atm.ChangePinCard(card, "0101", "abcd", "abcd"));
        assertFalse(atm.ChangePinCard(card, "0102", "1234", "1234"));
    }

    @Test
    public void testDepositFunds() {
        assertFalse(atm.DepositFunds(card, 100));
        card.AddAccount(account);
        assertTrue(atm.DepositFunds(card, 100));
        assertFalse(atm.DepositFunds(card, -100));
    }

    @Test
    public void testWithdrawFunds() {
        assertFalse(atm.WithdrawFunds(card, 0));
        card.AddAccount(account);
        account.DepositFunds(100);
        assertTrue(atm.WithdrawFunds(card, 100));
        assertFalse(atm.WithdrawFunds(card, 1));
        account.DepositFunds(1);
        assertTrue(atm.WithdrawFunds(card, 1));
    }

    @Test
    public void testTransfer() {
        assertFalse(atm.Transfer(card, account, 50));

        Account transferAcc = new Account();
        card.AddAccount(account);
        assertFalse(atm.Transfer(card, transferAcc, 50));

        card.DepositFunds(50);
        assertTrue(atm.Transfer(card, transferAcc, 50));
        assertFalse(atm.Transfer(card, transferAcc, 1));

        assertEquals(card.GetAccount().AccountStatus(), 0, 1e-9);
        assertEquals(transferAcc.AccountStatus(), 50, 1e-9);
    }

}