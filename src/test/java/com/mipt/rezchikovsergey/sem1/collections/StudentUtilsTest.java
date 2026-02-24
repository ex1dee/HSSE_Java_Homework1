package com.mipt.rezchikovsergey.sem1.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

public class StudentUtilsTest {
  @Test
  public void testFindStudentsByGradeRange() {
    HashMap<Integer, Student> studentHashMap = new HashMap<>();
    TreeMap<Integer, Student> studentTreeMap = new TreeMap<>();

    addStudentsToMap(studentHashMap);
    addStudentsToMap(studentTreeMap);

    for (Student student : StudentsUtils.findStudentsByGradeRange(studentHashMap, 5, 8)) {
      assertTrue(student.id == 2 || student.id == 3);
    }

    for (Student student : StudentsUtils.findStudentsByGradeRange(studentTreeMap, 5, 8)) {
      assertTrue(student.id == 2 || student.id == 3);
    }
  }

  @Test
  public void testGetTopNStudents() {
    TreeMap<Integer, Student> studentTreeMap = new TreeMap<>();

    addStudentsToMap(studentTreeMap);

    List<Student> students = StudentsUtils.getTopNStudents(studentTreeMap, 2);
    assertEquals(2, students.size());
    assertEquals(4, students.get(0).id);
    assertEquals(3, students.get(1).id);
  }

  private void addStudentsToMap(Map<Integer, Student> studentMap) {
    studentMap.put(1, new Student(1, "Alice", 2));
    studentMap.put(2, new Student(2, "Bob", 6));
    studentMap.put(3, new Student(3, "Carl", 7));
    studentMap.put(4, new Student(4, "Denis", 10));
  }
}
