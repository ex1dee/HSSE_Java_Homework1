package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Email;

public class EmailString {
  public @Email String email;

  public enum TestEmail {
    CORRECT_EMAIL1("test.email1@example.com"),
    CORRECT_EMAIL2("1@example.com"),
    CORRECT_EMAIL3("a@b.c"),

    EMAIL_WITHOUT_DOMAIN("test.email@"),
    EMAIL_WITHOUT_USER_NAME("@example.com"),
    EMAIL_WITHOUT_DOT_IN_DOMAIN("test.email@examplecom"),
    EMAIL_WITH_NUMBER_IN_DOMAIN("test.email@example1.com1"),
    EMAIL_WITHOUT_AT_SIGN("test.email.example.com"),
    EMPTY_EMAIL("");

    private final String email;

    TestEmail(String email) {
      this.email = email;
    }
  }

  public EmailString(TestEmail testEmail) {
    this.email = testEmail.email;
  }
}
