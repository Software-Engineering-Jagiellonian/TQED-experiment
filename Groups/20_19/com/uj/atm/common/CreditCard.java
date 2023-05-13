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
    private boolean isInitialized;

    public CreditCard() {
        this.pin = "1234";
        this.account = null;
        this.isInitialized = false;
    }

    @Override
    public void Init(String newPin, String newPinConfirm) {
        if (!this.isInitialized &&
                newPin.length() == 4 &&
                Integer.parseUnsignedInt(newPin) >= 0 &&
                newPin.equals(newPinConfirm)) {
            this.pin = newPin;
            this.isInitialized = true;
        }
    }

    @Override
    public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
        if (this.isInitialized &&
                this.pin.equals(oldPin) &&
                newPin.length() == 4 &&
                Integer.parseUnsignedInt(newPin) >= 0 &&
                newPin.equals(newPinConfirm)) {
            this.pin = newPin;
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
    public IAccount GetAccount(){
        return this.account;
    }

    @Override
    public boolean DepositFunds(double amount) {
        if (account != null &&
                this.isInitialized
        ) {
            final double balanceBeforeDeposit = account.AccountStatus();
            final double balanceAfterDeposit = account.DepositFunds(amount);

            return Double.compare(balanceBeforeDeposit, balanceAfterDeposit) == -1;
        }

        return false;
    }

    @Override
    public boolean WithdrawFunds(double amount) {
        if (account != null &&
                this.isInitialized
        ) {
            final double balanceBeforeDeposit = account.AccountStatus();
            final double balanceAfterDeposit = account.WithdrawFunds(amount);

            return Double.compare(balanceBeforeDeposit, balanceAfterDeposit) == 1;
        }

        return false;
    }
}
