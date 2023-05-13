package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Data:
 * Credit Card Pin
 * New pin
 * New Pin Confirm
 * Provided (Old) Pin
 *
 * Account
 * Account balance
 *
 * Events:
 * Initialize pin
 * Change pin
 * Validate pin
 *
 * Add Account
 * Get Account
 *
 * Time:
 * Sequence
 *
 * Quantity:
 *
 * Exactly 4 symbols
 * Too many symbols
 * Not enough symbols
 *
 * Null
 *
 * Only numbers
 * Not a number symbols
 *
 */

public class CreditCardTest {

    // Test actions with pin

    /**
     * D + E ((default) Credit Card Pin + (not) Initialize + Validate pin)
     */
    @Test
    public void testValidateDefaultPinWithoutInitialization() {
        ICreditCard creditCard = new CreditCard();
        assertTrue(creditCard.IsPinValid("0000"));
    }

    /**
     * D + E ((default) Credit Card Pin + Initialize + Validate pin)
     */
    @Test
    public void testInitializePinToDefault() {
        ICreditCard creditCard = new CreditCard();

        creditCard.Init("0000", "0000");
        assertTrue(creditCard.IsPinValid("0000"));
    }

    /**
     * D + D + E ((default) Credit Card Pin + (not) Initialize + Change pin + Validate pin)
     */
    @Test
    public void testChangePinToDefaultWithoutInitialization() {
        ICreditCard creditCard = new CreditCard();

        assertTrue(creditCard.ChangePin("0000", "0000", "0000"));
        assertTrue(creditCard.IsPinValid("0000"));
    }

    /**
     * D + D + E + T (New pin + New Pin Confirm + Initialize + Validate pin + Sequence)
     */
    @Test
    public void testMultipleInitializations() {
        ICreditCard creditCard = new CreditCard();
        creditCard.Init("2791", "2791");
        assertTrue(creditCard.IsPinValid("2791"));
        creditCard.Init("2439", "2439");
        assertTrue(creditCard.IsPinValid("2439"));
        creditCard.Init("9465", "9465");
        assertTrue(creditCard.IsPinValid("9465"));
        creditCard.Init("1161", "1161");
        assertTrue(creditCard.IsPinValid("1161"));
    }

    /**
     * D + D + E (New pin + New Pin Confirm + Initialize + Validate pin)
     */
    @Test
    public void testPinInitializationNewPinConfirmNotMatched() {
        ICreditCard creditCard = new CreditCard();
        try{
            creditCard.Init("1111", "1112");
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        assertFalse(creditCard.IsPinValid("1111"));
        assertTrue(creditCard.IsPinValid("0000"));
    }

    @RunWith(Parameterized.class)
    public static class TestCorrectPinValueActions {

        @Parameterized.Parameters(name="Test actions with pin = {0}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    {"8059"}, {"8448"}, {"1329"}, {"7033"}, {"4610"}, {"2916"}, {"1935"}, {"2695"}, {"3114"}, {"4925"},
                    {"5769"}, {"6897"}, {"7638"}, {"1741"}, {"3115"}, {"4566"}, {"2372"}, {"9103"}, {"7303"}, {"3795"},
                    {"2099"}, {"5604"}, {"4956"}, {"2342"}, {"2696"}, {"9953"}, {"2172"}, {"1598"}, {"2103"}, {"6770"},
                    {"3101"}, {"1035"}, {"5485"}, {"1041"}, {"8096"}, {"7296"}, {"4337"}, {"7883"}, {"4475"}, {"3545"},
            });
        }

        private final String pinValue;

        public TestCorrectPinValueActions(String pinValue){
            this.pinValue = pinValue;
        }

        /**
         * D + E ((default) Credit Card Pin + (not) Initialize + Validate (not default)pin)
         */
        @Test
        public void testValidateNotDefaultPinWithoutInitialization() {
            ICreditCard creditCard = new CreditCard();
            assertFalse(creditCard.IsPinValid(pinValue));
        }

        /**
         * D + D + E (New pin + New Pin Confirm + (not) Initialize + Validate pin)
         */
        @Test
        public void testChangePinWithoutInitialization() {
            ICreditCard creditCard = new CreditCard();

            assertTrue(creditCard.ChangePin("0000", pinValue, pinValue));
            assertTrue(creditCard.IsPinValid(pinValue));
            assertFalse(creditCard.IsPinValid("0000"));
        }

        /**
         * D + D + E (New pin + New Pin Confirm + Initialize + Validate pin)
         */
        @Test
        public void testPinInitializationProper() {
            ICreditCard creditCard = new CreditCard();

            creditCard.Init(pinValue, pinValue);

            assertTrue(creditCard.IsPinValid(pinValue));
            assertFalse(creditCard.IsPinValid("0000"));
        }

        /**
         * D + D + E + T ((Wrong)New pin + New Pin Confirm + Initialize + Change + Validate pin)
         */
        @Test
        public void testPinChange() {
            ICreditCard creditCard = new CreditCard();
            creditCard.Init("1111", "1111");

            creditCard.ChangePin("1111", pinValue, pinValue);

            assertTrue(creditCard.IsPinValid(pinValue));
            assertFalse(creditCard.IsPinValid("1111"));
        }
    }

    @RunWith(Parameterized.class)
    public static class TestWrongPinValueActions {

        @Parameterized.Parameters(name="Test actions with pin = {0}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    {null}, // null string
                    {""}, // empty string
                    {"ABCD"}, // Not a number (all)
                    {"14A4"}, // NotA number (some)
                    {"123"}, // Too short
                    {"12345"}, // Too long
                    {String.join("", Collections.nCopies(100, "9"))}, // Really too long
                    {"ąćęż"}, // latin characters
                    {"ùöğø"}, // special characters
                    {"ęśąćż"}, // mBank special test
                    {"\uD83D\uDE00\uD83D\uDE03\uD83D\uDE13\uD83D\uDE2D"} // emoji unicode
            });
        }

        private final String pinValue;

        public TestWrongPinValueActions(String pinValue){
            this.pinValue = pinValue;
        }

        /**
         * D + E + Q ((default) Credit Card Pin + (not) Initialize + Validate (Wrong)pin)
         */
        @Test
        public void testValidateWrongPinWithoutInitialization() {
            ICreditCard creditCard = new CreditCard();
            assertFalse(creditCard.IsPinValid(pinValue));
        }

        /**
         * D + D + E + Q ((Wrong)New pin + New Pin Confirm + Initialize + Validate pin)
         */
        @Test
        public void testPinInitializationWithWrongPin() {
            ICreditCard creditCard = new CreditCard();

            try {
                creditCard.Init(pinValue, pinValue);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

            assertTrue(creditCard.IsPinValid("0000"));
            assertFalse(creditCard.IsPinValid(pinValue));
        }

        /**
         * D + D + E + T + Q ((Wrong)New pin + New Pin Confirm + Initialize + Change + Validate pin)
         */
        @Test
        public void testPinChangeWithWrongPin() {
            ICreditCard creditCard = new CreditCard();
            creditCard.Init("1111", "1111");

            try {
                creditCard.ChangePin("1111", pinValue, pinValue);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

            assertTrue(creditCard.IsPinValid("1111"));
            assertFalse(creditCard.IsPinValid(pinValue));
        }
    }

    // Test actions with account

    /**
     * D + E (Account + (not)AddAccount + GetAccount)
     */
    @Test
    @Ignore("Getting account before adding it is undefined")
    public void testGetAccountWithoutAdding() {
        ICreditCard creditCard = new CreditCard();

        IAccount obj = null;
        try {
            obj = creditCard.GetAccount();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        // may be some optional object?
        assertNull(obj);
    }

    /**
     * D + E (Account + AddAccount + GetAccount)
     */
    @Test
    public void testAddAndGetAccount() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();

        creditCard.AddAccount(account);
        IAccount result = creditCard.GetAccount();

        assertEquals(account, result);
    }

    /**
     * D + E + T (Account + AddAccount + AddAccount (different) + GetAccount + Sequence)
     */
    @Test
    public void testAddTwoDifferentAccountsAndGet() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        IAccount other = new Account();

        try {
            creditCard.AddAccount(account);
            creditCard.AddAccount(other);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        IAccount result = creditCard.GetAccount();

        assertEquals(account, result);
    }

    /**
     * D + E + T (Account + AddAccount + GetAccount + Sequence)
     */
    @Test
    public void testMultipleAddAndGetAccount() {
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();

        for(int i = 0; i < 5; ++i){
            try {
                creditCard.AddAccount(account);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
        IAccount result = creditCard.GetAccount();

        assertEquals(account, result);
    }

    // Test actions on account balance

    /**
     D + E ( (No) Account added + Deposit money)
     */
    @Test
    public void testDepositFundsWithNoAccount() {
        ICreditCard creditCard = new CreditCard();

        assertFalse(creditCard.DepositFunds(100.0));
    }

    @RunWith(Parameterized.class)
    public static class TestFundsActions {

        @Parameterized.Parameters(name="Test funds operations_{index} [{0}]")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    {0.0}, {0.01}, // Near zero
                    {7.95}, {1.21}, {6.01}, {5.32}, {2.07}, // small amounts
                    {492.48}, {899.1}, {617.87}, {813.86}, {645.65}, // medium amounts
                    {1465876071.4}, {918218698.42}, {756016326.95}, {1349519703.58}, {1512912471.5}, {224.6e9} // Big amounts
            });
        }

        private final double value;

        public TestFundsActions(double value){
            this.value = value;
        }

        /**
         D + E + E + Q (Account balance + Add account + Deposit money + different amounts)
         */
        @Test
        public void testDepositFunds() {
            ICreditCard creditCard = new CreditCard();
            IAccount account = new Account();

            creditCard.AddAccount(account);
            assertTrue(creditCard.DepositFunds(value));
            assertEquals(value, account.AccountStatus(), 0.0);
        }

        /**
         D + E + E + Q (Account balance + Add account + Withdraw money + different amounts)
         */
        @Test
        public void testWithdrawAllFunds() {
            ICreditCard creditCard = new CreditCard();
            IAccount account = new Account();
            account.DepositFunds(value);

            creditCard.AddAccount(account);
            assertTrue(creditCard.WithdrawFunds(value));
            assertEquals(0.0, account.AccountStatus(), 0.0);
        }

        /**
         D + E + E + Q (Account balance + Add account + Withdraw money + different amounts)
         */
        @Test
        public void testWithdrawTooManyFunds() {
            ICreditCard creditCard = new CreditCard();
            IAccount account = new Account();
            account.DepositFunds(value);

            assertFalse(creditCard.WithdrawFunds(BigDecimal.valueOf(value).add(BigDecimal.valueOf(0.01)).doubleValue()));
            assertEquals(value, account.AccountStatus(), 0.0);
        }
    }


}
