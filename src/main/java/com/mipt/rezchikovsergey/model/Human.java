package com.mipt.rezchikovsergey.model;

public class Human {
    private String firstName;
    private String lastName;
    private boolean isEmployed;
    private int age;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public int getAge() {
        return age;
    }
}