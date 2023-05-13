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

    private String pin = "0000";
    private IAccount account = null;

    public CreditCard() {

    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (newPin == null) throw new NullPointerException("newPin is null");
        if (newPinConfirm == null) throw new NullPointerException("newPinConfir is null");
        if (!newPin.equals(newPinConfirm)) throw new RuntimeException("newPin and newPinConfirm are different");
        if (newPin.length() != 4){
            throw new RuntimeException("pin must be 4 characters long");
        }
        if (!newPin.matches("\\d+")){
            throw new RuntimeException("pin must contain only digits");
        }
        this.pin = newPin;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (newPin == null) return false;
        if (newPinConfirm == null) return false;
        if (IsPinValid(oldPin) && newPin.equals(newPinConfirm) && newPin.matches("\\d{4}") ){


            this.pin = newPin;
            return true;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        if (pin == null){
            return false;
        }
        return this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account != null)
            throw new RuntimeException("a card already has a connected account");
        if (account == null) throw new NullPointerException("provided account is null");
        this.account = account;
    }

    @Override
    public IAccount GetAccount(){
        if (this.account == null) throw new RuntimeException("a card has no connected accounts");
        return this.account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (amount <= 0.0) return false;
        try {
            IAccount a = GetAccount();
            a.DepositFunds(amount);
            return true;
        } catch (Throwable ignored){}
        return false;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (amount <= 0.0) return false;
        try {
            IAccount a = GetAccount();
            if (amount > a.AccountStatus()) return false;
            a.WithdrawFunds(amount);
            return true;
        } catch (Throwable ignored){}
        return false;
    }
}
