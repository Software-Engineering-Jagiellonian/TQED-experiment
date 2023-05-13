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

    private String PIN;
    private IAccount account;

    public CreditCard() {
        PIN = null;
        account = null;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (PIN != null) return;
        if (!newPin.equals(newPinConfirm)) return;
        if (!newPin.matches("[0-9]+")) return;
        if (newPin.length() != 4) return;
        PIN = newPin;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (PIN == null) return false;
        if (!PIN.equals(oldPin)) return false;
        if (!newPin.equals(newPinConfirm)) return false;
        if (!newPin.matches("[0-9]+")) return false;
        if (newPin.length() != 4) return false;
        PIN = newPin;
        return true;
    }

    @Override
    public boolean IsPinValid(String pin) {
        if (PIN == null) return false;
        return PIN.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account != null) return;
        this.account = account;
    }

    @Override
    public IAccount GetAccount(){
        return this.account;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if (amount <= 0.0) return false;
        if (this.account == null) return false;
        this.account.DepositFunds(amount);
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (amount <= 0.0) return false;
        if (this.account == null) return false;
        if (this.account.AccountStatus() < amount) return false;
        this.account.WithdrawFunds(amount);
        return true;
    }
}
