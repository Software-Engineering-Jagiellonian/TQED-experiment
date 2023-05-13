package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


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
        pin = "0000";
        account = null;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (!newPin.equals(newPinConfirm)) {
            throw new IllegalArgumentException("newPin and newPinConfirm have to be equal");
        }

        if (newPin.length() != 4) {
            throw new IllegalArgumentException("newPin is not valid");
        }
        for (int i = 0; i < newPin.length(); i++) {
            if (newPin.charAt(i) < '0' || newPin.charAt(i) > '9') {
                throw new IllegalArgumentException("newPin is not valid");
            }
        }

        pin = newPin;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (!newPin.equals(newPinConfirm)) {
            System.out.println("newPin and newPinConfirm have to be equal");
            return false;
        }
        if (!oldPin.equals(pin)) {
            System.out.println("Incorrect oldPin");
            return false;
        }

        if (newPin.length() != 4) {
            return false;
        }
        for (int i = 0; i < newPin.length(); i++) {
            if (newPin.charAt(i) < '0' || newPin.charAt(i) > '9') {
                return false;
            }
        }

        pin = newPin;
        return true;
    }

    @Override
    public boolean IsPinValid(String pin) {
        if (pin.length() != 4) {
            return false;
        }
        for (int i = 0; i < pin.length(); i++) {
            if (pin.charAt(i) < '0' || pin.charAt(i) > '9') {
                return false;
            }
        }

        return this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account != null) {
            try {
                throw new IllegalAccessException("This card already have an account connected");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (account == null) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }

        account.DepositFunds(amount);
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (account == null) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        if (account.AccountStatus() < amount) {
            return false;
        }

        account.WithdrawFunds(amount);
        return true;
    }
}
