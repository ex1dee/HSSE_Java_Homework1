package com.mipt.rezchikovsergey.collections;

import java.util.*;

public class StudentsUtils {
  public static List<Student> findStudentsByGradeRange(
      Map<Integer, Student> map, double minGrade, double maxGrade) {
    List<Student> students = new ArrayList<>();

    for (Map.Entry<Integer, Student> studentEntry : map.entrySet()) {
      Student student = studentEntry.getValue();

      if (student.grade >= minGrade && student.grade <= maxGrade) {
        students.add(student);
      }
    }

    return students;
  }

  public static List<Student> getTopNStudents(TreeMap<Integer, Student> map, int n) {
    List<Student> students = new ArrayList<>();

    for (Map.Entry<Integer, Student> studentEntry : map.reversed().entrySet()) {
      if (students.size() == n) break;

      students.add(studentEntry.getValue());
    }

    return students;
  }
}
