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
    private String cardPin;
    private IAccount account;
    public CreditCard() {
        this.cardPin = "0000";
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (cardPin.equals("0000") &&
                newPin != null &&
                newPin.equals(newPinConfirm) &&
                newPin.matches("\\d+") &&
                newPin.length() == 4 &&
                !newPin.equals("0000")) {
            this.cardPin = newPin;
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (!cardPin.equals("0000") &&
                newPin != null &&
                newPin.equals(newPinConfirm) &&
                newPin.matches("\\d+") &&
                newPin.length() == 4 &&
                !newPin.equals("0000") &&
                cardPin.equals(oldPin)) {
            this.cardPin = newPin;
            return true;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return pin != null &&
                pin.matches("\\d+") &&
                pin.length() == 4 &&
                cardPin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account == null) {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (account != null &&
                !cardPin.equals("0000")) {
            account.DepositFunds(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (account != null &&
                !cardPin.equals("0000") &&
                account.AccountStatus() >= amount) {
            account.WithdrawFunds(amount);
            return true;
        }
        return false;
    }
}
