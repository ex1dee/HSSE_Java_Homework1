package com.mipt.rezchikovsergey.sem2.spring_mvp.security.jwt;

import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      String token = headerAuth.substring(7);

      if (jwtUtils.isTokenValid(token)) {
        JwtPayload payload = jwtUtils.parseToken(token);
        AppUserDetails userDetails =
            new AppUserDetails(
                payload.userId(),
                payload.username(),
                "",
                payload.roles().stream().map(SimpleGrantedAuthority::new).toList());

        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }
}
