package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Data:
 *
 * Credit Card Pin
 * New pin
 * New Pin Confirm
 * Provided (Old) Pin
 *
 * Account balance
 *
 * Events:
 *
 * Change pin
 * Check pin (log in)
 *
 * Check account balance
 * Withdraw money
 * Deposit money
 * Transfer
 *
 * Time:
 *
 * Sequence
 *
 * Quantity:
 *
 * Exactly 4 symbols
 * Too many symbols
 * Not enough symbols
 *
 * Null (pin | card | account)
 *
 * Only numbers
 * Not a number symbols
 *
 * Big amount of money
 * Small amount of money
 * Negative amount of money
 *
 */

public class AtmTest {

    // Test operations on credit card pin

    /**
     D + D + E ((Uninitialized)CreditCard pin + Check pin)
     */
    @Test
    public void testLogInOnUninitializedCard() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();

        assertTrue(atm.CheckPinAndLogIn(creditCard, "0000"));
    }

    @RunWith(Parameterized.class)
    public static class TestCorrectPinValueActions {

        @Parameterized.Parameters(name="Test actions with pin = {0}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    {"0000"},
                    {"1111"},
                    {"1234"},
                    {"9999"},
                    {"4568"},
                    {"5648"},
                    {"9512"},
            });
        }

        private final String pinValue;

        public TestCorrectPinValueActions(String pinValue){
            this.pinValue = pinValue;
        }

        /**
         D + E (CreditCard pin + Check pin)
         */
        @Test
        public void testCheckCorrectPin() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            creditCard.Init(pinValue, pinValue);

            assertTrue(atm.CheckPinAndLogIn(creditCard, pinValue));
        }

        /**
         * D + D + E ((Uninitialized)CreditCard pin + New pin + New Pin Confirm + Change)
         */
        @Test
        public void testPinChangeWithCorrectPinOnUninitializedCard() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();

            assertTrue(atm.ChangePinCard(creditCard, "0000", pinValue, pinValue));
            // old pin is invalid or equal to new pin
            assertTrue(!creditCard.IsPinValid("0000") || pinValue.equals("0000"));
            assertTrue(creditCard.IsPinValid(pinValue));
        }

        /**
         * D + D + E (CreditCard pin + New pin + New Pin Confirm + Change)
         */
        @Test
        public void testPinChangeWithCorrectPin() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            creditCard.Init("1111", "1111");

            assertTrue(atm.ChangePinCard(creditCard, "1111", pinValue, pinValue));
            // old pin is invalid or equal to new pin
            assertTrue(!creditCard.IsPinValid("1111") || pinValue.equals("1111"));
            assertTrue(creditCard.IsPinValid(pinValue));
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
         D + D + E + Q ((Uninitialized)CreditCard pin + Check (wrong)pin)
         */
        @Test
        public void testCheckWrongPinOnUninitializedCard() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();

            assertFalse(atm.CheckPinAndLogIn(creditCard, pinValue));
        }

        /**
         D + D + E + Q (CreditCard pin + Check (wrong)pin)
         */
        @Test
        public void testCheckWrongPin() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            creditCard.Init("1234", "1234");

            assertFalse(atm.CheckPinAndLogIn(creditCard, pinValue));
        }

        /**
         * D + D + E + Q ((Uninitialized)CreditCard pin + (Wrong)New pin + New Pin Confirm + Change)
         */
        @Test
        public void testPinChangeWithWrongPinOnUninitializedCard() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();

            assertFalse(atm.ChangePinCard(creditCard, "0000", pinValue, pinValue));
            assertTrue(creditCard.IsPinValid("0000"));
            assertFalse(creditCard.IsPinValid(pinValue));
        }

        /**
         * D + D + E + Q (CreditCard pin + (Wrong)New pin + New Pin Confirm + Change)
         */
        @Test
        public void testPinChangeWithWrongPin() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            creditCard.Init("1111", "1111");

            assertFalse(atm.ChangePinCard(creditCard, "1111", pinValue, pinValue));
            assertTrue(creditCard.IsPinValid("1111"));
            assertFalse(creditCard.IsPinValid(pinValue));
        }
    }

    // Test operations on account balance

    /**
     D + E (Credit Card (with no account) + Deposit money)
     */
    @Test
    public void testDepositFundsOnCardWithNoAccount() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();

        assertFalse(atm.DepositFunds(creditCard, 1.0));
    }

    /**
     D + E (Credit Card (with no account) + Withdraw money)
     */
    @Test
    public void testWithdrawFundsOnCardWithNoAccount() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();

        assertFalse(atm.WithdrawFunds(creditCard, 1.0));
    }

    /**
     D + D + E (Credit Card + Account (with no enough money) + Withdraw money)
     */
    @Test
    public void testWithdrawFundsWhenNoEnoughMoneyOnClearCard() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);

        assertFalse(atm.WithdrawFunds(creditCard, 0.01));
        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    /**
     D + D + E (Credit Card + Account + Withdraw money)
     */
    @Test
    public void testWithdrawFundsWhenNoEnoughMoneyOnCard() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        account.DepositFunds(75.0);

        assertFalse(atm.WithdrawFunds(creditCard, 100.0));
        assertEquals(75.0, account.AccountStatus(), 0.0);
    }

    /**
     D + D + E (Credit Card + Account + Deposit money + negative amount)
     */
    @Test
    public void testDepositNegativeFunds() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        account.DepositFunds(100.0);

        assertFalse(atm.DepositFunds(creditCard, -1.0));
        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    /**
     D + D + E (Credit Card + Account + Withdraw money + negative amount)
     */
    @Test
    public void testWithdrawNegativeFunds() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        creditCard.AddAccount(account);
        account.DepositFunds(100.0);

        assertFalse(atm.WithdrawFunds(creditCard, -1.0));
        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    @RunWith(Parameterized.class)
    public static class TestFundsActions {

        @Parameterized.Parameters(name="Test funds operations_{index} [{0}]")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    {123}, {64.20}, {847.93}, {2153.12}, {12.99}, {0.0}, {0.01}, {2.03}, {1e10}, {224.6e9}
            });
        }

        private final double value;

        public TestFundsActions(double value){
            this.value = value;
        }

        /**
         D + E + Q (Account balance + Check Account + different amounts)
         */
        @Test
        public void testCheckAccountStatus() {
            IAtm atm = new Atm();
            IAccount account = new Account();
            account.DepositFunds(value);

            assertEquals(value, atm.AccountStatus(account), 0.0);
        }

        /**
         D + E + Q (Account balance + Deposit money + different amounts)
         */
        @Test
        public void testDepositFunds() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            IAccount account = new Account();
            creditCard.AddAccount(account);

            assertTrue(atm.DepositFunds(creditCard, value));
            assertEquals(value, account.AccountStatus(), 0.0);
        }

        /**
         D + E + Q (Account balance + Withdraw money + different amounts)
         */
        @Test
        public void testWithdrawAllFunds() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            IAccount account = new Account();
            creditCard.AddAccount(account);
            account.DepositFunds(value);

            assertTrue(atm.WithdrawFunds(creditCard, value));
            assertEquals(0.0, account.AccountStatus(), 0.0);
        }

        /**
         D + E + Q (Account balance + Withdraw money + different amounts)
         */
        @Test
        public void testWithdrawTooManyFunds() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            IAccount account = new Account();
            creditCard.AddAccount(account);
            account.DepositFunds(value);

            assertFalse(atm.WithdrawFunds(creditCard, BigDecimal.valueOf(value).add(BigDecimal.valueOf(0.01)).doubleValue()));
            assertEquals(value, account.AccountStatus(), 0.0);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestFundsActionsSeries {

        @Parameterized.Parameters(name = "Test funds series of operations_{index} [{0}]")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {Arrays.asList(45698.20, 52.40, 958.01, 1236.99, 4863.50)}, // normal
                    {Arrays.asList(99999.99, 12.00, 1.00, 98.12)}, // reasonably normal
                    {Arrays.asList(0.15, 0.01, 1.00, 0.98, 0.23)}, // small numbers
                    {Arrays.asList(3.5e10, 5.5e9, 2.8e14, 2.4e11)}, // big numbers
                    {Arrays.asList(1.2e10, 1.5e5, 0.01 ,1.90e6, 2.2e12, 3.1e10)} // test for numerical errors
            });
        }

        private final List<Double> values;

        public TestFundsActionsSeries(List<Double> values) {
            this.values = values;
        }

        /**
         * D + E + E + T + Q (Account balance + Deposit / Withdraw money + Series + different amounts + Check balance)
         */
        @Test
        public void testDepositFunds() {
            IAtm atm = new Atm();
            ICreditCard creditCard = new CreditCard();
            IAccount account = new Account();
            creditCard.AddAccount(account);

            BigDecimal balance = BigDecimal.ZERO;
            boolean isOperationValid;

            for (double val: values) {
                BigDecimal newBalance = balance.add(BigDecimal.valueOf(val));
                isOperationValid = (newBalance.compareTo(BigDecimal.ZERO) >= 0);
                if(isOperationValid){
                    balance = newBalance;
                }

                if (val >= 0){
                    assertTrue(atm.DepositFunds(creditCard, val));
                }else{
                    assertEquals(isOperationValid, atm.WithdrawFunds(creditCard, val));
                }
            }
            assertEquals(balance.doubleValue(), account.AccountStatus(), 0.0);
        }
    }

    // Test transfers

    /**
     D + D + E (Credit Card (with no account) + (recipient) Account + Transfer money)
     */
    @Test
    public void testTransferWithNoAccount() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();

        IAccount accountRecipient = new Account();
        accountRecipient.DepositFunds(25.0);

        assertFalse(atm.Transfer(creditCard, accountRecipient, 100.0));
        assertEquals(25.0, accountRecipient.AccountStatus(), 0.0);
    }

    /**
     D + D + E (Credit Card + (null recipient) Account + Transfer money)
     */
    @Test
    public void testTransferToNullRecipient() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(100.0);
        creditCard.AddAccount(account);

        try {
            assertFalse(atm.Transfer(creditCard, null, 10.0));
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        assertEquals(100.0, account.AccountStatus(), 0.0);
    }

    /**
     D + D + E (Credit Card (null) + (recipient) Account + Transfer money)
     */
    @Test
    public void testTransferFromNullCard() {
        IAtm atm = new Atm();

        IAccount accountRecipient = new Account();
        accountRecipient.DepositFunds(25.0);

        try {
            assertFalse(atm.Transfer(null, accountRecipient, 100.0));
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        assertEquals(25.0, accountRecipient.AccountStatus(), 0.0);
    }

    /**
     D + D + E (Credit Card + (recipient) Account + Transfer money)
     */
    @Test
    public void testTransferWithNoEnoughMoney() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(100.0);
        creditCard.AddAccount(account);
        //recipient
        IAccount accountRecipient = new Account();
        accountRecipient.DepositFunds(25.0);

        assertFalse(atm.Transfer(creditCard, accountRecipient, 125.0));
        assertEquals(100.0, account.AccountStatus(), 0.0);
        assertEquals(25.0, accountRecipient.AccountStatus(), 0.0);
    }

    /**
     D + D + E (Credit Card + (recipient) Account + Transfer (negative) money)
     */
    @Test
    public void testTransferNegativeMoney() {
        IAtm atm = new Atm();
        ICreditCard creditCard = new CreditCard();
        IAccount account = new Account();
        account.DepositFunds(100.0);
        creditCard.AddAccount(account);
        //recipient
        IAccount accountRecipient = new Account();
        accountRecipient.DepositFunds(25.0);

        assertFalse(atm.Transfer(creditCard, accountRecipient, -10.0));
        assertEquals(100.0, account.AccountStatus(), 0.0);
        assertEquals(25.0, accountRecipient.AccountStatus(), 0.0);
    }

    @RunWith(Parameterized.class)
    public static class TestTransfersSeries {

        @Parameterized.Parameters(name = "Test series of transactions_{index}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {95698.20, 0.0, Arrays.asList(45698.20, -52.40, 958.01, 1236.99, 4863.50)}, // normal transfers
                    {1000.20, 100.0, Arrays.asList(96.05, 1.59, 21.00, -70.91, 13.83, 24.22, 93.69, 41.82, 46.08, -58.01)}, // normal transfers
                    {2147483647.0, 0.0, Arrays.asList(1360071584.37, 442704044.79, -1197160220.44, 1142763803.47, -1140920914.47, 365995150.83, 1087072946.49)}, // big transfers
                    {100.0, 100.0, Arrays.asList(3.7, 1.35, -5.19, 6.64, 9.23, 4.14, 1.1, 7.59, -9.87, -2.76, 2.88, 3.03, -3.94)}, // small transfers
            });
        }

        private final double firstAccountBalance;
        private final double secondAccountBalance;
        private final List<Double> transactions;

        public TestTransfersSeries(double firstAccountBalance, double secondAccountBalance, List<Double> values) {
            this.firstAccountBalance = firstAccountBalance;
            this.secondAccountBalance = secondAccountBalance;
            this.transactions = values;
        }

        /**
         * D + E + E + T + Q (Account balance + Transfer + Series + different amounts + Check balance)
         */
        @Test
        public void testDepositFunds() {
            IAtm atm = new Atm();
            // first client
            ICreditCard firstCreditCard = new CreditCard();
            IAccount firstAccount = new Account();
            firstAccount.DepositFunds(firstAccountBalance);
            firstCreditCard.AddAccount(firstAccount);
            // second client
            ICreditCard secondCreditCard = new CreditCard();
            IAccount secondAccount = new Account();
            secondAccount.DepositFunds(secondAccountBalance);
            secondCreditCard.AddAccount(secondAccount);

            BigDecimal firstBalance = BigDecimal.valueOf(firstAccountBalance);
            BigDecimal secondBalance = BigDecimal.valueOf(secondAccountBalance);
            boolean isOperationValid;

            for (double val: transactions) {
                BigDecimal newDonorBalance = firstBalance.subtract(BigDecimal.valueOf(val));
                BigDecimal newRecipientBalance = secondBalance.add(BigDecimal.valueOf(val));
                isOperationValid = (newDonorBalance.compareTo(BigDecimal.ZERO) >= 0) &&
                                    (newRecipientBalance.compareTo(BigDecimal.ZERO) >= 0);
                if(isOperationValid){
                    firstBalance = newDonorBalance;
                    secondBalance = newRecipientBalance;
                }

                if (val >= 0){
                    assertEquals(isOperationValid, atm.Transfer(firstCreditCard, secondAccount, val));
                }else{
                    assertEquals(isOperationValid, atm.Transfer(secondCreditCard, firstAccount, Math.abs(val)));
                }
                assertEquals(firstBalance.doubleValue(), firstAccount.AccountStatus(), 0.0);
                assertEquals(secondBalance.doubleValue(), secondAccount.AccountStatus(), 0.0);
            }
        }
    }
}