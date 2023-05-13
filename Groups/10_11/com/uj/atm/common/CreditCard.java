package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    private boolean initialized;
    private String pin;
    private IAccount assignedAccount;

    public CreditCard() {
        initialized = false;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (initialized) {
            throw new IllegalStateException("Credit card already initialized.");
        }
        if (newPin == null || newPinConfirm == null) {
            throw new IllegalArgumentException("Pin cannot be null.");
        }
        if (!newPin.equals(newPinConfirm)) {
            throw new IllegalArgumentException("newPin and newPinConfirm do not match.");
        }

        pin = newPin;
        initialized = true;
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (!initialized) {
            throw new IllegalStateException("Credit card not initialized.");
        }
        if (oldPin == null || newPin == null || newPinConfirm == null) {
            throw new IllegalArgumentException("Pin cannot be null.");
        }
        if (!oldPin.equals(pin) | !newPin.equals(newPinConfirm)) {
            return false;
        }

        pin = newPin;
        return true;
    }

    @Override
    public boolean IsPinValid(String input) {
        if (!initialized) {
            throw new IllegalStateException("Credit card not initialized.");
        }
        
        return Optional.ofNullable(input)
                .map(nonNullInput -> nonNullInput.equals(pin))
                .orElse(false);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (!initialized) {
            throw new IllegalStateException("Credit card not initialized.");
        }
        if (assignedAccount != null) {
            throw new IllegalStateException("Account already added to the card");
        }
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        
        assignedAccount = account;
    }

    @Override
    public IAccount GetAccount() {
        if (!initialized) {
            throw new IllegalStateException("Credit card not initialized.");
        }
        
        return Optional.ofNullable(assignedAccount)
                .orElseThrow(() -> new IllegalStateException("No account added yet."));
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (!initialized) {
            throw new IllegalStateException("Credit card not initialized.");
        }
        
        return Optional.ofNullable(assignedAccount)
                .map(account -> account.DepositFunds(amount))
                .isPresent();
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (!initialized) {
            throw new IllegalStateException("Credit card not initialized.");
        }
        
        return Optional.ofNullable(assignedAccount)
                .map(account -> account.WithdrawFunds(amount))
                .isPresent();
    }
}
