package com.mipt.rezchikovsergey.generics;

public class Calculator<T extends Number> {
  public double sum(T a, T b) {
    return toDouble(a) + toDouble(b);
  }

  public double subtract(T a, T b) {
    return toDouble(a) - toDouble(b);
  }

  public double multiply(T a, T b) {
    return toDouble(a) * toDouble(b);
  }

  public double divide(T a, T b) {
    if (toDouble(b) == 0.0) return Double.NaN;

    return toDouble(a) / toDouble(b);
  }

  private double toDouble(T num) {
    if (num == null) return 0.0;

    return num.doubleValue();
  }
}
