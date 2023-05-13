package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.Math.abs;

/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class Account implements IAccount {
    private BigInteger balance;
    private final BigDecimal precision;

    public Account() {
        balance = BigInteger.ZERO;
        precision = BigDecimal.valueOf(100.0);
    }

    @Override
    public double AccountStatus() {
        return new BigDecimal(balance).divide(precision).doubleValue();
    }

    @Override
    public double DepositFunds(double amount) {
        try {
            BigInteger actualAmount = BigDecimal.valueOf(amount).multiply(precision).toBigInteger();
            if (actualAmount.compareTo(BigInteger.ZERO) > 0) {
                balance = balance.add(actualAmount);
            }
        } catch (NumberFormatException ignored) {}

        return AccountStatus();
    }

    @Override
    public double WithdrawFunds(double amount) {
        try {
            BigInteger actualAmount = BigDecimal.valueOf(amount).multiply(precision).toBigInteger();
            if (actualAmount.compareTo(BigInteger.ZERO) > 0 && balance.compareTo(actualAmount) >= 0) {
                balance = balance.subtract(actualAmount);
            }
        } catch (NumberFormatException ignored) {}

        return AccountStatus();
    }

}
