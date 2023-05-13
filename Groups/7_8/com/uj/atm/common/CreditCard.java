package com.uj.atm.common;

import com.sun.deploy.util.StringUtils;
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

    private String pin;
    private boolean beenInitialized;
    private IAccount account;

    public CreditCard() {
        pin = "0000";
        beenInitialized = false;
        account = null;
    }

    private boolean verifyPin(String newPin, String newPinConfirm) {
        return newPin!=null && Pattern.matches("^\\d{4}$", newPin) && newPin.equals(newPinConfirm);
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if(beenInitialized)
            throw new IllegalStateException("Card has been initialized already");

        if(verifyPin(newPin, newPinConfirm)) {
            pin = newPin;
            beenInitialized = true;
        }
        else
            throw new IllegalArgumentException("Error processing pin");
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if(beenInitialized && pin.equals(oldPin) && verifyPin(newPin, newPinConfirm)) {
            pin = newPin;
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return beenInitialized && this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if(this.account == null)
            this.account = account;
        else
            throw new IllegalStateException("Account already added");
    }

    @Override
    public IAccount GetAccount(){
        return account;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if(beenInitialized && account != null) {
            try {
                account.DepositFunds(amount);
                return true;
            }
            catch (IllegalArgumentException exc)
            {
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if(beenInitialized && account != null) {
            try {
                account.WithdrawFunds(amount);
                return true;
            }
            catch (IllegalArgumentException exc)
            {
                return false;
            }
        }

        return false;
    }
}
