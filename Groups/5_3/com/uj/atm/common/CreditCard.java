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
    private short pinValue;
    private boolean initialized;
    private IAccount linkedAccount;

    public CreditCard() {
        pinValue = 0;
        linkedAccount = null;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (initialized) return;

        if (newPin.length() != 4) return;

        if (!newPin.equals(newPinConfirm)) return;

        for (char ch: newPin.toCharArray()) {
            if (ch < '0' || ch > '9') return;
        }

        try {
            pinValue = Short.parseShort(newPin);
            initialized = true;
        } catch (NumberFormatException ignored) {}
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (newPin.length() != 4) return false;

        if (!newPin.equals(newPinConfirm)) return false;

        for (char ch: newPin.toCharArray()) {
            if (ch < '0' || ch > '9') return false;
        }

        try {
            if (!IsPinValid(oldPin)) return false;

            pinValue = Short.parseShort(newPin);
            initialized = true;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean IsPinValid(String pin) {
        if (pin == null) return false;

        if (pin.length() != 4) return false;

        try {
            return Short.parseShort(pin) == pinValue;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void AddAccount(IAccount account) {
        if (linkedAccount == null) {
            linkedAccount = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return linkedAccount;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if (linkedAccount == null) return false;

        double oldBalance = linkedAccount.AccountStatus();
        linkedAccount.DepositFunds(amount);
        return linkedAccount.AccountStatus() != oldBalance;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (linkedAccount == null) return false;

        double oldBalance = linkedAccount.AccountStatus();
        linkedAccount.WithdrawFunds(amount);
        return linkedAccount.AccountStatus() != oldBalance;
    }
}
