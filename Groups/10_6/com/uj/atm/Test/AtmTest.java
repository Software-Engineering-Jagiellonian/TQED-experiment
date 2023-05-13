package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AtmTest {

    private IAtm atm;
    private ICreditCard card;

    @Before
    public void beforeEach() {
        atm = new Atm();
        card = new CreditCard();
    }

    @Test
    public void shouldLoginWhenPinValid() {
        card.Init("1234", "1234");
        boolean res = atm.CheckPinAndLogIn(card, "1234");
        assertThat(res, is(true));
    }

    @Test
    public void shouldReturnFalseWhenPinInvalid() {
        card.Init("1234", "1234");
        boolean res = atm.CheckPinAndLogIn(card, "123");
        assertThat(res, is(false));
        res = atm.CheckPinAndLogIn(card, "12345");
        assertThat(res, is(false));
        res = atm.CheckPinAndLogIn(card, "123a");
        assertThat(res, is(false));
        res = atm.CheckPinAndLogIn(card, "ab23");
        assertThat(res, is(false));
        res = atm.CheckPinAndLogIn(card, "");
        assertThat(res, is(false));
        res = atm.CheckPinAndLogIn(card, null);
        assertThat(res, is(false));
    }

    @Test
    public void shouldReturnAccountStatus() {
        IAccount account = new Account();
        card.AddAccount(account);
        card.Init("1234", "1234");
        card.DepositFunds(120);
        double status = atm.AccountStatus(account);
        assertThat(status, is(120d));
    }

    @Test
    public void shouldReturnZeroWhenAccountIsNull() {
        card.DepositFunds(120);
        double status = atm.AccountStatus(null);
        assertThat(status, is(0d));
    }

    @Test
    public void shouldReturnTrueWhenNewPinValid() {
        card.Init("1234", "1234");
        boolean pinChanged = atm.ChangePinCard(card ,"1234", "4321", "4321");
        assertThat(pinChanged, is(true));
    }

    @Test
    public void shouldReturnFalseWhenOldPinInvalid() {
        card.Init("1234", "1234");
        boolean pinChanged = atm.ChangePinCard(card ,"12343", "4321", "4321");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"", "4321", "4321");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,null, "4321", "4321");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"123", "4321", "4321");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1235", "4321", "4321");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"123a", "4321", "4321");
        assertThat(pinChanged, is(false));
    }

    @Test
    public void shouldReturnFalseWhenNewPinInvalid() {
        card.Init("1234", "1234");
        boolean pinChanged = atm.ChangePinCard(card ,"1234", "432a", "432a");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1234", "43215", "43215");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1234", "4321", "43215");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1234", "432", "432");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1234", "", "4321");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1234", "4321", "");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1234", null, "4321");
        assertThat(pinChanged, is(false));
        pinChanged = atm.ChangePinCard(card ,"1235", "4321", null);
        assertThat(pinChanged, is(false));
    }

    @Test
    public void shouldReturnTrueWhenDepositFundsAdded() {
        IAccount account = new Account();
        card.AddAccount(account);
        boolean fundsAdded = atm.DepositFunds(card, 150);
        assertThat(fundsAdded, is(true));
    }

    @Test
    public void shouldReturnFalseWhenDepositFundsNotAdded() {
        boolean fundsAdded = atm.DepositFunds(card, 150);
        assertThat(fundsAdded, is(false));
    }

    @Test
    public void shouldReturnTrueWhenFundsWithdrawn() {
        IAccount account = new Account();
        card.AddAccount(account);
        atm.DepositFunds(card, 200);
        boolean fundsWithdrawn = atm.WithdrawFunds(card, 150);
        assertThat(fundsWithdrawn, is(true));
        assertThat(atm.AccountStatus(account), is(50d));
        fundsWithdrawn = atm.WithdrawFunds(card, 100);
        assertThat(fundsWithdrawn, is(true));
        assertThat(atm.AccountStatus(account), is(0d));
    }

    @Test
    public void shouldReturnFalseWhenFundsNotWithdrawn() {
        boolean fundsAdded = atm.WithdrawFunds(card, 150);
        assertThat(fundsAdded, is(false));
    }

    @Test
    public void shouldReturnTrueWhenTransferSuccessful() {
        IAccount account = new Account();
        card.AddAccount(account);
        atm.DepositFunds(card, 200);
        ICreditCard card2 = new CreditCard();
        IAccount account2 = new Account();
        card2.AddAccount(account2);
        boolean transferred = atm.Transfer(card, account2, 150);
        assertThat(transferred, is(true));
        assertThat(account.AccountStatus(), is(50d));
        assertThat(account2.AccountStatus(), is(150d));
    }

    @Test
    public void shouldReturnFalseWhenTransferUnsuccessful() {
        IAccount account = new Account();
        card.AddAccount(account);
        atm.DepositFunds(card, 200);
        ICreditCard card2 = new CreditCard();
        IAccount account2 = new Account();
        card2.AddAccount(account2);
        boolean transferred = atm.Transfer(card, account2, 300);
        assertThat(transferred, is(false));
        assertThat(account.AccountStatus(), is(200d));
        assertThat(account2.AccountStatus(), is(0d));
    }

    @Test
    public void shouldReturnFalseWhenObjectIsNull() {
        IAccount account = new Account();
        atm.DepositFunds(card, 200);
        ICreditCard card2 = new CreditCard();
        IAccount account2 = new Account();
        card2.AddAccount(account2);
        boolean transferred = atm.Transfer(null, account2, 300);
        assertThat(transferred, is(false));
        assertThat(account.AccountStatus(), is(0d));
        assertThat(account2.AccountStatus(), is(0d));
        transferred = atm.Transfer(card, null, 300);
        assertThat(transferred, is(false));
        assertThat(account.AccountStatus(), is(0d));
        assertThat(account2.AccountStatus(), is(0d));
        transferred = atm.Transfer(card, account2, 300);
        assertThat(transferred, is(false));
        assertThat(account.AccountStatus(), is(0d));
        assertThat(account2.AccountStatus(), is(0d));
    }

}