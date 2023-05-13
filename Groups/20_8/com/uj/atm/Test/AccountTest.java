package com.uj.atm.Test;

import com.uj.atm.common.Account;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	private Account account;
	private final int DEFAULT_ACCOUNT_BALANCE = 100;
	private final int DEFAULT_BALANCE_OPERATION_AMOUNT = 50;
	private final int TOO_BIG_WITHDRAW_AMOUNT = 150;

	@Before
	public void setUp() throws Exception {
		account = new Account();
		account.DepositFunds(DEFAULT_ACCOUNT_BALANCE);
	}

	@Test
	public void accountStatusReturnsAccountBalance() {
		assertEquals(DEFAULT_ACCOUNT_BALANCE, account.AccountStatus(), 0.0);
	}

	@Test
	public void addsMoneyToBalanceWithDeposit() {
		account.DepositFunds(DEFAULT_BALANCE_OPERATION_AMOUNT);
		assertEquals(DEFAULT_ACCOUNT_BALANCE + DEFAULT_BALANCE_OPERATION_AMOUNT, account.AccountStatus(), 0.0);
	}

	@Test
	public void takesMoneyFromBalanceWithWithdraw() {
		account.WithdrawFunds(DEFAULT_BALANCE_OPERATION_AMOUNT);
		assertEquals(DEFAULT_ACCOUNT_BALANCE - DEFAULT_BALANCE_OPERATION_AMOUNT, account.AccountStatus(), 0.0);
	}

	@Test
	public void doNotTakeMoneyWhenNotEnoughInAccount() {
		account.WithdrawFunds(TOO_BIG_WITHDRAW_AMOUNT);
		assertEquals(DEFAULT_ACCOUNT_BALANCE, account.AccountStatus(), 0.0);
	}
}