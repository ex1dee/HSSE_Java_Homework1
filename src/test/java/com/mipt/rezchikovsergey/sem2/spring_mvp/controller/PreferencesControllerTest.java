package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mipt.rezchikovsergey.sem2.spring_mvp.BaseMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.MyWebMvcTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;

@MyWebMvcTest(PreferencesController.class)
public class PreferencesControllerTest extends BaseMvcTest {
  @Test
  void shouldReturnDefaultViewModeWhenNoCookie() throws Exception {
    mockMvc
        .perform(get("/api/preferences/view"))
        .andExpect(status().isOk())
        .andExpect(content().string(appProperties.cookie().viewMode().defaultValue()));
  }

  @Test
  void shouldReturnModeFromCookie() throws Exception {
    String cookieName = appProperties.cookie().viewMode().name();

    mockMvc
        .perform(get("/api/preferences/view").cookie(new Cookie(cookieName, "compact")))
        .andExpect(status().isOk())
        .andExpect(content().string("compact"));
  }

  @Test
  void shouldSetCookieInResponse() throws Exception {
    String cookieName = appProperties.cookie().viewMode().name();
    int maxAge = appProperties.cookie().viewMode().maxAge();

    mockMvc
        .perform(post("/api/preferences").param("mode", "compact"))
        .andExpect(status().isOk())
        .andExpect(cookie().value(cookieName, "compact"))
        .andExpect(cookie().path(cookieName, "/"))
        .andExpect(cookie().httpOnly(cookieName, true))
        .andExpect(cookie().maxAge(cookieName, maxAge));
  }

  @Test
  void shouldReturn400ForUnknownMode() throws Exception {
    mockMvc
        .perform(post("/api/preferences").param("mode", "ultra-wide-4k-mode"))
        .andExpect(status().isBadRequest());
  }
}
