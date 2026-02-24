package com.mipt.rezchikovsergey.sem1;

public class Application {
  protected static double someDoubleVarWithoutInitialization;

  public final long someLongVarWithInitialization = 0;

  private String someStringVarWithoutInitialization;
  private int someIntVarWithoutInitialization;

  public static void main(String[] args) {
    for (int i = 0; i < 15; i++) {
      System.out.println("Iter: " + i);
    }
  }
}
