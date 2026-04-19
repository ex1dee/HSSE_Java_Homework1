package com.mipt.rezchikovsergey.sem2.spring_mvp.security.jwt;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
  private final byte[] secretKey;
  private final long expirationMs;

  public JwtUtils(AppProperties appProperties) {
    secretKey = appProperties.security().jwt().secretKey().getBytes();
    expirationMs = appProperties.security().jwt().expirationMs();
  }

  public String generateToken(Authentication auth) {
    AppUserDetails userDetails = (AppUserDetails) auth.getPrincipal();
    Date issuedAt = new Date();
    Date expiration = new Date(issuedAt.getTime() + expirationMs);

    List<String> roles =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim("userId", userDetails.getUserId().toString())
        .claim("roles", roles)
        .setIssuedAt(issuedAt)
        .setExpiration(expiration)
        .signWith(Keys.hmacShaKeyFor(secretKey), SignatureAlgorithm.HS256)
        .compact();
  }

  @SuppressWarnings("unchecked")
  public JwtPayload parseToken(String token) {
    Claims claims = parseClaimsFromToken(token).getBody();

    return new JwtPayload(
        claims.getSubject(),
        UUID.fromString(claims.get("userId", String.class)),
        claims.get("roles", List.class));
  }

  public boolean isTokenValid(String token) {
    try {
      parseClaimsFromToken(token);
      return true;
    } catch (Exception e) {
      logValidationError(token, e);
      return false;
    }
  }

  private Jws<Claims> parseClaimsFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
  }

  private void logValidationError(String token, Exception e) {
    String maskedToken = token.substring(0, 6) + "***";

    String reason =
        switch (e) {
          case ExpiredJwtException ignored -> "Expired";
          case MalformedJwtException ignored -> "Malformed";
          case UnsupportedJwtException ignored -> "Unsupported";
          default -> "Unknown error: " + e.getMessage();
        };

    log.error("JWT Validation failed [{}] for token: {}", reason, maskedToken);
  }
}
