package com.mipt.rezchikovsergey.annotations_reflections;

import static org.junit.jupiter.api.Assertions.*;

import com.mipt.rezchikovsergey.annotations_reflections.test_classes.*;
import org.junit.jupiter.api.Test;

class ValidatorTest {
  @Test
  public void checkNotNullValidation() {
    assertValid(new NotNullField());
    assertInvalid(new NullField());
  }

  @Test
  public void checkStringSizeValidation() {
    assertValid(new StringSize(StringSize.TestString.EXACT_MIN));
    assertValid(new StringSize(StringSize.TestString.EXACT_MAX));
    assertValid(new StringSize(StringSize.TestString.MIDDLE));

    assertInvalid(new StringSize(StringSize.TestString.TOO_SHORT));
    assertInvalid(new StringSize(StringSize.TestString.TOO_LONG));
    assertInvalid(new StringSize(StringSize.TestString.EMPTY));
  }

  @Test
  public void shouldIgnoreSizeValidation() {
    assertValid(new SizeOfNull());
    assertValid(new NotStringSize());
  }

  @Test
  public void checkIntegerRangeValidation() {
    assertValid(new IntegerRange(IntegerRange.TestInteger.EXACT_MIN));
    assertValid(new IntegerRange(IntegerRange.TestInteger.EXACT_MAX));
    assertValid(new IntegerRange(IntegerRange.TestInteger.MIDDLE));

    assertInvalid(new IntegerRange(IntegerRange.TestInteger.TOO_LOW));
    assertInvalid(new IntegerRange(IntegerRange.TestInteger.TOO_HIGH));
  }

  @Test
  public void shouldIgnoreRangeValidation() {
    assertValid(new RangeOfNull());
    assertValid(new NotIntegerRange());
  }

  @Test
  public void checkEmailStringValidation() {
    assertValid(new EmailString(EmailString.TestEmail.CORRECT_EMAIL1));
    assertValid(new EmailString(EmailString.TestEmail.CORRECT_EMAIL2));
    assertValid(new EmailString(EmailString.TestEmail.CORRECT_EMAIL3));

    assertInvalid(new EmailString(EmailString.TestEmail.EMAIL_WITHOUT_DOMAIN));
    assertInvalid(new EmailString(EmailString.TestEmail.EMAIL_WITHOUT_USER_NAME));
    assertInvalid(new EmailString(EmailString.TestEmail.EMAIL_WITHOUT_DOT_IN_DOMAIN));
    assertInvalid(new EmailString(EmailString.TestEmail.EMAIL_WITH_NUMBER_IN_DOMAIN));
    assertInvalid(new EmailString(EmailString.TestEmail.EMAIL_WITHOUT_AT_SIGN));
    assertInvalid(new EmailString(EmailString.TestEmail.EMPTY_EMAIL));
  }

  @Test
  public void shouldIgnoreEmailValidation() {
    assertValid(new NullEmail());
    assertValid(new NotStringEmail());
  }

  @Test
  public void checkMixedAnnotations() {
    assertValid(new FullValidAnnotated());
    assertInvalid(new FullInvalidAnnotated());
  }

  @Test
  public void checkMultipleErrors() {
    MultipleInvalid testClass = new MultipleInvalid();
    ValidationResult result = Validator.validate(testClass);

    assertValidation(result, false, 4);
  }

  private void assertValid(Object testClass) {
    assertValidation(Validator.validate(testClass), true, 0);
  }

  private void assertInvalid(Object testClass) {
    assertValidation(Validator.validate(testClass), false, 1);
  }
  
  private void assertValidation(ValidationResult result, boolean expectedValid, int expectedErrorsNum) {
    assertEquals(expectedValid, result.isValid());
    assertEquals(expectedErrorsNum, result.getErrors().size());
  }
}
