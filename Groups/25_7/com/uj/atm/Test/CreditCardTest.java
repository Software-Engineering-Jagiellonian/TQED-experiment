package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {

    private ICreditCard card;

    @Before
    public void BeforeEach() {
        card = new CreditCard();
    }

    // D - not 4; eq, A - Init
    @Test(expected = IllegalArgumentException.class)
    public void InitNot4()
    {
        card.Init("11111", "11111");
    }
    // D - not digits; eq, A - Init
    @Test(expected = IllegalArgumentException.class)
    public void Init4NonDig()
    {
        card.Init("111a", "111a");
    }
    // D - 4 digits; non eq, A - Init
    @Test(expected = IllegalArgumentException.class)
    public void InitNotEq()
    {
        card.Init("1112", "1111");
    }
    // D - 4 digits; eq, A - Init
    @Test
    public void Init()
    {
        card.Init("1111", "1111");
    }
    // A - Reinit; I - 2
    @Test(expected = IllegalStateException.class)
    public void Reinit()
    {
        card.Init("1111", "1111");
        card.Init("1111", "1111");
    }

    // A - ChangePin
    @Test
    public void ChangeNotInit()
    {
        assertFalse(card.ChangePin("0000", "1111", "1111"));
    }
    //D - Wrong Old, A - Init; ChangePin
    @Test
    public void ChangeInitWrong()
    {
        card.Init("1111", "1111");
        assertFalse(card.ChangePin("0000", "1112", "1112"));
    }

    //D - Not eq, A - Init; ChangePin
    @Test
    public void ChangeInitNotEq()
    {
        card.Init("1111", "1111");
        assertFalse(card.ChangePin("1111", "1112", "1113"));
    }

    //D - Not dig, A - Init; ChangePin
    @Test
    public void ChangeInitNotD()
    {
        card.Init("1111", "1111");
        assertFalse(card.ChangePin("1111", "111a", "111a"));
    }

    //D - Not 4, A - Init; ChangePin
    @Test
    public void ChangeInitNot4()
    {
        card.Init("1111", "1111");
        assertFalse(card.ChangePin("1111", "111", "111"));
    }

    // A - isValid
    @Test
    public void IsNalidNotInit()
    {
        assertFalse(card.IsPinValid("0000"));
    }

    // D- Wrong; A - Init; isValid
    @Test
    public void IsValidInit()
    {
        card.Init("1111", "1111");
        assertFalse(card.IsPinValid("0000"));
    }

    // D - Right; A - Init; isValid
    @Test
    public void IsValidRight()
    {
        card.Init("1111", "1111");
        assertTrue(card.IsPinValid("1111"));
    }

    // D - Right; A - Init; ChangePin; isValid
    @Test
    public void IsValidChange()
    {
        card.Init("1111", "1111");
        card.ChangePin("1111", "1112", "1112");
        assertTrue(card.IsPinValid("1112"));
    }

    //D- null, A - GetAcc
    @Test
    public void IsGetNullAcc()
    {
        assertEquals(card.GetAccount(), null);
    }

    //D- null, A - Set; GetAcc
    @Test
    public void SetGetNullAcc()
    {
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertEquals(card.GetAccount(), acc);
    }

    // A - Set; I - 2
    @Test(expected = IllegalStateException.class)
    public void ResetAcc()
    {
        IAccount acc = new Account();
        card.AddAccount(acc);
        card.AddAccount(new Account());
    }

    // A - AddAcc; Deposit
    @Test
    public void DepositNoInit()
    {
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertFalse(card.DepositFunds(2.0));
    }
    // A - AddAcc; Init
    @Test
    public void DepositNoAcc()
    {
        card.Init("1111", "1111");
        assertFalse(card.DepositFunds(2.0));
    }

    // A - AddAcc; Init; Deposit
    @Test
    public void Deposit()
    {
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertTrue(card.DepositFunds(2.0));
        assertEquals(acc.AccountStatus(), 2.0, 0.001);
    }

    // A - AddAcc; Withdraw
    @Test
    public void WithdrawNoInit()
    {
        IAccount acc = new Account();
        card.AddAccount(acc);
        assertFalse(card.WithdrawFunds(0.0));
    }
    // A - AddAcc; Init; Withdraw
    @Test
    public void WithdrawNoAcc()
    {
        card.Init("1111", "1111");
        assertFalse(card.WithdrawFunds(0.0));
    }

    // A - AddAcc; Init; Deposit; Withdraw
    @Test
    public void Withdraw()
    {
        card.Init("1111", "1111");
        IAccount acc = new Account();
        card.AddAccount(acc);
        card.DepositFunds(2.0);
        assertTrue(card.WithdrawFunds(1.0));
        assertEquals(acc.AccountStatus(), 1.0, 0.001);
    }

    // Time impact impossible
}