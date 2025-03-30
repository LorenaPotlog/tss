package org.tss;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Teste funcționale și structurale
class BankingServiceTest {

  BankingService bankingService = new BankingService();
  String validIban = "BANK1234RO00000001";

  @Test
  @DisplayName("Validate IBAN with valid value")
  void validateIban() {
    assertTrue(bankingService.validateIban(validIban));
  }

  @Test
  @DisplayName("Validate IBAN with null value")
  void validateIbanNull() {
    String iban = null;
    assertFalse(bankingService.validateIban(iban));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {"This iban is longer than 18", "BANK1234TR00000001", "BANK1235RO00000001", "BADK1234TR00000001", "BANK1234TR00000003"})
  @DisplayName("Validate IBAN with wrong values")
  void validateIbanWrong(String iban) {
    assertFalse(bankingService.validateIban(iban));
  }

  @Test
  @DisplayName("Withdraw money from existing account")
  void withdraw() {
    Account account = new Account(validIban, 2000, "RON");
    String result = bankingService.withdraw(account, 2000);
    assertEquals(0, account.getBalance());
    assertEquals("You have withdraw 2000. Your current balance is 0", result);
  }

  @Test
  @DisplayName("Withdraw bigger amount than actual balance")
  void withdrawAmountBiggerThanBalance() {
    Account account = new Account(validIban, 500, "RON");
    String result = bankingService.withdraw(account, 600);
    assertEquals(500, account.getBalance());
    assertEquals("Insufficient funds for withdrawal - 600. Your current balance is 500", result);
  }

  @Test
  @DisplayName("Withdraw negative amount")
  void withdrawNegativeAmount() {
    Account account = new Account(validIban, 500, "RON");
    String result = bankingService.withdraw(account, -100);
    assertEquals(500, account.getBalance());
    assertEquals("Error on account withdrawal", result);
  }

  @Test
  @DisplayName("Withdraw no amount")
  void withdrawNoAmount() {
    Account account = new Account(validIban, 500, "RON");
    String result = bankingService.withdraw(account, 0);
    assertEquals(500, account.getBalance());
    assertEquals("No amount to be withdraw", result);
  }

  @Test
  @DisplayName("Deposit money in existing account")
  void depositAmount() {
    Account account = new Account(validIban, 500, "RON");
    String result = bankingService.deposit(account, 350, "RON");
    assertEquals(850, account.getBalance());
    assertEquals("You have deposited 350 RON. Your current balance is 850", result);
  }

  @Test
  @DisplayName("Deposit money in different currency and exchange from EUR to RON")
  void depositAmountExchangeToRon() {
    Account account = new Account(validIban, 500, "RON");
    String result = bankingService.deposit(account, 350, "EUR");
    assertEquals(2253, account.getBalance());
    assertEquals("You have deposited 1753 RON. Your current balance is 2253", result);
  }

  @Test
  @DisplayName("Deposit money in different currency and exchange from RON to EUR")
  void depositAmountExchangeToEuro() {
    Account account = new Account(validIban, 500, "EUR");
    String result = bankingService.deposit(account, 350, "RON");
    assertEquals(570, account.getBalance());
    assertEquals("You have deposited 70 EUR. Your current balance is 570", result);
  }


  @Test
  @DisplayName("ATM - invalid iban")
  void atmInvalidIban() {
    String result = bankingService.atm(null, "check", Optional.of(1000),Optional.of("RON"));
    assertEquals("Invalid IBAN", result);
  }

  @Test
  @DisplayName("ATM - valid iban but no account found")
  void atmNoAccountFound() {
    String result = bankingService.atm("BANK1234RO00020001", "check", Optional.of(1000),Optional.of("RON"));
    assertEquals("Account not found", result);
  }

  @Test
  @DisplayName("ATM - valid account but no action found")
  void atmNoActionFound() {
    String result = bankingService.atm(validIban, "checks", Optional.of(1000),Optional.of("RON"));
    assertEquals("Invalid action", result);
  }

  @Test
  @DisplayName("ATM - check valid account")
  void atmCheck() {
    String result = bankingService.atm(validIban, "check", Optional.of(1000),Optional.of("RON"));
    assertEquals("The account for iban - BANK1234RO00000001, has a balance of 2000 RON", result);
  }


  @Test
  @DisplayName("ATM - withdraw from valid account")
  void atmWithdraw() {
    String result = bankingService.atm(validIban, "withdraw", Optional.of(200),Optional.of("RON"));
    assertEquals("You have withdraw 200. Your current balance is 1800", result);
  }

  @Test
  @DisplayName("ATM - withdraw but no amount specified")
  void atmWithdrawNoAmount() {
    String result = bankingService.atm(validIban, "withdraw", Optional.empty(),Optional.of("RON"));
    assertEquals("No amount specified", result);
  }

  @Test
  @DisplayName("ATM - deposit in valid account")
  void atmDeposit() {
    String result = bankingService.atm(validIban, "deposit", Optional.of(400),Optional.of("RON"));
    assertEquals("You have deposited 400 RON. Your current balance is 2400", result);
  }

  @Test
  @DisplayName("ATM - deposit but no valid exchange")
  void atmDepositNoExchangeFound() {
    String result = bankingService.atm(validIban, "deposit", Optional.of(400),Optional.of("USD"));
    assertEquals("No possible exchange", result);
  }

  @Test
  @DisplayName("ATM - deposit but no amount specified")
  void atmDepositNoAmount() {
    String result = bankingService.atm(validIban, "deposit", Optional.empty(),Optional.of("RON"));
    assertEquals("No amount/currency specified", result);
  }

  @Test
  @DisplayName("ATM - deposit but no currency specified")
  void atmDepositNoCurrency() {
    String result = bankingService.atm(validIban, "deposit", Optional.of(100),Optional.empty());
    assertEquals("No amount/currency specified", result);
  }
}
