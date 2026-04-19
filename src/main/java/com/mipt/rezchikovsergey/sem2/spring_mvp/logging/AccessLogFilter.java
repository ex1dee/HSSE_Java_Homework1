package com.mipt.rezchikovsergey.sem2.spring_mvp.logging;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(2)
@Slf4j
public class AccessLogFilter extends OncePerRequestFilter {
  private final String mdcKey;

  public AccessLogFilter(AppProperties props) {
    this.mdcKey = props.log().mdcKey();
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    long startTime = System.currentTimeMillis();

    try {
      filterChain.doFilter(request, response);
    } finally {
      long duration = System.currentTimeMillis() - startTime;

      log.info(
          "HTTP {} {} -> status={} timeMs={} trace={}",
          request.getMethod(),
          request.getRequestURI(),
          response.getStatus(),
          duration,
          MDC.get(mdcKey));
    }
  }
}
