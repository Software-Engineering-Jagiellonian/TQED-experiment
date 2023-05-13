package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {

    private IAtm atm;
    private ICreditCard creditCard;
    private IAccount account;

    @Before
    public void setUp() {
        this.atm = new Atm();
        this.creditCard = new CreditCard();
        this.account = new Account();
    }

    @Test
    public void testCheckPinAndLoginWithNullAccount() {
        creditCard.Init("1111", "1111");
        assertFalse(atm.CheckPinAndLogIn(creditCard, "1111"));
    }

    @Test
    public void testCheckPinAndLoginWithoutInitCard() {
        assertFalse(atm.CheckPinAndLogIn(creditCard, "1111"));
    }

    @Test
    public void testCheckPinAndLoginWithWrongPin() {
        creditCard.Init("1111", "1111");
        assertFalse(atm.CheckPinAndLogIn(creditCard, "2222"));
    }

    @Test
    public void testCheckPinAndLoginWithValidPin() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        assertTrue(atm.CheckPinAndLogIn(creditCard, "1111"));
    }

    @Test
    public void testCheckPinAndLoginWithValidPinWithDefaultPin() {
        assertFalse(atm.CheckPinAndLogIn(creditCard, "1234"));
    }

    @Test
    public void testCheckPinAndLoginWithWrongNullCreditCard() {
        assertFalse(atm.CheckPinAndLogIn(null, "1234"));
    }

    @Test(expected = SecurityException.class)
    public void testAccountStatusWithoutLogin() {
        atm.AccountStatus(account);
    }

    @Test
    public void testAccountStatus() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertEquals(0.0, atm.AccountStatus(account), 0.0);
    }

    @Test
    public void testChangePinCardWithoutCardInit() {
        assertFalse(creditCard.ChangePin("1234", "1111", "1111"));
    }

    @Test
    public void testChangePinCard() {
        creditCard.Init("1111", "1111");
        assertTrue(creditCard.ChangePin("1111", "2222", "2222"));
    }

    @Test
    public void testChangePingCardWithWrongOldPin() {
        creditCard.Init("1111", "1111");
        assertFalse(creditCard.ChangePin("1234", "2222", "2222"));
    }

    @Test
    public void testChangePingCardWithNotMatchConfirmPin() {
        creditCard.Init("1111", "1111");
        assertFalse(creditCard.ChangePin("1234", "2222", "3333"));
    }

    @Test
    public void testChangePingCardWithInvalidNewPin() {
        creditCard.Init("1111", "1111");
        assertFalse(creditCard.ChangePin("1234", "22222", "22222"));
    }

    @Test
    public void testDepositFunds() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertTrue(atm.DepositFunds(creditCard, 3.0));
    }

    @Test
    public void testDepositFundsWithoutLogin() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        assertFalse(atm.DepositFunds(creditCard, 3.0));
    }

    @Test
    public void testDepositNegativeAmount() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertFalse(atm.DepositFunds(creditCard, -3.0));
    }

    @Test
    public void testDepositZero() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertFalse(atm.DepositFunds(creditCard, 0.0));
    }

    @Test
    public void testWithdrawFundsWithEmptyAccount() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertFalse(atm.WithdrawFunds(creditCard, 3.0));
    }

    @Test
    public void testWithdrawFunds() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        atm.DepositFunds(creditCard, 3.0);
        assertTrue(atm.WithdrawFunds(creditCard, 3.0));
    }

    @Test
    public void testWithdrawFundsWithNegativeAmount() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertFalse(atm.WithdrawFunds(creditCard, -3.0));
    }

    @Test
    public void testWithdrawFundsWithNullCard() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertFalse(atm.WithdrawFunds(null, -3.0));
    }

    @Test
    public void testWithdrawFundsWithoutLogin() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        assertFalse(atm.WithdrawFunds(null, -3.0));
    }

    @Test
    public void testTransferWithNullCard() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        IAccount accountRecipient = new Account();
        assertFalse(atm.Transfer(null, accountRecipient, 1.0));
    }

    @Test
    public void testTransferWithNullAccountRecipient() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertFalse(atm.Transfer(creditCard, null, 1.0));
    }

    @Test
    public void testTransferWithoutAvailableAmount() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        IAccount accountRecipient = new Account();
        assertFalse(atm.Transfer(null, accountRecipient, 1.0));
    }

    @Test
    public void testTransferWithNegativeAmount() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        IAccount accountRecipient = new Account();
        assertFalse(atm.Transfer(null, accountRecipient, -1.0));
    }

    @Test
    public void testTransferWithoutLogin() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        IAccount accountRecipient = new Account();
        assertFalse(atm.Transfer(null, accountRecipient, 1.0));
    }

    @Test
    public void testTransferToOwnAccount() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        assertFalse(atm.Transfer(null, creditCard.GetAccount(), 0.0));
    }

    @Test
    public void testTransfer() {
        creditCard.Init("1111", "1111");
        creditCard.AddAccount(account);
        atm.CheckPinAndLogIn(creditCard, "1111");
        atm.DepositFunds(creditCard, 3.0);
        IAccount accountRecipient = new Account();
        atm.Transfer(creditCard, accountRecipient, 2.0);

        assertEquals(1.0, atm.AccountStatus(account), 0.0);
        assertEquals(2.0, accountRecipient.AccountStatus(), 0.0);
    }
}