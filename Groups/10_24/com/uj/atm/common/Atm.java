package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;
import com.uj.atm.interfaces.IAtm;
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
public class Atm implements IAtm {

    public Atm() {

    }

    @Override
    public boolean CheckPinAndLogIn(ICreditCard creditCard, String pin) {
        if (creditCard == null || pin == null) {
            throw new IllegalArgumentException("creditCard and pin can not be null.");
        }
        if (pin.length() != 4) {
            return false;
        }

        return pin.chars().mapToObj(ch -> (char) ch).allMatch(Character::isDigit) && creditCard.IsPinValid(pin);
    }

    @Override
    public double AccountStatus(IAccount account) {
        return Optional.ofNullable(account)
                .map(IAccount::AccountStatus)
                .orElseThrow(() -> new IllegalArgumentException("Account can not be null."));
    }

    @Override
    public boolean ChangePinCard(ICreditCard card, String oldPin, String newPin, String newPinConfirm) {
        return Optional.ofNullable(card)
                .map(c -> c.ChangePin(oldPin, newPin, newPinConfirm))
                .isPresent();
    }

    @Override
    public boolean DepositFunds(ICreditCard card, double amount) {
        return Optional.ofNullable(card)
                .map(c -> c.DepositFunds(amount))
                .isPresent();
    }

    @Override
    public boolean WithdrawFunds(ICreditCard card, double amount) {
        return Optional.ofNullable(card)
                .map(c -> c.WithdrawFunds(amount))
                .isPresent();
    }

    @Override
    public boolean Transfer(ICreditCard card, IAccount accountRecipient, double amount) {
        if (card == null || accountRecipient == null) {
            throw new IllegalArgumentException("Card and accountRecipient cannot be null.");
        }
        
        if (!card.WithdrawFunds(amount)) {
            return false;
        }
        
        try {
            accountRecipient.DepositFunds(amount);
        } catch (RuntimeException e) {
            card.DepositFunds(amount);
            return false;
        }
        return true;
    }
}
