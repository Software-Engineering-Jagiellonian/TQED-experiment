package com.uj.atm.common;

import com.uj.atm.interfaces.IAccount;

/**
 * UWAGA:
 * 1. Nie wolno tworzyć publicznych pól, jedynie można używać metod z interface
 * 2. Nie wolno dopisywać własnych metod
 * 3. Nie wolno modyfikować interface
 * 4. Nie wolno zmieniać wersji javy ani junita.
 * 5. Nie wolno tworzyć nowych (własnych) konstruktorów. Można używać jedynie istniejących (bezparametrowych).
 */
public class Account implements IAccount {
	private double balance;

	public Account() {
		balance = 0;
	}

	@Override
	public double AccountStatus() {
		return balance;
	}

	@Override
	public double DepositFunds(double amount) {
		balance += amount;
		return balance;
	}

	@Override
	public double WithdrawFunds(double amount) {
		if (amount < balance) {
			balance -= amount;
		}
		return balance;
	}

}
