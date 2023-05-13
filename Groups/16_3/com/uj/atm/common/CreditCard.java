package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private String pin = "0000";
    private IAccount account = null;
    private final Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public CreditCard() {

    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if(newPin != null && newPin.length() == 4 && numberPattern.matcher(newPin).matches() && newPin.equals(newPinConfirm)) {
            this.pin = newPin;
        } else {
            System.out.println("Nie udalo sie zmienic pinu");
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if(this.pin.equals(oldPin) && newPin != null && newPin.length() == 4 && numberPattern.matcher(newPin).matches() && newPin.equals(newPinConfirm)) {
            this.pin = newPin;
            return true;
        } else {
            return  false;
        }
    }

    @Override
    public boolean IsPinValid(String pin) {
        return pin != null && pin.length() == 4 && numberPattern.matcher(pin).matches() && this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if(account != null) {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return this.account;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if(this.account != null) {
            this.account.DepositFunds(amount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if(this.account != null && this.account.AccountStatus() > amount) {
            this.account.WithdrawFunds(amount);
            return true;
        } else {
            return false;
        }
    }
}
