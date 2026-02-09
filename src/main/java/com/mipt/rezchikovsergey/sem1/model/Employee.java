package com.mipt.rezchikovsergey.model;

public abstract class Employee {
  public abstract void work(int anyInt);

  public boolean goHome(String str1, String str2) {
    return str1.equals(str2);
  }
}
