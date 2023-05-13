package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.management.ValueExp;

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

    private double acc_balance;
    
    public Account() {
        this.acc_balance = 0.;
    }

    @Override
    public double AccountStatus() {
        return acc_balance;
        //throw new NotImplementedException();
    }

    @Override
    public double DepositFunds(double amount) {
        if(amount > 0 )
        {
            acc_balance += amount;
        }
        else
        {
            throw new IllegalArgumentException("Amount can not be negative");
        }
        return acc_balance;
    }

    @Override
    public double WithdrawFunds(double amount) {
        if(amount > 0 && acc_balance - amount >= 0)
        {
            acc_balance -= amount;
        }
        else
        {
            throw new IllegalArgumentException("Amount can not be negative");
        }
        return acc_balance;
    }

}
