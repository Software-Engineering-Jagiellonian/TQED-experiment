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
        if(newPin == null){
            throw new RuntimeException("Pin cannot be null");
        }
        if(newPin.length() != 4){
            throw new RuntimeException("Invalid length of pin");
        }
        try{
            Integer.parseInt(newPin);
        }catch (NumberFormatException e){
            throw new RuntimeException("Pin should contain only numbers");
        }
        if(!newPin.equals(newPinConfirm)){
            throw new RuntimeException("Pin does not match with its confirmation");
        }

        pin = newPin;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if(!pin.equals(oldPin)){
            return false;
        }
        try{
            Init(newPin, newPinConfirm);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if(this.account != null){
            throw new RuntimeException("Account is already set");
        }
        this.account = account;
    }

    @Override
    public IAccount GetAccount(){
        return account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if(account == null){
            return false;
        }
        try {
            account.DepositFunds(amount);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if(account == null){
            return false;
        }
        try {
            account.WithdrawFunds(amount);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
}
