package com.mipt.rezchikovsergey.sem2.spring_mvp.controller.user;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.LoginDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.JwtResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @PostMapping("/login")
  @Operation(summary = "Login and get JWT token")
  public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto request) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));

    SecurityContextHolder.getContext().setAuthentication(auth);
    AppUserDetails userDetails = (AppUserDetails) auth.getPrincipal();
    String token = jwtUtils.generateToken(auth);

    return ResponseEntity.ok(new JwtResponseDto(token, userDetails.getUsername()));
  }
}
