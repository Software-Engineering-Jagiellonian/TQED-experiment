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
    
    private BigDecimal currentBalance;
    
    public Account() {
        this.currentBalance = BigDecimal.ZERO;
    }

    @Override
    public double AccountStatus() {
        return currentBalance.doubleValue();
    }

    @Override
    public double DepositFunds(double amount) {
        if (amount <= 0.) {
            throw new IllegalArgumentException("Only amounts greater than 0 can be deposited.");
        }
        currentBalance = currentBalance.add(BigDecimal.valueOf(amount));
        
        return AccountStatus();
    }

    @Override
    public double WithdrawFunds(double amount) {
        if (amount <= 0.) {
            throw new IllegalArgumentException("Only amounts greater than zero 0 can be withdrawn.");
        }
        currentBalance = currentBalance.subtract(BigDecimal.valueOf(amount));
        
        return AccountStatus();
    }

}
