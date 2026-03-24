package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.UnknownViewModeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class PreferencesController {
  private final AppProperties appProperties;

  private static final String VIEW_DETAILED_MODE = "detailed";
  private static final String VIEW_COMPACT_MODE = "compact";

  @GetMapping("/view")
  @Operation(
      summary = "Get view preference",
      description =
          "Reads the UI display mode (compact/detailed) from the browser cookies. Defaults to 'detailed' if no cookie is present.")
  @ApiResponse(responseCode = "200", description = "Current view mode string")
  public ResponseEntity<String> getViewPreference(
      @CookieValue(
              value = "${app.cookie.view-mode.name}",
              defaultValue = "${app.cookie.view-mode.default-value}")
          String mode) {
    return ResponseEntity.ok(mode);
  }

  @PostMapping
  @Operation(summary = "Set UI mode")
  @ApiResponse(responseCode = "200", description = "Cookie set")
  @ApiResponse(responseCode = "400", description = "Unknown mode (not 'detailed'/'compact')")
  public ResponseEntity<Void> setViewPreference(
      @RequestParam String mode, HttpServletResponse response) {
    if (!mode.equals(VIEW_DETAILED_MODE) && !mode.equals(VIEW_COMPACT_MODE)) {
      throw new UnknownViewModeException(mode);
    }

    Cookie cookie = new Cookie(appProperties.cookie().viewMode().name(), mode);
    cookie.setPath("/");
    cookie.setMaxAge(appProperties.cookie().viewMode().maxAge());
    cookie.setHttpOnly(true);

    response.addCookie(cookie);

    return ResponseEntity.ok().build();
  }
}
