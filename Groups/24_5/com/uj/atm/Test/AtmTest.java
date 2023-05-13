package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AtmTest {
    private IAccount setupAccount() {
        IAccount account = new Account();
        account.DepositFunds(100.0);

        return account;
    }

    private ICreditCard setupCreditCard() {
        ICreditCard card = new CreditCard();
        card.Init("1234", "1234");
        card.AddAccount(setupAccount());

        return card;
    }

    // Z
    @Test
    public void login() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
    }

    // Z
    @Test
    public void doesNotLoginWrongPin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertFalse(atm.CheckPinAndLogIn(card, "0000"));
    }

    // Z + I
    @Test
    public void loginTwice() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
    }

    // D + D + Z
    @Test
    public void loginAnotherCard() {
        IAtm atm = new Atm();
        ICreditCard card1 = setupCreditCard();
        ICreditCard card2 = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card1, "1234"));
        assertTrue(atm.CheckPinAndLogIn(card2, "1234"));
    }

    // D + Z
    @Test
    public void accountStatusNotLoggedInInaccessible() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertEquals(-1.0, atm.AccountStatus(card.GetAccount()), 0.0);
    }

    // D + Z
    @Test
    public void accountStatusLoggedIn() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        assertTrue(atm.CheckPinAndLogIn(card, "1234"));

        assertEquals(100.0, atm.AccountStatus(card.GetAccount()), 0.0);
    }

    // D + D + Z
    @Test
    public void accountStatusAnotherAccountInaccessible() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        IAccount anotherAccount = setupAccount();
        assertTrue(atm.CheckPinAndLogIn(card, "1234"));

        assertEquals(-1.0, atm.AccountStatus(anotherAccount), 0.0);
    }

    // D + D + Z
    @Test
    public void accountStatusAnotherCardInaccessible() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        ICreditCard anotherCard = setupCreditCard();
        assertTrue(atm.CheckPinAndLogIn(card, "1234"));

        assertEquals(-1.0, atm.AccountStatus(anotherCard.GetAccount()), 0.0);
    }

    // D + D + Z + Z
    @Test
    public void accountStatusTwoCards() {
        IAtm atm = new Atm();
        ICreditCard card1 = setupCreditCard();
        ICreditCard card2 = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card1, "1234"));
        assertEquals(100.0, atm.AccountStatus(card1.GetAccount()), 0.0);
        assertEquals(-1.0, atm.AccountStatus(card2.GetAccount()), 0.0);

        assertTrue(atm.CheckPinAndLogIn(card2, "1234"));
        assertEquals(-1.0, atm.AccountStatus(card1.GetAccount()), 0.0);
        assertEquals(100.0, atm.AccountStatus(card2.GetAccount()), 0.0);
    }

    // D + Z
    @Test
    public void doesNotChangePinWithoutLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertFalse(atm.ChangePinCard(card, "1234", "4321", "4321"));
    }

    // D + Z + Z
    @Test
    public void changePinWithLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertTrue(atm.ChangePinCard(card, "1234", "4321", "4321"));
        assertFalse(atm.CheckPinAndLogIn(card, "1234"));
        assertTrue(atm.CheckPinAndLogIn(card, "4321"));
    }

    // D + Z
    @Test
    public void doesNotChangePinWrongOldPin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.ChangePinCard(card, "0000", "4321", "4321"));
    }

    // D + D + Z + Z
    @Test
    public void doesNotChangePinOnAnotherCard() {
        IAtm atm = new Atm();
        ICreditCard card1 = setupCreditCard();
        ICreditCard card2 = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card1, "1234"));
        assertFalse(atm.ChangePinCard(card2, "1234", "4321", "4321"));
    }

    // D + Z
    @Test
    public void doesNotDepositWithoutLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertFalse(atm.DepositFunds(card, 100.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + Z
    @Test
    public void depositWithLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertTrue(atm.DepositFunds(card, 100.0));
        assertEquals(200.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + Z
    @Test
    public void doesNotDepositToAnotherCard() {
        IAtm atm = new Atm();
        ICreditCard card1 = setupCreditCard();
        ICreditCard card2 = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card1, "1234"));
        assertFalse(atm.DepositFunds(card2, 100.0));
        assertEquals(100.0, card1.GetAccount().AccountStatus(), 0.0);
        assertEquals(100.0, card2.GetAccount().AccountStatus(), 0.0);
    }

    // D + D + Z + Z
    @Test
    public void doesNotDepositToCardWithoutAccount() {
        IAtm atm = new Atm();
        ICreditCard card = new CreditCard();
        card.Init("1234", "1234");

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.DepositFunds(card, 100.0));
    }

    // D + Z + I
    @Test
    public void doesNotDepositNegative() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.DepositFunds(card, -100.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + I
    @Test
    public void doesNotDepositZero() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.DepositFunds(card, 0.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z
    @Test
    public void doesNotWithdrawWithoutLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertFalse(atm.WithdrawFunds(card, 100.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + Z
    @Test
    public void withdrawWithLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertTrue(atm.WithdrawFunds(card, 100.0));
        assertEquals(0.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + Z
    @Test
    public void doesNotWithdrawFromAnotherCard() {
        IAtm atm = new Atm();
        ICreditCard card1 = setupCreditCard();
        ICreditCard card2 = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card1, "1234"));
        assertFalse(atm.WithdrawFunds(card2, 100.0));
        assertEquals(100.0, card1.GetAccount().AccountStatus(), 0.0);
        assertEquals(100.0, card2.GetAccount().AccountStatus(), 0.0);
    }

    // D + D + Z + Z
    @Test
    public void doesNotWithdrawFromCardWithoutAccount() {
        IAtm atm = new Atm();
        ICreditCard card = new CreditCard();
        card.Init("1234", "1234");

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.WithdrawFunds(card, 100.0));
    }

    // D + Z + I
    @Test
    public void doesNotWithdrawNegative() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.WithdrawFunds(card, -100.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + I
    @Test
    public void doesNotWithdrawZero() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.WithdrawFunds(card, 0.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + D + Z
    @Test
    public void doesNotWithdrawMoreThanBalance() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.WithdrawFunds(card, 200.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z
    @Test
    public void doesNotTransferWithoutLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        IAccount recipient = setupAccount();

        assertFalse(atm.Transfer(card, recipient, 50.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
        assertEquals(100.0, recipient.AccountStatus(), 0.0);
    }

    // D + Z + Z
    @Test
    public void transfersWithLogin() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        IAccount recipient = setupAccount();

        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
        assertEquals(100.0, recipient.AccountStatus(), 0.0);

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertTrue(atm.Transfer(card, recipient, 50.0));

        assertEquals(50.0, card.GetAccount().AccountStatus(), 0.0);
        assertEquals(150.0, recipient.AccountStatus(), 0.0);
    }

    // D + D + Z + Z
    @Test
    public void doesNotTransferToOwnAccount() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        IAccount recipient = card.GetAccount();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.Transfer(card, recipient, 50.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + Z + I
    @Test
    public void doesNotTransferZero() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        IAccount recipient = setupAccount();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.Transfer(card, recipient, 0.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + Z + I
    @Test
    public void doesNotTransferNegative() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        IAccount recipient = setupAccount();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.Transfer(card, recipient, -100.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + D + Z + Z
    @Test
    public void doesNotTransferMoreThanBalance() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();
        IAccount recipient = setupAccount();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        assertFalse(atm.Transfer(card, recipient, 200.0));
        assertEquals(100.0, card.GetAccount().AccountStatus(), 0.0);
    }

    // D + D + Z + Z + Z
    @Test
    public void twoCardsTransfer() {
        IAtm atm = new Atm();
        ICreditCard card1 = setupCreditCard();
        ICreditCard card2 = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card1, "1234"));
        assertEquals(100.0, atm.AccountStatus(card1.GetAccount()), 0.0);
        assertTrue(atm.Transfer(card1, card2.GetAccount(), 50.0));
        assertEquals(50.0, atm.AccountStatus(card1.GetAccount()), 0.0);

        assertFalse(atm.Transfer(card2, card1.GetAccount(), 50.0));

        assertTrue(atm.CheckPinAndLogIn(card2, "1234"));
        assertEquals(150.0, atm.AccountStatus(card2.GetAccount()), 0.0);
        assertTrue(atm.Transfer(card2, card1.GetAccount(), 50.0));
        assertEquals(100.0, atm.AccountStatus(card2.GetAccount()), 0.0);

        assertFalse(atm.Transfer(card1, card2.GetAccount(), 50.0));

        assertEquals(100.0, card1.GetAccount().AccountStatus(), 0.0);
        assertEquals(100.0, card2.GetAccount().AccountStatus(), 0.0);
    }

    // D + Z + C
    @Test
    public void withdrawAfterSleep() {
        IAtm atm = new Atm();
        ICreditCard card = setupCreditCard();

        assertTrue(atm.CheckPinAndLogIn(card, "1234"));
        try {
            TimeUnit.SECONDS.sleep(3);
            assertTrue(atm.WithdrawFunds(card, 50.0));
        } catch (InterruptedException e) {
            fail();
        }
    }
}
