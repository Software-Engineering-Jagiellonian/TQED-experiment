package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
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
    private Map<ICreditCard, Long> logged = new HashMap<>();
    private long MaxLoginTime = 2 * 1000;

	public Atm() {

    }

    private boolean IsLoggedIn(ICreditCard card) {
        if (logged.get(card) == null) {
            return false;
        }
        long loginTime = logged.get(card);
        long timeFromLoggedIn = System.currentTimeMillis() - loginTime;
        return timeFromLoggedIn <= this.MaxLoginTime;
    }

    @Override
    public boolean CheckPinAndLogIn(ICreditCard creditCard, String pin) {
        boolean success = creditCard.IsPinValid(pin);
        if (success) {
            long currentTime = System.currentTimeMillis();
            logged.put(creditCard, currentTime);
        }
        return success;
    }

    @Override
    public double AccountStatus(IAccount account) {
        return account.AccountStatus();
    }

    @Override
    public boolean ChangePinCard(ICreditCard card, String oldPin, String newPin, String newPinConfirm) {
        boolean success = card.ChangePin(oldPin, newPin, newPinConfirm);
        // log out card
        this.logged.remove(card);
        return success;
    }

    @Override
    public boolean DepositFunds(ICreditCard card, double amount) {
        if (this.IsLoggedIn(card)) {
            return card.DepositFunds(amount);
        } else {
            return false;
        }
    }

    @Override
    public boolean WithdrawFunds(ICreditCard card, double amount) {
        if (this.IsLoggedIn(card)) {
            return card.WithdrawFunds(amount);
        } else {
            return false;
        }
    }

    @Override
    public boolean Transfer(ICreditCard card, IAccount accountRecipient, double amount) {
        if (!this.IsLoggedIn(card)) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        boolean canWithdraw = card.WithdrawFunds(amount);
        if (!canWithdraw) {
            return false;
        }

        accountRecipient.DepositFunds(amount);
        return true;
    }
}
