package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;


/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class CreditCard implements ICreditCard {

    private IAccount account = null;
    private String pin = null;
    private boolean activated = false;

    public CreditCard() {
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (isPinLegal(newPin) && isPinLegal(newPinConfirm) && newPin.equals(newPinConfirm) && newPin.length() == 4) {
            pin = newPin;
            activated = true;
        }
    }

    private boolean isPinLegal(String pin) {
        return (pin != null) && pin.chars().allMatch(Character::isDigit);
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (activated && isPinLegal(newPin) && isPinLegal(newPinConfirm) && newPin.equals(newPinConfirm) && !newPin.equals(oldPin)) {
            if (pin.equals(oldPin)) {
                pin = newPin;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return activated && this.pin.equals(pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (activated && this.account == null) this.account = account;
    }

    @Override
    public IAccount GetAccount() {
        return account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (!activated || account == null) {
            return false;
        }
        if (amount < 0) {
            return false;
        }
        account.DepositFunds(amount);
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (!activated || account == null || amount < 0) {
            return false;
        }
        if (account.AccountStatus() < amount) {
            return false;
        }
        account.WithdrawFunds(amount);
        return true;
    }
}
