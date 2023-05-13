package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {

    @Test
    public void CheckPinAndLogInReturnsFalseBecausePinIsWrong() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        boolean result = atm.CheckPinAndLogIn(creditCard, "1235");
        assertFalse(result);
    }

    @Test
    public void CheckPinAndLogReturnsTrue() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        boolean result = atm.CheckPinAndLogIn(creditCard, "1234");
        assertTrue(result);
    }

    @Test
    public void AccountStatusReturnsStatus() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Account newAccount = new Account();
        creditCard.AddAccount(newAccount);
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        IAccount account = creditCard.GetAccount();
        assertEquals(0.0, atm.AccountStatus(account), 0);
    }

    @Test
    public void AccountStatusReturnsNanBecauseAtmIsBlocked() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1235");
        IAccount account = creditCard.GetAccount();
        assertEquals(Double.NaN, atm.AccountStatus(account), 0);
    }

    @Test
    public void ChangePinCardReturnsFalseBecauseAtmIsBlocked() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1235");
        boolean result = atm.ChangePinCard(creditCard, "1234", "1236", "1236");
        assertFalse(result);
    }

    @Test
    public void ChangePinCardReturnsFalseBecauseOldPinIsWrong() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        boolean result = atm.ChangePinCard(creditCard, "1235", "1236", "1236");
        assertFalse(result);
    }

    @Test
    public void ChangePinCardReturnsFalseBecauseNewPinInWrongFormat() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        boolean result = atm.ChangePinCard(creditCard, "1234", "123f", "123f");
        assertFalse(result);
    }

    @Test
    public void ChangePinCardReturnsFalseBecauseNewPinConfirmIsNotCorrect() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        boolean result = atm.ChangePinCard(creditCard, "1234", "1235", "1236");
        assertFalse(result);
    }

    @Test
    public void DepositFundsReturnsFalseBecauseAtmIsBlocked() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1235");
        boolean result = atm.DepositFunds(creditCard, 150.0);
        assertFalse(result);
    }

    @Test
    public void DepositFundsReturnsFalseBecauseCardIsNotInitialize() {
        CreditCard creditCard  = new CreditCard();
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "0000");
        boolean result = atm.DepositFunds(creditCard, 150.0);
        assertFalse(result);
    }

    @Test
    public void DepositFundsReturnsTrue() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        boolean result = atm.DepositFunds(creditCard, 150.0);
        assertFalse(result);
    }

    @Test
    public void WithdrawFundsReturnsFalseBecauseAtmIsBlocked() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1235");
        boolean result = atm.WithdrawFunds(creditCard, 150.0);
        assertFalse(result);
    }

    @Test
    public void WithdrawFundsReturnsFalseBecauseCardHasSmallerFunds() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        atm.DepositFunds(creditCard, 100.0);
        boolean result = atm.WithdrawFunds(creditCard, 150.0);
        assertFalse(result);
    }

    @Test
    public void WithdrawFundsReturnsTrue() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        atm.DepositFunds(creditCard, 150.0);
        boolean result = atm.WithdrawFunds(creditCard, 150.0);
        assertFalse(result);
    }

    @Test
    public void TransferReturnsFalseBecauseAtmIsBlocked() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        atm.DepositFunds(creditCard, 150.0);
        Account account = new Account();
        account.DepositFunds(150.0);
        boolean result = atm.Transfer(creditCard, account, 150.0);
        assertFalse(result);
    }

    @Test
    public void TransferReturnsFalseBecauseCardHasSmallerFunds() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        atm.DepositFunds(creditCard, 100.0);
        Account account = new Account();
        account.DepositFunds(150.0);
        boolean result = atm.Transfer(creditCard, account, 150.0);
        assertFalse(result);
    }
    @Test
    public void TransferReturnsFalseBecauseAmountUnderZero() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        atm.DepositFunds(creditCard, 150.0);
        Account account = new Account();
        account.DepositFunds(150.0);
        boolean result = atm.Transfer(creditCard, account, -150.0);
        assertFalse(result);
    }
    @Test
    public void TransferReturnsTrue() {
        CreditCard creditCard  = new CreditCard();
        creditCard.Init("1234", "1234");
        Account newAccount = new Account();
        creditCard.AddAccount(newAccount);
        newAccount.DepositFunds(150.0);
        Atm atm = new Atm();
        atm.CheckPinAndLogIn(creditCard, "1234");
        atm.DepositFunds(creditCard, 150.0);
        Account account = new Account();
        account.DepositFunds(150.0);
        atm.Transfer(creditCard, account, 150.0);
        assertEquals(300.0, account.AccountStatus(), 0);
    }
}