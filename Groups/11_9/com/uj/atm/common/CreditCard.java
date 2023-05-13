package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;

import java.util.Optional;


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

    private Optional<IAccount> account;

    public static final String DEFAULT_PIN = "0000";

    public CreditCard() {

        pin = DEFAULT_PIN;
        account = Optional.empty();
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {

        ChangePin(DEFAULT_PIN, newPin, newPinConfirm);
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {

        if (!IsPinValid(oldPin)) {

            return false;
        }
        if (!newPin.equals(newPinConfirm)) {

            return false;
        }

        pin = newPin;
        return true;
    }

    @Override
    public boolean IsPinValid(String pin) {

        if (pin.length() != 4){

            return false;
        }
        try {

            Integer.parseInt(pin);
        } catch (NumberFormatException ex) {
            return false;
        }
        return this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {

        if (!this.account.isPresent()){

            this.account = Optional.of(account);
        }
    }

    @Override
    public IAccount GetAccount() {

        return account.orElse(null);
    }

    @Override
    public boolean DepositFunds(double amount) {

        if (!account.isPresent()){

            return false;
        }

        account.ifPresent(iAccount -> iAccount.DepositFunds(amount));
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {

        if (!account.isPresent()){

            return false;
        }
        if (account.get().AccountStatus() < amount) {

            return false;
        }

        account.get().WithdrawFunds(amount);
        return true;
    }
}
