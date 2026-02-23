package com.mipt.rezchikovsergey.sem1.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CustomArrayListTest extends CustomListTest {
  @Override
  protected CustomList<Integer> createList() {
    return new CustomArrayList<>();
  }

  @Test
  void shouldUpdateCapacity() {
    CustomArrayList<Integer> list = new CustomArrayList<>();
    int oldCapacity = list.capacity();

    for (int i = 0; i <= oldCapacity; i++) {
      list.add(0);
    }

    assertTrue(oldCapacity < list.capacity());
  }

  @Test
  void shouldRemoveFirstElement() {
    CustomArrayList<Integer> list = new CustomArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.remove(0);

    assertEquals(2, list.get(0));
    assertEquals(3, list.get(1));
  }

  @Test
  void shouldRemoveLastElement() {
    CustomArrayList<Integer> list = new CustomArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.remove(list.size() - 1);

    assertEquals(1, list.get(0));
    assertEquals(2, list.get(1));
  }
}
