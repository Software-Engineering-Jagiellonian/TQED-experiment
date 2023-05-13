package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class CreditCard implements ICreditCard {
    IAccount account;
    String pin;

    public CreditCard() {
        this.account = null;
        this.pin = null;

    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (pin != null){
            throw new IllegalStateException("Cannot init multiple times");
        } else if (newPin == null || newPinConfirm == null) {
            throw new IllegalArgumentException("New pin cannot be null");
        } else if (!newPin.equals(newPinConfirm)){
            throw new IllegalArgumentException("Pin and confirm pin must match");
        } else if (!isPinCorrect(newPin)){
            throw new IllegalArgumentException("Pin does not meet all criteria");
        }
        pin = newPin;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (pin == null || newPin == null || !pin.equals(oldPin) || !newPin.equals(newPinConfirm) || !isPinCorrect(newPin)) {
            return false;
        }
        pin = newPin;
        return true;

    }

    @Override
    public boolean IsPinValid(String pin) {
        if (this.pin == null || pin == null ){
            return false;
        }
        return this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account != null) {
            throw new IllegalStateException("Account already added");
        } else if (pin == null) {
            throw new IllegalStateException("Card not initialised");
        } else if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        this.account = account;
    }

    @Override
    public IAccount GetAccount(){
       return this.account;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if (this.account == null || this.pin == null) {
            return false;
        }
        try {
            account.DepositFunds(amount);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (this.account == null || this.pin == null) {
            return false;
        }
        try {
            account.WithdrawFunds(amount);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }


    private boolean isPinCorrect(String newPin) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(newPin);
        return matcher.matches();
    }
}
