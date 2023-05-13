package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.interfaces.IAccount;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Data:
 * Balance
 *
 *
 * Events:
 * Deposit
 * Withdraw
 * Check status
 *
 * Time:
 * Sequences of events
 *
 * Quantity:
 * "Normal" amount
 * Negative amount
 * Big amount
 * Small amount
 * Zero
 * Floating points over precision
 */

public class AccountTest {

    /**
     * D (Balance)
     */
    @Test
    public void testNewAccountBalanceIsZero() {
        IAccount account = new Account();
        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    @RunWith(Parameterized.class)
    public static class TestBalanceOnSingleAction {

        @Parameterized.Parameters(name = "Test funds operations_{index} [{0}]")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0.01}, {1.44}, {1.32}, {1.37}, {1.82}, // Near zero
                    {3.83}, {7.86}, {6.81}, {4.79}, {3.77}, {3.01}, {9.67}, {6.94}, {8.35}, {9.49}, // Small amounts
                    {403.75}, {950.26}, {517.79}, {559.6}, {638.54}, {231.27}, {275.47}, {840.89}, {441.55}, {27.73}, // Normal amounts
                    {105479818.88}, {480456574.27}, {1938871659.14}, {1648221568.49}, {633529020.99}, {25838202.97},
                    {1110637066.82}, {94431813.03}, {1017654685.12}, {789317717.45}, {1000_000_000.23} // Big amounts
            });
        }

        private final double value;

        public TestBalanceOnSingleAction(double value) {
            this.value = value;
        }

        /**
         * D + E + Q ((zero)Balance + Deposit + Check balance + different quantities)
         */
        @Test
        public void testNewAccountDeposit() {
            IAccount account = new Account();
            assertEquals(value, account.DepositFunds(value), 0.0);
            assertEquals(value, account.AccountStatus(), 0.0);
        }

        /**
         * D + E + Q ((zero)Balance + Withdraw + different quantities)
         */
        @Test
        public void testNewAccountWithdraw() {
            IAccount account = new Account();
            try{
                account.WithdrawFunds(value);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
            assertEquals(0.0, account.AccountStatus(), 0.0);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestBalanceOnActionsWithRelativeQuantities {

        @Parameterized.Parameters(name = "Test funds operations_{index} [{0}]")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    // pairs of (bigger amount, smaller amount)
                    {0.01, 0.00}, {0.02, 0.01}, // Near zero
                    {7.86, 3.83}, {6.81, 4.79}, {3.77, 3.01}, {9.67, 6.94}, {9.49, 8.35}, // Small amounts
                    {7673.63, 7380.9}, {9729.28, 5596.47}, {9119.75, 8302.32}, {8431.18, 3178.55}, {1046.9, 848.83}, // Normal amounts
                    {1755509053, 1708458999}, {2007469232, 1848685977}, {1781840062, 1309167640}, {2089820580, 1722251474},
                    {2125886947.04, 1191816213.23}, {1906928976.08, 1293385133.35}, {1686908963.52, 1336465501.44},
                    {1_000_000_000.23, 1_000_000_000.22} // Big amounts
            });
        }

        private final double bigger;
        private final double smaller;

        public TestBalanceOnActionsWithRelativeQuantities(double bigger, double smaller) {
            this.bigger = bigger;
            this.smaller = smaller;
        }

        /**
         * D + E + T + Q (Balance + Deposit + Withdraw + Check balance + Sequence + different quantities)
         */
        @Test
        public void testAccountDepositWithdraw() {
            IAccount account = new Account();
            double expected = BigDecimal.valueOf(bigger).subtract(BigDecimal.valueOf(smaller)).doubleValue();

            assertEquals(bigger, account.DepositFunds(bigger), 0.0);
            assertEquals(expected, account.WithdrawFunds(smaller), 0.0);
            assertEquals(expected, account.AccountStatus(), 0.0);
        }

        /**
         * D + E + T + Q (Balance + Deposit + Withdraw + Sequence + different quantities)
         */
        @Test
        public void testAccountDepositWithdrawBelowZero() {
            IAccount account = new Account();
            account.DepositFunds(smaller);
            try{
                account.WithdrawFunds(bigger);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
            assertEquals(smaller, account.AccountStatus(), 0.0);
        }

        /**
         * D + E + Q (Balance + Deposit + Negative amounts)
         */
        @Test
        public void testAccountDepositNegative() {
            IAccount account = new Account();
            account.DepositFunds(bigger);
            try {
                account.DepositFunds(-smaller);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
            assertEquals(bigger, account.AccountStatus(), 0.0);
        }

        /**
         * D + E + Q (Balance + Withdraw + Negative amount)
         */
        @Test
        public void testAccountWithdrawNegative() {
            IAccount account = new Account();
            account.DepositFunds(bigger);
            try {
                account.WithdrawFunds(-smaller);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
            assertEquals(bigger, account.AccountStatus(), 0.0);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestBalanceOnSeriesOfActions {

        @Parameterized.Parameters(name = "Test funds series of operations_{index} [{0}]")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {Arrays.asList(0.71, 0.23, 1.39, 1.99, 1.36, 1.76)}, // Small amounts
                    {Arrays.asList(21651.82, 87922.1, 62242.47, 6305.07, 31009.28, 88167.75, 6131.09, 63288.22)}, // Normal amounts
                    {Arrays.asList(211036695.73, 738473.66, 1286984568.7, 240168093.16, 2008835651.56, 769019417.9)}, // Big amounts
                    {Arrays.asList(1_000_000_000.23, 1_000_000_000.23, 1_000_000_000.23)} // Big amounts
            });
        }

        private final List<Double> values;

        public TestBalanceOnSeriesOfActions(List<Double> values) {
            this.values = values;
        }

        /**
         * D + E + Q + T (Balance + Deposit + different amounts + Sequence)
         */
        @Test
        public void testAccountMultipleDeposit() {
            IAccount account = new Account();
            BigDecimal sum = BigDecimal.ZERO;

            for (double val: values) {
                sum = sum.add(BigDecimal.valueOf(val));
                assertEquals(sum.doubleValue(), account.DepositFunds(val), 0.0);
            }
        }

        /**
         * D + E + E + Q + T (Balance + Deposit + Withdraw + different amounts + Sequence)
         */
        @Test
        public void testAccountMultipleDepositWithdrawMix() {
            IAccount account = new Account();
            BigDecimal balance = BigDecimal.ZERO;

            for (double val: values) {
                BigDecimal newBalance = balance.add(BigDecimal.valueOf(val));
                if(newBalance.compareTo(BigDecimal.ZERO) >= 0){
                    balance = newBalance;
                }

                try {
                    if (val >= 0) {
                        assertEquals(balance.doubleValue(), account.DepositFunds(val), 0.0);
                    } else {
                        assertEquals(balance.doubleValue(), account.WithdrawFunds(Math.abs(val)), 0.0);
                    }
                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
                }

                assertEquals(balance.doubleValue(), account.AccountStatus(), 0.0);
            }
        }
    }

    /**
     * D + E + Q (Balance + Deposit + Floating points over precision)
     */
    @Ignore("Undefined behaviour")
    @Test
    public void testAccountOverPrecision() {
        IAccount account = new Account();
        account.DepositFunds(120.23333);
        account.DepositFunds(120.26666);
        // Undefined behaviour
        assertEquals(240.49, account.AccountStatus(), 0.0);
    }

    /**
     * D + E + Q (Balance + Deposit + Zero)
     */
    @Test
    public void testAccountDepositZero() {
        IAccount account = new Account();
        assertEquals(0.0, account.DepositFunds(0.0), 0.0);
        assertEquals(0.0, account.AccountStatus(), 0.0);
    }

    /**
     * D + E + Q (Balance + Withdraw + Zero)
     */
    @Test
    public void testAccountWithdrawZero() {
        IAccount account = new Account();
        assertEquals(0.0, account.WithdrawFunds(0.0), 0.0);
        assertEquals(0.0, account.AccountStatus(), 0.0);    }
}