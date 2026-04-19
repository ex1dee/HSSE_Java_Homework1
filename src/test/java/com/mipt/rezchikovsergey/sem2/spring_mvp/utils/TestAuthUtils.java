package com.mipt.rezchikovsergey.sem2.spring_mvp.utils;

import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;

public class TestAuthUtils {
  public static UUID getContextUserId() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    return ((AppUserDetails) auth.getPrincipal()).getUserId();
  }
}
