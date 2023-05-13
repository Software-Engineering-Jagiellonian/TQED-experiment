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
	private String pin;
	private IAccount mainAccount;

	public CreditCard() {
		pin = "0000";
	}

	@Override
	public void Init(String newPin, String newPinConfirm) {
		if (newPin.equals(newPinConfirm) && IsPinValid(newPin)) {
			pin = newPin;
		}
	}

	@Override
	public boolean ChangePin(String oldPin, String newPin, String newPinConfirm) {
		if (pin.equals(oldPin) && newPin.equals(newPinConfirm) && IsPinValid(newPin)) {
			pin = newPin;
			return true;
		}
		return false;
	}

	@Override
	public boolean IsPinValid(String pin) {
		return pin.matches("[0-9]+") && pin.length() == 4;
	}

	@Override
	public void AddAccount(IAccount account) {
		if (mainAccount == null) {
			mainAccount = account;
		}
	}

	@Override
	public IAccount GetAccount() {
		return mainAccount;
	}

	@Override
	public boolean DepositFunds(double amount) {
		double accountBalance = mainAccount.AccountStatus();
		mainAccount.DepositFunds(amount);
        return accountBalance != mainAccount.AccountStatus();
    }

	@Override
	public boolean WithdrawFunds(double amount) {
		double accountBalance = mainAccount.AccountStatus();
		mainAccount.WithdrawFunds(amount);
        return accountBalance != mainAccount.AccountStatus();
    }
}
