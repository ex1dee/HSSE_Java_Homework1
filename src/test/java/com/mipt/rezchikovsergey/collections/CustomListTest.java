package com.mipt.rezchikovsergey.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public abstract class CustomListTest {
  protected abstract CustomList<Integer> createList();

  @Test
  void shouldAddElementAndUpdateSize() {
    CustomList<Integer> list = createList();
    list.add(1);

    assertEquals(1, list.get(0));
    assertEquals(1, list.size());
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldRemoveElement() {
    CustomList<Integer> list = createList();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.remove(1);

    assertEquals(3, list.size());
    assertEquals(1, list.get(0));
    assertEquals(3, list.get(1));
    assertEquals(4, list.get(2));
  }
}
