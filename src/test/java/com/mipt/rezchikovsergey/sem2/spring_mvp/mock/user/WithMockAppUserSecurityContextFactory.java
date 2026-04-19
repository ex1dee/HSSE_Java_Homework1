package com.mipt.rezchikovsergey.sem2.spring_mvp.mock.user;

import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockAppUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockAppUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockAppUser annotation) {
    AppUserDetails userDetails =
        new AppUserDetails(
            UUID.fromString("00000000-0000-0000-0000-000000000000"),
            annotation.username(),
            "password",
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    Authentication auth =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(auth);

    return context;
  }
}
