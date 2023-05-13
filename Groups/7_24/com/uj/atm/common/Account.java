package com.uj.atm.common;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uj.atm.interfaces.IAccount;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.DecimalFormat;

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
    
    public Account() {
        currentAmount = 0.0;
    }

    private double currentAmount;


    @Override
    public double AccountStatus() {
        return currentAmount;
    }

    @Override
    public double DepositFunds(double amount) {
        if(amount < 0)
            throw new IllegalArgumentException("amount should be bigger or equal 0");

        currentAmount += amount;
        return currentAmount;
    }

    @Override
    public double WithdrawFunds(double amount) {
        if(amount < 0)
            throw new IllegalArgumentException("amount should be bigger or equal 0");

        if(amount>currentAmount)
            throw new IllegalArgumentException("amount should be smaller or equal account state");

        currentAmount -= amount;
        return currentAmount;
    }

}
