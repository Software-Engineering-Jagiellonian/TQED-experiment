package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;


/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class CreditCard implements ICreditCard {

    private String pin;
    private IAccount account;

    public CreditCard() {

    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (newPin.length() == 4 && newPin.equals(newPinConfirm) && Pattern.matches("\\d{4}", newPin)) {
            pin = newPin;
        } else {
            throw new IllegalArgumentException("New pin must be four digits long and confirmed with the same sequence");
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        boolean pinChanged = false;
        boolean noneIsNull = !isNull(oldPin) && !isNull(newPin) && !isNull(newPinConfirm);
        if (noneIsNull && oldPin.equals(pin) && newPin.length() == 4 &&
            newPin.equals(newPinConfirm) && Pattern.matches("\\d{4}", newPin)) {
            pin = newPin;
            pinChanged = true;
        }
        return pinChanged;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return !isNull(pin) && pin.equals(this.pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (isNull(this.account)) {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        boolean depositMade = false;
        if (!isNull(account) && amount > 0) {
            double before = account.AccountStatus();
            double after = account.DepositFunds(amount);
            double result = after - before;
            depositMade = result == amount;
        }
        return depositMade;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        boolean withdrawed = false;
        if (!isNull(account) && amount > 0) {
            double before = account.AccountStatus();
            double after = account.WithdrawFunds(amount);
            double result = before - after;
            withdrawed = amount >= before ? after == 0d : result == amount;
        }
        return withdrawed;
    }
}
