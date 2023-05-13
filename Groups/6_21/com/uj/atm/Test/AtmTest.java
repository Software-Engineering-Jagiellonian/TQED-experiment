package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {

    //D+Z
    @Test
    public void CheckPinAndLogIn_returnTrueWhenCardAndPinIsValid() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        Atm atm = new Atm();
        assertTrue(atm.CheckPinAndLogIn(creditCard, "1234"));
    }

    //D+Z
    @Test
    public void CheckPinAndLogIn_returnTrueWhenPinIsNotValid() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        Atm atm = new Atm();
        assertFalse(atm.CheckPinAndLogIn(creditCard, "123E"));
    }

    //D+Z
    @Test
    public void AccountStatus_returnBalanceWhenAccountIsValid() {
        IAccount account = new Account();
        account.DepositFunds(123.45);
        Atm atm = new Atm();
        assertEquals(123.45, atm.AccountStatus(account), 0);
    }

    //D+Z+Z
    @Test
    public void ChangePinCard_shouldReturnTrueForNewValidPin() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        assertTrue(atm.ChangePinCard(creditCard, "1234", "4312", "4312"));
    }

    //D+Z+Z
    @Test
    public void ChangePinCard_shouldReturnFalseForNotValidNewPin() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        assertFalse(atm.ChangePinCard(creditCard, "1234", "A312", "A312"));
    }

    //D+Z+Z
    @Test
    public void DepositFunds_returnTrueWhenDepositChangeAccountBalance() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        Atm atm = new Atm();
        assertTrue(atm.DepositFunds(creditCard, 100.01));
    }

    //D+Z+Z
    @Test
    public void WithdrawFunds_returnTrueWhenWithdrawalChangeAccountBalance() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123.45);
        creditCard.AddAccount(account);
        Atm atm = new Atm();
        assertTrue(atm.WithdrawFunds(creditCard, 123));
    }

    //D+Z+Z
    @Test
    public void Transfer_returnTrueWhenTransferChangeAccountBalancesForCardAndRecipient() {
        Atm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(123);
        creditCard.AddAccount(account);
        IAccount recipient = new Account();
        recipient.DepositFunds(29);
        assertTrue(atm.Transfer(creditCard, recipient, 100));
        assertEquals(23, atm.AccountStatus(account), 0);
        assertEquals(129, atm.AccountStatus(recipient), 0);
    }
}