package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class AccountTest {
    
    private final Account sut = new Account();
    
    // D + Z
	@Test
    public void accountStatus_noFunds_returnsZero() {
        double actual = sut.AccountStatus();
        
        assertDoubleEquals(actual, 0);
    }

    // D + Z
    @Test
    public void accountStatus_positiveFunds_returnsPositive() {
        sut.DepositFunds(123D);
        
        double actual = sut.AccountStatus();
        
        assertDoubleEquals(actual, 123D);
    }

    // D + Z
    @Test
    public void accountStatus_negativeFunds_returnsNegative() {
        sut.WithdrawFunds(100);
        
        double actual = sut.AccountStatus();
        
        assertDoubleEquals(actual, -100D);
    }

    // D + Z
    @Test
    public void DepositFunds_zeroAmount_exception() {
        assertThrows(
                IllegalArgumentException.class,
                () -> sut.DepositFunds(0D));
    }

    // D + Z
    @Test
    public void DepositFunds_positiveAmountNoPreviousDeposits_deposited() {
        sut.DepositFunds(123D);
        
        double actual = sut.AccountStatus();
        
        assertDoubleEquals(actual, 123D);
    }

    // D + Z + I
    @Test
    public void DepositFunds_positiveAmountMultipleDeposits_sumDeposited() {
        sut.DepositFunds(123D);
        sut.DepositFunds(234D);
        sut.DepositFunds(345D);
        
        double actual = sut.AccountStatus();
        
        assertDoubleEquals(actual, 123D + 234D + 345D);
    }

    // D + Z
    @Test
    public void DepositFunds_negativeAmount_exception() {
        assertThrows(
                IllegalArgumentException.class, 
                () -> sut.DepositFunds(-123D));
    }

    // D + Z
    @Test
    public void WithdrawFunds_zeroAmount_exception() {
        assertThrows(
                IllegalArgumentException.class,
                () -> sut.WithdrawFunds(0D));
    }

    // D + Z
    @Test
    public void WithdrawFunds_positiveAmountNoPreviousDeposits_negativeAccountState() {
        sut.WithdrawFunds(123D);
        
        double actual = sut.AccountStatus();
        
        assertDoubleEquals(actual, -123D);
    }

    // D + Z + I
    @Test
    public void WithdrawFunds_positiveAmountMultipleDeposits_amountWithdrawn() {
        sut.DepositFunds(123D);
        sut.DepositFunds(234D);
        sut.WithdrawFunds(456D);
        
        double actual = sut.AccountStatus();
        
        assertDoubleEquals(actual, 123D + 234D - 456D);
    }

    // D + Z
    @Test
    public void WithdrawFunds_negativeAmount_exception() {
        assertThrows(
                IllegalArgumentException.class,
                () -> sut.WithdrawFunds(-123D));
    }
    
    private static void assertDoubleEquals(double actual, double expected) {
        assertEquals(actual, expected, 0.001);
    }
}