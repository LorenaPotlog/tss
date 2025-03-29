package org.tss;

import java.util.*;

public class BankingService {
  public static final List<String> COUNTRY_CODES = Arrays.asList("DE", "RO", "IT");
  public static final List<String> CURRENCY_CODES = Arrays.asList("1234", "5678", "4321");

  public List<Account> accounts;

  // Constructor to initialize the accounts list
  public BankingService() {
    accounts = new ArrayList<>();
    accounts.add(new Account("BANK1234RO00000001", 2000, "RON"));
    accounts.add(new Account("BANK5678DE00000001", 1500, "EUR"));
    accounts.add(new Account("BANK4321FR00000001", 1500, "EUR"));
    accounts.add(new Account("BANK9876GB00000001", 1500, "GBP"));
    accounts.add(new Account("BANK1357IT00000001", 1500, "EUR"));
    accounts.add(new Account("BANK2468ES00000001", 1500, "EUR"));
    accounts.add(new Account("BANK3690NL00000001", 1500, "EUR"));
    accounts.add(new Account("BANK4820PT00000001", 1500, "EUR"));
    accounts.add(new Account("BANK9132BE00000001", 1500, "EUR"));
    accounts.add(new Account("BANK5246AT00000001", 1500, "EUR"));
  }

  // action - CHECK, WITHDRAW, DEPOSIT

  public String atm(
      String iban, String action, Optional<Integer> amount, Optional<String> currency) {

    if (!validateIban(iban)) {
      return "Invalid IBAN";
    }

    Optional<Account> accountOp = getAccountFromIban(iban, accounts);

    if (accountOp.isEmpty()) {
      return "Account not found";
    }

    Account account = accountOp.get();

    switch (action) {
      case "check":
        return checkAccount(account);
      case "withdraw":
        if (amount.isPresent()) {
          int withdrawAmount = amount.get();
          return withdraw(account, withdrawAmount);
        }
        return "No amount specified";
      case "deposit":
        if (amount.isPresent() && currency.isPresent()) {
          int depositAmount = amount.get();
          String depositCurrency = currency.get();
          return deposit(account, depositAmount, depositCurrency);
        }
        return "No amount/currency specified";
      default:
        return "Invalid action";
    }
  }

  // IBAN should be BANK + 4 digits (currency code) + 2 letters (country code) + 6 digits + 01
  // BANK 0051 DE 938234 01

  public boolean validateIban(String iban) {

    if (iban == null || iban.length() != 18) {
      return false;
    }

    String currencyCode = iban.substring(4, 8);
    String countryCode = iban.substring(8, 10);

    return iban.startsWith("BANK")
        && iban.endsWith("01")
        && COUNTRY_CODES.contains(countryCode)
        && CURRENCY_CODES.contains(currencyCode);
  }

  private Optional<Account> getAccountFromIban(String iban, List<Account> accounts) {
    for (Account account : accounts) {
      if (account.getIban().equals(iban)) {
        return Optional.of(account);
      }
    }
    return Optional.empty();
  }

  public String checkAccount(Account account) {
    return account.toString();
  }

  public String withdraw(Account account, int amount) {
    int balance = account.getBalance();
    if (amount > 0 && amount <= balance) {
      account.setBalance(balance - amount);
      return "You have withdraw "
          + amount
          + ". "
          + "Your current balance is "
          + account.getBalance();
    } else if (amount > balance) {
      return "Insufficient funds for withdrawal - "
          + amount
          + ". Your current balance is "
          + account.getBalance();
    }
    return "Error on account withdrawal";
  }

  public String deposit(Account account, int amount, String currency) {
    int balance = account.getBalance();
    if (account.getCurrency().equals(currency)) {
      balance += amount;
      account.setBalance(balance);
      return "You have deposited "
          + amount
          + " "
          + account.getCurrency()
          + ". "
          + "Your current balance is "
          + account.getBalance();
    } else {
      int exchangedAmount = exchange(amount, currency, account.getCurrency());
      if(exchangedAmount == 0){
        return "No possible exchange";
      }
      balance += exchangedAmount;
      account.setBalance(balance);
      return "You have deposited "
          + exchangedAmount
          + " "
          + account.getCurrency()
          + ". "
          + "Your current balance is "
          + account.getBalance();
    }
  }

  private int exchange(int amount, String currency, String accountCurrency) {
    if (currency.equals("RON") && accountCurrency.equals("EUR")) {
      return (int) (amount * 0.20);
    } else if (accountCurrency.equals("RON") && currency.equals("EUR")) {
      return (int) (amount * 5.01);
    }
    return 0;
  }
}
