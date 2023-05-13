package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


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
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        try {
            if(!newPin.equals(newPinConfirm)) {
                throw new Exception("Pins are not equal.");
            }
            try {
                Pattern pattern = Pattern.compile("^\\d{4}$");
                Matcher matcher = pattern.matcher(newPin);
                boolean result = matcher.find();
                if(!result) {
                    throw new Exception("The pin should only consists of 4 digits.");
                }
                this.pin = newPin;
            } catch(Exception e) {
                System.out.println(e.toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        try {
            if(!oldPin.equals(this.pin)) {
                throw new Exception("Provided pin is not valid.");
            }
            Init(newPin, newPinConfirm);
            if (this.pin.equals(newPin)) {
                return true;
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return pin.equals(this.pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if(this.account == null) {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount(){
        return account;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if (account == null) {
            return false;
        }
        double balance = account.AccountStatus();
        account.DepositFunds(amount);
        return account.AccountStatus() == balance + amount;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (account == null) {
            return false;
        }
        double balance = account.AccountStatus();
        account.WithdrawFunds(amount);
        return account.AccountStatus() == balance - amount;
    }
}
