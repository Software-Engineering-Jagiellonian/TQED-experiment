package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Struct;
import java.util.regex.Matcher;
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

    private IAccount account;
    private String pin;

    public CreditCard() {
        account = null;
        pin = "0000";
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        String regex = "^[0-9]{4}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(newPin);

        if (newPin.equals(newPinConfirm) && m.matches()) {
            pin = newPin;
//            return true;
        }
//        return false;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        String regex = "^[0-9]{4}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(newPin);

        if (newPin.equals(newPinConfirm) && m.matches()) {
            pin = newPin;
            return true;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account == null) {
            this.account = account;
        }
    }

    @Override
    public IAccount GetAccount() {
        return account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (account == null || amount <= 0) {
            return false;
        }

        account.DepositFunds(amount);
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (account == null || amount > account.AccountStatus()) {
            return false;
        }

        account.WithdrawFunds(amount);
        return true;
    }
}
