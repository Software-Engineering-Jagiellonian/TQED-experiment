package com.uj.atm.common;

import com.sun.corba.se.spi.activation._ActivatorImplBase;
import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.management.remote.rmi._RMIConnection_Stub;


/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class CreditCard implements ICreditCard {

    private String _pin;
    private IAccount _account;
    public CreditCard() {
       _pin = "0000";
       _account = null;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if(newPin.length() == 4 && newPin.equals(newPinConfirm))
        {
            for(int i=0; i<newPin.length(); i++){
                if(!(newPin.charAt(i)>=48 && newPin.charAt(i)<=57)){
                    return;
                }
            }

            _pin = newPin;
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if(IsPinValid(oldPin) && newPin.equals(newPinConfirm) && newPin.length() == 4)
        {
            for (int i = 0; i < newPin.length(); i++) {
                if (!(newPin.charAt(i) >= 48 && newPin.charAt(i) <= 57)) {
                    return false;
                }
            }

            _pin = newPin;
            return true;
        }

        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        if(pin.equals(_pin)){
            return true;
        }

        return false;
    }

    @Override
    public void AddAccount(IAccount account) {
        if(_account == null){
            _account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
       return _account;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if(GetAccount() != null){
            if(amount == 0.0)
            {
                return true;
            }
            double before = GetAccount().AccountStatus();
            double after = GetAccount().DepositFunds(amount);
            return before != after;
        }

        return false;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if(GetAccount() != null) {
            if(amount == 0.0)
            {
                return true;
            }
            double before = GetAccount().AccountStatus();
            return before != GetAccount().WithdrawFunds(amount);
        }

        return false;
    }
}
