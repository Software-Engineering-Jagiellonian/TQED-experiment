package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;

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

    private BigDecimal balance;

    public Account() {
        this.balance = new BigDecimal("0.0");
    }

    @Override
    public double AccountStatus() {
        return this.balance.doubleValue();
    }

    @Override
    public double DepositFunds(double amount) {
        if (amount > 0) {
            this.balance = this.balance.add(new BigDecimal(amount));
        }

        return this.balance.doubleValue();
    }

    @Override
    public double WithdrawFunds(double amount) {
        if (amount > 0) {
            final BigDecimal amountAsBigDecimal = new BigDecimal(amount);

            if (this.balance.compareTo(amountAsBigDecimal) >= 0) {
                this.balance = this.balance.subtract(amountAsBigDecimal);
            }
        }

        return this.balance.doubleValue();
    }

}
