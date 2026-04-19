package com.mipt.rezchikovsergey.sem2.spring_mvp.security.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PepperedPasswordEncoder implements PasswordEncoder {
  private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();
  private final String pepper;

  @Override
  public String encode(CharSequence rawPassword) {
    return delegate.encode(rawPassword + pepper);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return delegate.matches(rawPassword + pepper, encodedPassword);
  }
}
