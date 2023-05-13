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

	public Atm() {

    }

    @Override
    public boolean CheckPinAndLogIn(ICreditCard creditCard, String pin) {
        if(creditCard != null && creditCard.GetAccount() != null){
            return creditCard.IsPinValid(pin);
        }

        return false;
    }

    @Override
    public double AccountStatus(IAccount account) {
        if(account != null){
            return account.AccountStatus();
        }

        return 0.0;
    }

    @Override
    public boolean ChangePinCard(ICreditCard card, String oldPin, String newPin, String newPinConfirm) {
        if(card != null)
        {
            return card.ChangePin(oldPin, newPin, newPinConfirm);
        }

        return false;
    }

    @Override
    public boolean DepositFunds(ICreditCard card, double amount) {
        if(card != null)
        {
            return card.DepositFunds(amount);
        }

        return false;
    }

    @Override
    public boolean WithdrawFunds(ICreditCard card, double amount) {
        if(card != null)
        {
            return card.WithdrawFunds(amount);
        }

        return false;
    }

    @Override
    public boolean Transfer(ICreditCard card, IAccount accountRecipient, double amount) {
        if(
                accountRecipient != null &&
                card.GetAccount() != accountRecipient &&
                card.WithdrawFunds(amount)
        ){
            accountRecipient.DepositFunds(amount);
            return true;
        }
        return false;
    }
}
