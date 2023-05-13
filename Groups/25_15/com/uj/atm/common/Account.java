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

    private BigDecimal balance = BigDecimal.ZERO;

    public Account() {

    }

    @Override
    public double AccountStatus() {
        return balance.doubleValue();
    }

    @Override
    public double DepositFunds(double amount) {
        if (amount < 0){
            throw new RuntimeException("Invalid amount of money to deposit");
        }
        balance = balance.add(BigDecimal.valueOf(amount));
        return balance.doubleValue();
    }

    @Override
    public double WithdrawFunds(double amount) {
        if (amount < 0){
            throw new RuntimeException("Invalid amount of money to withdraw");
        }
        BigDecimal newBalance = balance.subtract(BigDecimal.valueOf(amount));
        if (newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new RuntimeException("Not enough money on the account");
        }
        balance = newBalance;
        return balance.doubleValue();
    }

}
