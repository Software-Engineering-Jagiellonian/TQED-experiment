package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import org.hamcrest.core.Is;

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
    private int pin;
    private IAccount account;

    public CreditCard() {
        this.pin = 0;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (pin == 0 && newPin.equals(newPinConfirm) && IsPinValid(newPin) && parsePinNumber(newPin) != pin) {
            pin = parsePinNumber(newPin);
        }
    }

    private int parsePinNumber(String newPin) {
        try {
            return Integer.parseInt(newPin);
        }
        catch (NumberFormatException ignored) {
            return 0;
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (IsPinValid(oldPin) && parsePinNumber(oldPin) == pin && newPin.equals(newPinConfirm) && IsPinValid(newPin) && !oldPin.equals(newPin)) {
            pin = parsePinNumber(newPin);
            return true;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        Pattern pattern = Pattern.compile("\\d{4}");
        if (pin == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(pin);
        return matcher.matches();
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
        if (account == null) {
            return false;
        }
        double balance = account.AccountStatus();
        double balanceAfterTransaction = account.DepositFunds(amount);
        return balance != balanceAfterTransaction;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (account == null) {
            return false;
        }
        double balance = account.AccountStatus();
        double balanceAfterTransaction = account.WithdrawFunds(amount);
        return balance != balanceAfterTransaction;
    }
}