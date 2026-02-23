package com.mipt.rezchikovsergey.sem1.multithreading;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class BankTest {
  @FunctionalInterface
  interface BankTransferFunction {
    void run(BankAccount firstAccount, BankAccount secondAccount, int money);
  }

  @Test
  public void sendToAccount_shouldSendMoney() {
    BankAccount source = new BankAccount(100);
    BankAccount dest = new BankAccount(30);
    Bank bank = new Bank();

    System.out.println(source.getBalance());

    bank.sendToAccount(source, dest, 40);

    assertEquals(100 - 40, source.getBalance());
    assertEquals(30 + 40, dest.getBalance());
  }

  @Test
  public void sendToAccount_shouldThrowException_WhenMoneyNotEnough() {
    BankAccount source = new BankAccount(100);
    BankAccount dest = new BankAccount(30);
    Bank bank = new Bank();

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          bank.sendToAccount(source, dest, 120);
        });
  }

  @Test
  public void sendToAccount_shouldNotSendMoney_WhenMoneyNegative() {
    BankAccount source = new BankAccount(100);
    BankAccount dest = new BankAccount(30);
    Bank bank = new Bank();

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          bank.sendToAccount(source, dest, -1);
        });
  }

  @Test
  public void sendToAccount_shouldThrowException_WhenSourceOrDestIsNull() {
    BankAccount dest = new BankAccount(1);
    Bank bank = new Bank();

    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(null, dest, 1));
    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(dest, null, 1));
    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(null, null, 1));
  }

  @Test
  public void sendToAccountDeadlock_shouldBeDeadlock() throws InterruptedException {
    Bank bank = new Bank();
    testDeadlock(true, bank::sendToAccountDeadlock);
  }

  @Test
  public void sendToAccount_shouldNotBeDeadlock() throws InterruptedException {
    Bank bank = new Bank();
    testDeadlock(false, bank::sendToAccount);
  }

  private void testDeadlock(boolean shouldBeDeadlock, BankTransferFunction transferFunction)
      throws InterruptedException {
    final int MAX_AWAIT_TIME = 100;
    final int ITERATIONS_NUM = 100;
    final int THREADS_NUM = 10;

    AtomicInteger completedCounter = new AtomicInteger(0);

    BankAccount firstAccount = new BankAccount(THREADS_NUM * ITERATIONS_NUM);
    BankAccount secondAccount = new BankAccount(THREADS_NUM * ITERATIONS_NUM);

    List<Thread> threads = new ArrayList<>(THREADS_NUM);

    for (int i = 0; i < THREADS_NUM; i++) {
      Thread thread;

      if (i % 2 == 0) {
        thread =
            new Thread(
                () -> {
                  for (int j = 0; j < ITERATIONS_NUM; j++) {
                    transferFunction.run(firstAccount, secondAccount, 1);
                    completedCounter.incrementAndGet();
                  }
                });
      } else {
        thread =
            new Thread(
                () -> {
                  for (int j = 0; j < ITERATIONS_NUM; j++) {
                    transferFunction.run(secondAccount, firstAccount, 1);
                    completedCounter.incrementAndGet();
                  }
                });
      }

      threads.add(thread);
      thread.start();
    }

    Thread.sleep(MAX_AWAIT_TIME);

    for (Thread thread : threads) {
      thread.interrupt();
    }

    assertEquals(shouldBeDeadlock, completedCounter.get() < THREADS_NUM * ITERATIONS_NUM);
  }
}
