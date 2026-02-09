package com.mipt.rezchikovsergey.sem1.model;

public class Human {
  private String firstName;
  private String lastName;
  private boolean isEmployed;
  private int age;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public boolean isEmployed() {
    return isEmployed;
  }

  public void setEmployed(boolean employed) {
    isEmployed = employed;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
