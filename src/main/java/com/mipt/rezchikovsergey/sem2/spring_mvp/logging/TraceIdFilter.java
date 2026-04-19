package com.mipt.rezchikovsergey.sem2.spring_mvp.logging;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class TraceIdFilter extends OncePerRequestFilter {
  private final String traceIdHeader;
  private final String mdcKey;

  public TraceIdFilter(AppProperties props) {
    traceIdHeader = props.log().traceIdHeader();
    mdcKey = props.log().mdcKey();
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String traceId = UUID.randomUUID().toString().substring(0, 8);

    try (MDC.MDCCloseable ignored = MDC.putCloseable(mdcKey, traceId)) {
      response.setHeader(traceIdHeader, traceId);
      filterChain.doFilter(request, response);
    }
  }
}
