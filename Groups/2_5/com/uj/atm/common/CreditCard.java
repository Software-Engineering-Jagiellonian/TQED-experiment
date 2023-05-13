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
        this.pin = "0000";
        this.account = null;
    }

    private boolean IsPinInConvention(String pin) {
        return pin.chars().allMatch(Character::isDigit) && pin.length() == 4;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (this.pin == "0000") {
            if (newPin == newPinConfirm && this.IsPinInConvention(newPin)) {
                this.pin = newPin;
            }
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        boolean success = (this.pin == oldPin) && (newPin == newPinConfirm) && this.IsPinInConvention(newPin);
        if (success) {
            this.pin = newPin;
        }
        return success;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return this.pin == pin;
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account == null) {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return this.account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (this.account != null) {
            this.account.DepositFunds(amount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (this.account != null && this.account.AccountStatus() >= amount) {
            this.account.WithdrawFunds(amount);
            return true;
        } else {
            return false;
        }
    }
}
