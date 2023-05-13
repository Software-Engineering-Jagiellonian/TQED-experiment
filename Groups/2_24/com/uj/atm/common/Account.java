package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
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
    private double funds;

    public Account() {
        this.funds = 0.0F;
    }

    @Override
    public double AccountStatus() {
        return this.funds;
    }

    @Override
    public double DepositFunds(double amount) {
        this.funds += amount;
        return this.funds;
    }

    @Override
    public double WithdrawFunds(double amount) {
        this.funds -= amount;
        return this.funds;
    }

}
