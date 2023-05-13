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

    private double debit;

    public Account() {
        debit = 0.0;
    }

    @Override
    public double AccountStatus() {
        return debit;
    }

    @Override
    public double DepositFunds(double amount) {
        if(amount > 0.0)
        {
            debit += amount;
        }
        return AccountStatus();
    }

    @Override
    public double WithdrawFunds(double amount) {
        if(amount <= 0.0 || amount > AccountStatus())
        {
            return AccountStatus();
        }
        debit -= amount;
        return AccountStatus();
    }
}
