package com.uj.atm.Test;

import com.uj.atm.common.Account;
import com.uj.atm.common.Atm;
import com.uj.atm.common.CreditCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
	private Atm atm;
	private CreditCard creditCard;
	private Account account;

	private final int DEFAULT_ACCOUNT_BALANCE = 100;
	private final int DEFAULT_BALANCE_OPERATION_AMOUNT = 50;
	private final int DEFAULT_RECIPIENT_ACCOUNT_BALANCE = 50;
	private final int TOO_BIG_WITHDRAW_AMOUNT = 150;

	private final String DEFAULT_PIN = "0000";
	private final String WRONG_OLD_PIN = "1234";
	private final String NEW_PIN = "1111";
	private final String NEW_PIN_CONFIRM = "1111";
	private final String WRONG_NEW_PIN_CONFIRM = "2222";
	private final String NOT_VALID_PIN_LENGHT = "0";
	private final String NOT_VALID_PIN_LETTERS = "000a";

	@Before
	public void setUp() throws Exception {
		creditCard = new CreditCard();
		account = new Account();
		atm = new Atm();
		account.DepositFunds(DEFAULT_ACCOUNT_BALANCE);
		creditCard.AddAccount(account);
	}

	@Test
	public void logInTest() {
		assertTrue(atm.CheckPinAndLogIn(creditCard, DEFAULT_PIN));
		assertFalse(atm.CheckPinAndLogIn(creditCard, NOT_VALID_PIN_LENGHT));
		assertFalse(atm.CheckPinAndLogIn(creditCard, NOT_VALID_PIN_LETTERS));
	}

	@Test
	public void accountStatusTest() {
		assertEquals(DEFAULT_ACCOUNT_BALANCE, atm.AccountStatus(account), 0.0);
	}

	@Test
	public void changePinCardTest() {
		assertTrue(atm.ChangePinCard(creditCard, DEFAULT_PIN, NEW_PIN, NEW_PIN_CONFIRM));
		assertFalse(atm.ChangePinCard(creditCard, WRONG_OLD_PIN, NEW_PIN, NEW_PIN_CONFIRM));
		assertFalse(atm.ChangePinCard(creditCard, DEFAULT_PIN, NEW_PIN, WRONG_NEW_PIN_CONFIRM));
		assertFalse(atm.ChangePinCard(creditCard, DEFAULT_PIN, NOT_VALID_PIN_LENGHT, NOT_VALID_PIN_LENGHT));
		assertFalse(atm.ChangePinCard(creditCard, DEFAULT_PIN, NOT_VALID_PIN_LETTERS, NOT_VALID_PIN_LETTERS));
	}

	@Test
	public void depositFundsTest() {
		atm.DepositFunds(creditCard, DEFAULT_BALANCE_OPERATION_AMOUNT);
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

	@Test
	public void transferTest() {
		Account recipientAccount = new Account();

		atm.Transfer(creditCard, recipientAccount, DEFAULT_BALANCE_OPERATION_AMOUNT);

		assertEquals(DEFAULT_RECIPIENT_ACCOUNT_BALANCE, recipientAccount.AccountStatus(), 0.0);
		assertEquals(DEFAULT_ACCOUNT_BALANCE - DEFAULT_BALANCE_OPERATION_AMOUNT, account.AccountStatus(), 0.0);
	}

	@Test
	public void transferTestWithTooBigAmount() {
		Account recipientAccount = new Account();

		atm.Transfer(creditCard, recipientAccount, TOO_BIG_WITHDRAW_AMOUNT);

		assertEquals(0, recipientAccount.AccountStatus(), 0.0);
		assertEquals(DEFAULT_ACCOUNT_BALANCE, account.AccountStatus(), 0.0);
	}
}