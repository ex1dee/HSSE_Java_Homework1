package com.mipt.rezchikovsergey.collections;

import java.util.*;

/**
 * Custom list implementation based on array with dynamic expansion
 *
 * @param <A> the type of element in the list
 */

public class CustomArrayList<A> implements CustomList<A> {
  private final float EXPANSION_COEFFICIENT = 1.5f;
  private final int DEFAULT_CAPACITY = 8;

  private int capacity = DEFAULT_CAPACITY;
  private Object[] array = new Object[capacity];
  private int size = 0;

  /**
   * Returns element with specified index in the list
   *
   * @param index the index of an element
   * @return the element at the specified index
   * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
   */
  @SuppressWarnings("unchecked")
  @Override
  public A get(int index) {
    Objects.checkIndex(index, size);

    return (A) array[index];
  }

  /**
   * Adds the specified element to the end of the list
   * If size is equal to capacity array expands
   *
   * @param element the element that will be added to the list
   */
  @Override
  public void add(A element) {
    Objects.requireNonNull(element);

    if (size == capacity) {
      expandArray();
    }

    array[size++] = element;
  }

  /**
   * Removes element with specified index from the list
   * Shifts all subsequent elements to the left
   *
   * @param index the index of an element to be removed
   */
  @Override
  public void remove(int index) {
    Objects.checkIndex(index, size);
    array[index] = null;

    for (int i = index; i < size - 1; ++i) {
      array[i] = array[i + 1];
    }

    size = Math.max(0, size - 1);
  }

  /**
   * Returns true if the list is empty
   *
   * @return true if the list is empty
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns size of the list
   *
   * @return size of the list
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns the iterator of elements of the array in the order they were added
   *
   * @return the iterator of elements of the array in the order they were added
   * @see CustomArrayListIterator
   */
  @Override
  public Iterator<A> iterator() {
    return new CustomArrayListIterator();
  }

  /**
   * Returns the current capacity of the list
   *
   * @return the current capacity of the list
   */

  public int capacity() {
    return capacity;
  }

  private void expandArray() {
    capacity = (int) Math.ceil(capacity * EXPANSION_COEFFICIENT);
    array = Arrays.copyOf(array, capacity);
  }

  private final class CustomArrayListIterator implements Iterator<A> {
    private int currentIndex = 0;

    @Override
    public boolean hasNext() {
      return currentIndex < size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public A next() {
      if (!hasNext()) throw new NoSuchElementException();

      return (A) array[currentIndex++];
    }
  }
}
