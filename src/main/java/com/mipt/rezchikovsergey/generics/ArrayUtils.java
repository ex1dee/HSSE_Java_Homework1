package com.mipt.rezchikovsergey.generics;

public class ArrayUtils {
  public static <T> int findFirst(T[] array, T element) {
    if (array == null) return -1;

    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(element)) {
        return i;
      }
    }

    return -1;
  }
}
