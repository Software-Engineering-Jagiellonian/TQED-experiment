package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class CreditCardTest {
    private ICreditCard iCreditCard;

    @Before
    public void init() {
        this.iCreditCard = new CreditCard();
    }

    /**
     * Test sprawdzajcy walidacje pinu dla wartosci domyslnej
     * Wymiar: D
     */
    @Test
    public void test01() {
        assertTrue(iCreditCard.IsPinValid("0000"));
    }

    /**
     * Test sprawdzajcy walidacje pinu dla za dlugiej wartosci
     * Wymiar: D
     */
    @Test
    public void test02() {
        assertFalse(iCreditCard.IsPinValid("1456712347845"));
    }

    /**
     * Test sprawdzajcy walidacje pinu dla za krotkiej wartosci
     * Wymiar: D
     */
    @Test
    public void test03() {
        assertFalse(iCreditCard.IsPinValid("4"));
    }

    /**
     * Test sprawdzajcy walidacje pinu dla wartosci nienumerycznych
     * Wymiar: D + I
     */
    @Test
    public void test04() {
        assertFalse(iCreditCard.IsPinValid("932daz23"));
        assertFalse(iCreditCard.IsPinValid("Ąs35gczw"));
        assertFalse(iCreditCard.IsPinValid("是一個轉換繁體/簡體中文"));
    }

    /**
     * Test sprawdzajcy inicjalizacje pinu dla wartosci nienumerycznych
     * Wymiar: D + I
     */
    @Test
    public void test05() {
        iCreditCard.Init("簡體中文","簡體中文");
        assertFalse(iCreditCard.IsPinValid("簡體中文"));
    }

    /**
     * Test sprawdzajcy inicjalizacje karty
     * Wymiar: Z
     */
    @Test
    public void test06() {
        iCreditCard.Init("4321","4321");
        assertTrue(iCreditCard.IsPinValid("4321"));
    }

    /**
     * Test sprawdzajcy inicjalizacje karty
     * Wymiar: Z
     */
    @Test
    public void test07() {
        iCreditCard.Init("5421","1245");
        assertTrue(iCreditCard.IsPinValid("0000"));
    }

    /**
     * Test sprawdzajcy inicjalizacje karty dla pustej warosci pinu
     * Wymiar: D + Z
     */
    @Test
    public void test08() {
        iCreditCard.Init("","");
        assertTrue(iCreditCard.IsPinValid("0000"));
    }

    /**
     * Test sprawdzajcy inicjalizacje karty dla za dlugiej warosci pinu
     * Wymiar: D + Z
     */
    @Test
    public void test09() {
        iCreditCard.Init("542114","542114");
        assertTrue(iCreditCard.IsPinValid("0000"));
    }

    /**
     * Test sprawdzajcy inicjalizacje karty dla za krotkiej warosci pinu
     * Wymiar: D + Z
     */
    @Test
    public void test10() {
        iCreditCard.Init("7","7");
        assertTrue(iCreditCard.IsPinValid("0000"));
    }

    /**
     * Test sprawdzajcy inicjalizacje karty dla za roznych dlugosci pinu
     * Wymiar: D + Z
     */
    @Test
    public void test11() {
        iCreditCard.Init("734","1237");
        assertTrue(iCreditCard.IsPinValid("0000"));
    }

    /**
     * Test sprawdzajcy inicjalizacje karty dla za roznych dlugosci pinu
     * Wymiar: D + Z
     */
    @Test
    public void test12() {
        iCreditCard.Init("734","1237");
        assertTrue(iCreditCard.IsPinValid("0000"));
    }

    /**
     * Test sprawdzajcy zmiane pinu dla poprawnie zainicjalizowanej karty
     * Wymiar: Z
     */
    @Test
    public void test13() {
        iCreditCard.Init("1234","1234");
        assertTrue(iCreditCard.ChangePin("1234","3451","3451"));
    }

    /**
     * Test sprawdzajcy zmiane pinu dla zlej wartosci weryfikujacej
     * Wymiar: D + Z
     */
    @Test
    public void test14() {
        iCreditCard.Init("1234","1234");
        assertFalse(iCreditCard.ChangePin("1234","1245","1324"));
    }

    /**
     * Test sprawdzajcy zmiane pinu dla zlej wartosci starego pinu
     * Wymiar: D + Z
     */
    @Test
    public void test15() {
        iCreditCard.Init("1234","1234");
        assertFalse(iCreditCard.ChangePin("4321","1245","1245"));
    }

    /**
     * Test sprawdzajcy zmiane pinu dla pustych wartosci
     * Wymiar: D + Z
     */
    @Test
    public void test16() {
        iCreditCard.Init("1234","1234");
        assertFalse(iCreditCard.ChangePin("","",""));
    }

    /**
     * Test sprawdzajcy zmiane pinu dla zle zainicjalizowanej karty
     * Wymiar: Z + Z
     */
    @Test
    public void test17() {
        iCreditCard.Init("4321","1234");
        assertFalse(iCreditCard.ChangePin("1234","4321","4321"));
    }

    /**
     * Test sprawdzajcy zmiane pinu dla zle zainicjalizowanej karty
     * Wymiar: Z + Z
     */
    @Test
    public void test18() {
        iCreditCard.Init("4321","1234");
        assertFalse(iCreditCard.ChangePin("1234","4321","4321"));
    }

    /**
     * Test sprawdzajcy zmiane podwojna zmiane pinu
     * Wymiar: Z + C
     */
    @Test
    public void test19() {
        iCreditCard.Init("4321","4321");
        assertTrue(iCreditCard.ChangePin("4321","1234","1234"));
        assertTrue(iCreditCard.ChangePin("1234","5431","5431"));
    }

    /**
     * Test sprawdzajcy zmiane pinu dla nienumerycznych znakow
     * Wymiar: Z + I
     */
    @Test
    public void test20() {
        iCreditCard.Init("4321","4321");
        assertFalse(iCreditCard.ChangePin("1234","xy","xy"));
    }

    /**
     * Test sprawdzajcy poprawne dodanie konta karty
     * Wymiar: Z
     */
    @Test
    public void test21() {
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        assertSame(acc,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy brak przypisanego konta do karty
     * Wymiar: Z
     */
    @Test
    public void test22() {
        assertSame(null,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy podwojne przypisanie konta do karty
     * Wymiar: Z + C
     */
    @Test
    public void test23() {
        IAccount acc = new Account();
        IAccount acc_sec = new Account();
        iCreditCard.AddAccount(acc);
        iCreditCard.AddAccount(acc_sec);
        assertSame(acc,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy podwojne przypisanie konta do karty
     * Wymiar: Z + C
     */
    @Test
    public void test24() {
        assertSame(null,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy przypisanie konta do karty i inicjalizacje
     * Wymiar: Z + Z
     */
    @Test
    public void test25() {
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        iCreditCard.Init("1234","1234");
        assertSame(acc,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy przypisanie konta do karty i inicjalizacje
     * Wymiar: Z + Z + Z
     */
    @Test
    public void test26() {
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        iCreditCard.Init("1234","1234");
        assertSame(acc,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy inicjalizacje, zmiane pinu i dodanie konta
     * Wymiar: Z + Z + Z
     */
    @Test
    public void test27() {
        IAccount acc = new Account();
        iCreditCard.Init("1234","1234");
        assertTrue(iCreditCard.ChangePin("1234","5431","5431"));
        iCreditCard.AddAccount(acc);
        assertSame(acc,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy inicjalizacje, zmiane pinu i dodanie konta
     * Wymiar: Z + Z + Z
     */
    @Test
    public void test28() {
        iCreditCard.Init("1234","1234");
        IAccount acc = new Account();
        assertTrue(iCreditCard.ChangePin("1234","5431","5431"));
        iCreditCard.AddAccount(acc);
        assertSame(acc,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy inicjalizacje, zmiane pinu i dodanie konta
     * Wymiar: Z + I
     */
    @Test
    public void test29() {
        iCreditCard.Init("1234","1234");
        iCreditCard.AddAccount(null);
        assertSame(null,iCreditCard.GetAccount());
    }

    /**
     * Test sprawdzajcy dodanie pieniedzy do niezainicjalizowanej karty
     * Wymiar: Z
     */
    @Test
    public void test30() {
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        assertFalse(iCreditCard.DepositFunds(200.0));
    }

    /**
     * Test sprawdzajcy dodanie pieniedzy do niestowarzyszonego konta
     * Wymiar: Z
     */
    @Test
    public void test31() {
        iCreditCard.Init("1234","1234");
        assertFalse(iCreditCard.DepositFunds(200.0));
    }

    /**
     * Test sprawdzajcy wyplate pieniedzy z konta o niewystarczajacej ilosci pieniedzy
     * Wymiar: Z
     */
    @Test
    public void test32() {
        iCreditCard.Init("1234","1234");
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        assertFalse(iCreditCard.WithdrawFunds(200.0));
    }

    /**
     * Test sprawdzajcy wplate i wyplate pieniedzy z konta
     * Wymiar: Z + Z
     */
    @Test
    public void test33() {
        iCreditCard.Init("1234","1234");
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        assertTrue(iCreditCard.DepositFunds(200.0));
        assertTrue(iCreditCard.WithdrawFunds(150.0));
    }

    /**
     * Test sprawdzajcy wplate i wyplate pieniedzy z konta w przypadku niezainicjalizowanej karty
     * Wymiar: Z + Z
     */
    @Test
    public void test34() {
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        assertFalse(iCreditCard.DepositFunds(200.0));
        assertFalse(iCreditCard.WithdrawFunds(150.0));
    }

    /**
     * Test sprawdzajcy wplate i wyplate pieniedzy rownej wartosci pieniedzy
     * Wymiar: Z + Z + I
     */
    @Test
    public void test35() {
        iCreditCard.Init("1234","1234");
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        assertTrue(iCreditCard.DepositFunds(200.0));
        assertTrue(iCreditCard.WithdrawFunds(200.0));
    }

    /**
     * Test sprawdzajcy podwojne dodanie pieniedzy do konta
     * Wymiar: Z + C
     */
    @Test
    public void test36() {
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        iCreditCard.Init("1234","1234");
        assertTrue(iCreditCard.DepositFunds(200.0));
        assertTrue(iCreditCard.DepositFunds(132.0));
    }

    /**
     * Test sprawdzajcy podwojna wplate i wyplate pieniedzy
     * Wymiar: Z + Z + C
     */
    @Test
    public void test37() {
        IAccount acc = new Account();
        iCreditCard.AddAccount(acc);
        iCreditCard.Init("1234","1234");
        assertTrue(iCreditCard.DepositFunds(200.0));
        assertTrue(iCreditCard.DepositFunds(132.0));
        assertTrue(iCreditCard.WithdrawFunds(23.0));
        assertTrue(iCreditCard.WithdrawFunds(123.0));
    }
}