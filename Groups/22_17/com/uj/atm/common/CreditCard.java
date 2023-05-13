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
        pin = "";
        account = null;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if(HasPinCorrectFormat(newPin) && newPin.equals(newPinConfirm))
        {
            pin = newPin;
            return;
        }
        pin = "0000";
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if(IsCreditCardInitialize() && IsPinValid(oldPin) && HasPinCorrectFormat(newPin) && newPin.equals(newPinConfirm))
        {
            pin = newPin;
            return true;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return IsCreditCardInitialize() && this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if(this.account == null && IsCreditCardInitialize())
        {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if(!IsCreditCardInitialize() || account == null)
        {
            return false;
        }

        account.DepositFunds(amount);
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if(!IsCreditCardInitialize() || account == null || account.AccountStatus() < amount)
        {
            return false;
        }

        account.WithdrawFunds(amount);
        return true;
    }

    private boolean IsCreditCardInitialize()
    {
        return !pin.equals("");
    }

    private boolean HasPinCorrectFormat(String pin)
    {
        String pinPattern = "\\d+";
        return pin.matches(pinPattern) && pin.length() == 4;
    }
}