package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.CreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTest {
	private CreditCard creditCard;
	private Account account;

	private final String DEFAULT_PIN = "0000";
	private final String WRONG_OLD_PIN = "1234";
	private final String NEW_PIN = "1111";
	private final String NEW_PIN_CONFIRM = "1111";
	private final String WRONG_NEW_PIN_CONFIRM = "2222";

	private final String NOT_VALID_PIN_LENGHT = "0";
	private final String NOT_VALID_PIN_LETTERS = "000a";

	private final int DEFAULT_ACCOUNT_BALANCE = 100;
	private final int DEFAULT_BALANCE_OPERATION_AMOUNT = 50;
	private final int TOO_BIG_WITHDRAW_AMOUNT = 150;

	@Before
	public void setUp() throws Exception {
		creditCard = new CreditCard();
		account = new Account();
		account.DepositFunds(DEFAULT_ACCOUNT_BALANCE);
		creditCard.AddAccount(account);
	}

	@Test
	public void changePinTest() {
		assertTrue(creditCard.ChangePin(DEFAULT_PIN, NEW_PIN, NEW_PIN_CONFIRM));
		assertFalse(creditCard.ChangePin(WRONG_OLD_PIN, NEW_PIN, NEW_PIN_CONFIRM));
		assertFalse(creditCard.ChangePin(DEFAULT_PIN, NEW_PIN, WRONG_NEW_PIN_CONFIRM));
		assertFalse(creditCard.ChangePin(DEFAULT_PIN, NOT_VALID_PIN_LENGHT, NOT_VALID_PIN_LENGHT));
		assertFalse(creditCard.ChangePin(DEFAULT_PIN, NOT_VALID_PIN_LETTERS, NOT_VALID_PIN_LETTERS));
	}

	@Test
	public void isPinValidTest() {
		assertTrue(creditCard.IsPinValid(DEFAULT_PIN));
		assertFalse(creditCard.IsPinValid(NOT_VALID_PIN_LENGHT));
		assertFalse(creditCard.IsPinValid(NOT_VALID_PIN_LETTERS));
	}

	@Test
	public void depositFundsTest() {
		creditCard.DepositFunds(DEFAULT_BALANCE_OPERATION_AMOUNT);
		assertEquals(DEFAULT_ACCOUNT_BALANCE + DEFAULT_BALANCE_OPERATION_AMOUNT, account.AccountStatus(), 0.0);
	}

	@Test
	public void withdrawFundsTest() {
		creditCard.WithdrawFunds(DEFAULT_BALANCE_OPERATION_AMOUNT);
		assertEquals(DEFAULT_ACCOUNT_BALANCE - DEFAULT_BALANCE_OPERATION_AMOUNT, account.AccountStatus(), 0.0);
	}

	@Test
	public void withdrawFundsTestWithTooBigAmount() {
		creditCard.WithdrawFunds(TOO_BIG_WITHDRAW_AMOUNT);
		assertEquals(DEFAULT_ACCOUNT_BALANCE, account.AccountStatus(), 0.0);
	}
}