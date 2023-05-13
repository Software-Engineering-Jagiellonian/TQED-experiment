package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.ICreditCard;
import sun.font.TrueTypeFont;
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
        this.pin = "0000";
        this.account = null;
    }

    private boolean IsPinCorrect(String pin) {
        boolean isNumeric = pin.chars().allMatch( Character::isDigit );
        boolean isCorrectLen = (pin.length() == 4);

        return isNumeric && isCorrectLen;
    }

    private boolean IsPinChangeValid(String newPin, String newPinConfirm) {
        boolean isCorrect = IsPinCorrect(newPin);
        boolean isEqual = newPin.equals(newPinConfirm);

        return isCorrect && isEqual;
    }
    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (IsPinChangeValid(newPin, newPinConfirm)){
            this.pin = newPin;
        } else {
            throw new IllegalArgumentException("Pin is not correct.");
        }
    }
    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (oldPin.equals(this.pin) && IsPinChangeValid(newPin, newPinConfirm)){
            this.pin = newPin;
            return true;
        }
        return false;
    }

    @Override
    public boolean IsPinValid(String pin) {
        return IsPinCorrect(pin) && pin.equals(this.pin);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (this.account == null){
            this.account = account;
        }
    }
    @Override
    public IAccount GetAccount(){
        return this.account;
    }
    @Override
    public boolean DepositFunds(double amount) {
        if (GetAccount() == null){
            System.out.println("This card is not linked to any account.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }

        IAccount account = GetAccount();
        account.DepositFunds(amount);
        return true;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (GetAccount() == null){
            System.out.println("This card is not linked to any account.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Withdraw amount must be positive.");
            return false;
        }

        IAccount account = GetAccount();
        if (amount > account.AccountStatus()) {
            System.out.println("Account doesn't have sufficient funds.");
            return false;
        }

        account.WithdrawFunds(amount);
        return true;
    }
}
