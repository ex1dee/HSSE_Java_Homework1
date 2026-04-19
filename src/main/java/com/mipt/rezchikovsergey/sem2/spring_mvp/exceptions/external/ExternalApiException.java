package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.external;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.BaseProjectException;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ExternalApiException extends BaseProjectException {
  private final HttpStatusCode status;

  public ExternalApiException(String message, HttpStatusCode status) {
    super(message);

    this.status = status;
  }
}
