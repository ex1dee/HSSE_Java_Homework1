package com.mipt.rezchikovsergey.collections;

import java.util.*;
import org.junit.jupiter.api.Test;

@FunctionalInterface
interface ListOperation {
  void execute(List<Integer> list, int value);
}

public class CollectionPerformanceTest {
  final int OPERATIONS_TEST_COUNT = 10000;

  @Test
  public void compareListsPerformance() {
    ListOperation addToEndOperation = List::addLast;
    ListOperation addToBeginOperation = List::addFirst;
    ListOperation insertToMiddleOperation =
        (list, value) -> {
          if (!list.isEmpty()) list.add(list.size() / 2, value);
          else list.add(value);
        };

    ListOperation getElementOperation = List::get;
    ListOperation removeFromBeginOperation = (list, value) -> list.removeFirst();
    ListOperation removeFromEndOperation = (list, value) -> list.removeLast();

    final List<Integer> list = new ArrayList<>();

    for (int i = 0; i < OPERATIONS_TEST_COUNT; i++) {
      list.add(0);
    }

    long arrayListAddToEndMeasure =
        measureOperation(new ArrayList<>(), addToEndOperation);
    long arrayListAddToBeginMeasure =
        measureOperation(new ArrayList<>(), addToBeginOperation);
    long arrayListInsertToMiddleMeasure =
        measureOperation(new ArrayList<>(), insertToMiddleOperation);

    long arrayListGetElementMeasure =
        measureOperation(new ArrayList<>(list), getElementOperation);
    long arrayListRemoveFromBeginMeasure =
        measureOperation(new ArrayList<>(list), removeFromBeginOperation);
    long arrayListRemoveFromEndMeasure =
        measureOperation(new ArrayList<>(list), removeFromEndOperation);

    long linkedListAddToEndMeasure =
        measureOperation(new LinkedList<>(), addToEndOperation);
    long linkedListAddToBeginMeasure =
        measureOperation(new LinkedList<>(), addToBeginOperation);
    long linkedListInsertToMiddleMeasure =
        measureOperation(new LinkedList<>(), insertToMiddleOperation);

    long linkedListGetElementMeasure =
        measureOperation(new LinkedList<>(list), getElementOperation);
    long linkedListRemoveFromBeginMeasure =
        measureOperation(new LinkedList<>(list), removeFromBeginOperation);
    long linkedListRemoveFromEndMeasure =
        measureOperation(new LinkedList<>(list), removeFromEndOperation);

    System.out.println("Performance comparison of ArrayList and LinkedList in microseconds: ");
    System.out.println("===================================================================");
    System.out.printf("%-20s | %-13s | %-13s\n", "Operation", "ArrayList", "LinkedList");
    System.out.printf(
        "%-20s | %-13d | %-13d\n",
        "Add to end", arrayListAddToEndMeasure, linkedListAddToEndMeasure);
    System.out.printf(
        "%-20s | %-13d | %-13d\n",
        "Add to begin", arrayListAddToBeginMeasure, linkedListAddToBeginMeasure);
    System.out.printf(
        "%-20s | %-13d | %-13d\n",
        "Insert to middle", arrayListInsertToMiddleMeasure, linkedListInsertToMiddleMeasure);
    System.out.printf(
        "%-20s | %-13d | %-13d\n",
        "Get element", arrayListGetElementMeasure, linkedListGetElementMeasure);
    System.out.printf(
        "%-20s | %-13d | %-13d\n",
        "Remove from begin", arrayListRemoveFromBeginMeasure, linkedListRemoveFromBeginMeasure);
    System.out.printf(
        "%-20s | %-13d | %-13d\n",
        "Remove from end", arrayListRemoveFromEndMeasure, linkedListRemoveFromEndMeasure);
  }

  private long measureOperation(List<Integer> list, ListOperation operation) {
    long currentTimeNano = System.nanoTime();

    for (int i = 0; i < OPERATIONS_TEST_COUNT; i++) {
      operation.execute(list, i);
    }

    return (System.nanoTime() - currentTimeNano) / 1000;
  }
}
