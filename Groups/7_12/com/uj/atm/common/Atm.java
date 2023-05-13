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

    private ICreditCard loggedCard;
	public Atm() {
        loggedCard = null;
    }

    @Override
    public boolean CheckPinAndLogIn(ICreditCard creditCard, String pin) {
        if(creditCard!=null && creditCard.IsPinValid(pin)) {
            loggedCard = creditCard;
            return true;
        }

        return false;
    }

    @Override
    public double AccountStatus(IAccount account) {
        if(account==null || loggedCard == null || loggedCard.GetAccount()!=account)
            throw new IllegalArgumentException("Account cannot be null");

        return account.AccountStatus();
    }

    @Override
    public boolean ChangePinCard(ICreditCard card, String oldPin, String newPin, String newPinConfirm) {
        if(card == null || loggedCard == null || loggedCard != card )
            throw new IllegalArgumentException("Card cannot be null");

        return card.ChangePin(oldPin, newPin, newPinConfirm);
    }

    @Override
    public boolean DepositFunds(ICreditCard card, double amount) {
        if(card == null || loggedCard == null || loggedCard != card)
            throw new IllegalArgumentException("Card cannot be null");

        return card.DepositFunds(amount);
    }

    @Override
    public boolean WithdrawFunds(ICreditCard card, double amount) {
        if(card == null || loggedCard == null || loggedCard != card)
            throw new IllegalArgumentException("Card cannot be null");

        return card.WithdrawFunds(amount);
    }

    @Override
    public boolean Transfer(ICreditCard card, IAccount accountRecipient, double amount) {
        if(card == null || accountRecipient == null || loggedCard == null || loggedCard != card)
            throw new IllegalArgumentException("Card and account cannot be null");

        boolean result = card.WithdrawFunds(amount);
        if(result)
            accountRecipient.DepositFunds(amount);

        return result;
    }
}
