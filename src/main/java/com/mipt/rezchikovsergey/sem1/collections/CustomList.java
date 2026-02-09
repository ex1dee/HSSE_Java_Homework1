package com.mipt.rezchikovsergey.sem1.collections;

/**
 * Interface for custom list
 *
 * @param <A> the type of element in the list
 * @see CustomArrayList
 */
public interface CustomList<A> extends Iterable<A> {
  /**
   * Returns element with specified index in the list
   *
   * @param index the index of an element
   * @return the element at the specified index
   */
  A get(int index);

  /**
   * Adds the specified element to the end of the list
   *
   * @param element element that will be added to the list
   * @throws NullPointerException if the element is null
   */
  void add(A element);

  /**
   * Removes element with specified index from the list
   *
   * @param index the index of an element to be removed
   */
  void remove(int index);

  /**
   * Returns true if the list is empty
   *
   * @return true if the list is empty
   */
  boolean isEmpty();

  /**
   * Returns size of the list
   *
   * @return size of the list
   */
  int size();
}
