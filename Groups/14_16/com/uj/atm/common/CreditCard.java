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
        account = null;
        pin = "0000";
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (newPin == null){
            return;
        }
        if (newPin.length() != 4 || !newPin.matches("[0-9][0-9][0-9][0-9]")){
            return;
        }
        if (newPin.equals(newPinConfirm))
            this.pin = newPin;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (newPin == null){
            return false;
        }
        if (newPin.length() != 4 || !newPin.matches("[0-9][0-9][0-9][0-9]")){
            return false;
        }
        if (!this.IsPinValid(oldPin) || !newPin.equals(newPinConfirm))
            return false;
        this.pin = newPin;
        return true;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account != null)
            return;
        this.account = account;
    }

    @Override
    public IAccount GetAccount(){
        return this.account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if(this.account == null)
            return false;
        this.account.DepositFunds(amount);
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if(this.account == null)
            return false;
        this.account.WithdrawFunds(amount);
        return true;
    }
}
