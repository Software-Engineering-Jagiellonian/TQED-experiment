package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

import static java.lang.StrictMath.abs;

/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class Atm implements IAtm {

	public Atm() {

    }

    @Override
    public boolean CheckPinAndLogIn(ICreditCard creditCard, String pin) {
        return creditCard.IsPinValid(pin);
    }

    @Override
    public double AccountStatus(IAccount account) {
        return account.AccountStatus();
    }

    @Override
    public boolean ChangePinCard(ICreditCard card, String oldPin, String newPin, String newPinConfirm) {
        return card.ChangePin(oldPin, newPin, newPinConfirm);
    }

    @Override
    public boolean DepositFunds(ICreditCard card, double amount) {
        return card.DepositFunds(amount);
    }

    @Override
    public boolean WithdrawFunds(ICreditCard card, double amount) {
        return card.WithdrawFunds(amount);
    }

    @Override
    public boolean Transfer(ICreditCard card, IAccount accountRecipient, double amount) {
        if (card == null || accountRecipient == null)
            return false;
        if (card.GetAccount() == null)
            return false;
        double balanceRecipient = accountRecipient.AccountStatus();

        boolean res = card.WithdrawFunds(amount);

        if (!res) {
            return false;
        }

         accountRecipient.DepositFunds(amount);

        if (accountRecipient.AccountStatus() == balanceRecipient) {
            card.DepositFunds(amount);
            return false;
        }
        return true;
    }
}
