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

    private String pinAccount;
    private IAccount account_obj;
    public CreditCard() {
        this.pinAccount = "0000";
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (newPin.matches("\\d+") && newPin.length() == 4){
            if (newPin.equals(newPinConfirm)) {
                pinAccount = newPin;
            } else {
                throw new IllegalArgumentException("Incorrect newPinConfirm");
            }
        }
        //throw new NotImplementedException();
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if(oldPin.equals(pinAccount)) {
            if (newPin.matches("\\d+") && newPin.length() == 4){
                if (newPin.equals(newPinConfirm)) {
                    pinAccount = newPin;
                    return true;
                }
                return false;
            }
                return false;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return pinAccount.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (account == null)
        {
            throw new IllegalArgumentException("Account doesn't exist");
        }
        if(account_obj != null)
        {
            throw new IllegalArgumentException("Account for credit card exist.");
        }
        account_obj = account;
    }

    @Override
    public IAccount GetAccount(){
        return account_obj;
    }
    @Override
    public boolean DepositFunds(double amount) {
        try{
            account_obj.DepositFunds(amount);
            return true;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        try{
            account_obj.WithdrawFunds(amount);
            return true;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }
}
