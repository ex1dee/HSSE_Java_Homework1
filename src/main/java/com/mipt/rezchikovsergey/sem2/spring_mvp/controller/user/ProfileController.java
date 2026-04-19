package com.mipt.rezchikovsergey.sem2.spring_mvp.controller.user;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.ProfileResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  @Operation(summary = "Get current user profile")
  public ResponseEntity<ProfileResponseDto> getProfile(
      @AuthenticationPrincipal AppUserDetails userDetails) {
    List<String> authorities =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    List<String> roles =
        authorities.stream()
            .filter(auth -> auth.startsWith("ROLE_"))
            .map(auth -> auth.replace("ROLE_", ""))
            .toList();

    List<String> permissions =
        authorities.stream().filter(auth -> !auth.startsWith("ROLE_")).toList();

    return ResponseEntity.ok(new ProfileResponseDto(userDetails.getUsername(), roles, permissions));
  }
}
