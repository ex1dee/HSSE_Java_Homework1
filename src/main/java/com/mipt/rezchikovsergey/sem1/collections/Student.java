package com.mipt.rezchikovsergey.sem1.collections;

public class Student {
  int id;
  String name;
  double grade;

  Student(int id, String name, double grade) {
    this.id = id;
    this.name = name;
    this.grade = grade;
  }

  @Override
  public boolean equals(Object other) {
    return other != this
        && (other instanceof Student student)
        && id == student.id
        && name.equals(student.name)
        && grade == student.grade;
  }
}
