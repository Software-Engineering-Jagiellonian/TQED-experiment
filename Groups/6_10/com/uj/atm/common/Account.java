package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;

/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class Account implements IAccount {

    private double accountStatus;

    public Account() {

    }

    @Override
    public double AccountStatus() {
        return accountStatus;
    }

    @Override
    public double DepositFunds(double amount) {
        if (amount > 0) {
            accountStatus += amount;
        }
        return accountStatus;
    }

    @Override
    public double WithdrawFunds(double amount) {
        if (accountStatus > amount) {
            accountStatus -= amount;
        } else {
            accountStatus = 0;
        }
        return accountStatus;
    }

}
