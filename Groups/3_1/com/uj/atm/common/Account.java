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
    double balance;
    public Account() {
        this.balance = 0.0;
    }

    @Override
    public double AccountStatus() {
        return balance;
    }

    @Override
    public double DepositFunds(double amount) {
        this.balance += amount;
        return this.balance;
    }

    @Override
    public double WithdrawFunds(double amount) {
        try {
            if(this.balance < amount) {
                throw new Exception("Insufficient funds on the account.");
            }
            this.balance -= amount;
            return this.balance;
        } catch(Exception e) {
            System.out.println(e.toString());
            return this.balance;
        }

    }

}
