package com.mipt.rezchikovsergey.sem1.multithreading;

import java.util.UUID;

public class BankAccount {
  private int balance;
  private UUID id;

  public BankAccount(int balance) {
    this.balance = balance;
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
  }

  public int getBalance() {
    return balance;
  }

  public void withdraw(int money) {
    balance -= money;
  }

  public void deposit(int money) {
    this.balance += money;
  }
}
