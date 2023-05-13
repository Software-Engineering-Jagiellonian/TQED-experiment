package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
import com.uj.atm.interfaces.ICreditCard;
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

    private boolean isAtmUnblocked = false;

	public Atm() {

    }

    @Override
    public boolean CheckPinAndLogIn(ICreditCard creditCard, String pin) {
        if(creditCard.IsPinValid(pin))
        {
            isAtmUnblocked = true;
            return true;
        }
	    return false;
    }

    @Override
    public double AccountStatus(IAccount account) {
	    if(isAtmUnblocked)
        {
            return account.AccountStatus();
        }
	    return Double.NaN;
    }

    @Override
    public boolean ChangePinCard(ICreditCard card, String oldPin, String newPin, String newPinConfirm) {
        return isAtmUnblocked && card != null && card.ChangePin(oldPin, newPin, newPinConfirm);
    }

    @Override
    public boolean DepositFunds(ICreditCard card, double amount) {
	    return isAtmUnblocked && card != null && card.DepositFunds(amount);
    }

    @Override
    public boolean WithdrawFunds(ICreditCard card, double amount) {
	    return isAtmUnblocked && card != null && card.WithdrawFunds(amount);
    }

    @Override
    public boolean Transfer(ICreditCard card, IAccount accountRecipient, double amount) {
        if(isAtmUnblocked && card != null && card.WithdrawFunds(amount))
        {
            accountRecipient.DepositFunds(amount);
            return true;
        }
        return false;
    }
}
