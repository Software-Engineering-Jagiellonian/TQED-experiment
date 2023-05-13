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
    private double balance;
    
    public Account() {
        this.balance = 0.0;

    }

    @Override
    public double AccountStatus() {
        return balance;
    }

    @Override
    public double DepositFunds(double amount) {
        Double amount_ = amount*100;
        amount_ = amount_ - amount_.intValue();
        if (amount <= 0 ) {
            throw new IllegalArgumentException("The amount must be grater than 0");
        } else if (amount_.compareTo(0D) > 0) {
            throw new IllegalArgumentException("The amount can have a maximum of 2 digits after the decimal point");
        }
        balance += amount;
        return balance;
    }

    @Override
    public double WithdrawFunds(double amount) {
        Double amount_ = amount*100;
        amount_ = amount_ - amount_.intValue();
        if (amount <= 0 ) {
            throw new IllegalArgumentException("The amount must be grater than 0");
        } else if (amount_.compareTo(0D) > 0) {
            throw new IllegalArgumentException("The amount can have a maximum of 2 digits after the decimal point");
        } else if (amount > balance) {
            throw new IllegalArgumentException("Not enough money in the account");
        }
        balance -= amount;
        return balance;
    }

}
