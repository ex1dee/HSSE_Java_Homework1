package com.mipt.rezchikovsergey.sem1.multithreading;

public class Bank {
  public void sendToAccount(BankAccount source, BankAccount destination, int money) {
    if (source == null || destination == null || money < 0) throw new IllegalArgumentException();

    BankAccount firstAccount =
        source.getId().compareTo(destination.getId()) < 0 ? source : destination;
    BankAccount secondAccount = firstAccount == source ? destination : source;

    synchronized (firstAccount) {
      synchronized (secondAccount) {
        if (source.getBalance() >= money) {
          source.withdraw(money);
          destination.deposit(money);
        } else {
          throw new IllegalArgumentException();
        }
      }
    }
  }

  public void sendToAccountDeadlock(BankAccount source, BankAccount destination, int money) {
    if (source == null || destination == null || money < 0) throw new IllegalArgumentException();

    synchronized (source) {
      synchronized (destination) {
        if (source.getBalance() >= money) {
          source.withdraw(money);
          destination.deposit(money);
        } else {
          throw new IllegalArgumentException();
        }
      }
    }
  }
}
