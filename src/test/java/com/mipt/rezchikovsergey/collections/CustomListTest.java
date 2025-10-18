package com.mipt.rezchikovsergey.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CustomListTest {
  protected abstract CustomList<Integer> createList();

  @Test
  void shouldAddElement() {
    CustomList<Integer> list = createList();
    list.add(1);
    list.add(2);

    assertEquals(1, list.get(0));
    assertEquals(2, list.get(1));
  }

  @Test
  void shouldUpdateSize() {
    CustomList<Integer> list = createList();
    list.add(1);

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

  @Test
  void shouldThrowNullException() {
    CustomList<Integer> list = createList();
    assertThrows(NullPointerException.class, () -> list.add(null));
  }

  @Test
  void shouldThrowIndexOutOfBounds() {
    CustomList<Integer> list = createList();
    list.add(1);
    list.add(2);

    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
  }
}
