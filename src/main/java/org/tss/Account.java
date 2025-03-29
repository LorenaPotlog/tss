package org.tss;

public class Account {
  int balance;
  String iban;
  String currency;

  public Account(String iban, int balance, String currency) {
    this.iban = iban;
    this.balance = balance;
    this.currency = currency;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public String getIban() {
    return iban;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {
    return String.format("The account for iban - %s, has a balance of %d %s", iban, balance, currency);
  }
}
