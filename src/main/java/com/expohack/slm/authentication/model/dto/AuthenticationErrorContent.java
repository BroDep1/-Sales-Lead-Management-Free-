package com.expohack.slm.authentication.model.dto;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import jakarta.servlet.http.HttpServletResponse;

public record AuthenticationErrorContent(int status, String error, String message) {

  public static AuthenticationErrorContent badRequest(String message) {
    return new AuthenticationErrorContent(HttpServletResponse.SC_BAD_REQUEST,
        BAD_REQUEST.getReasonPhrase(), message);
  }

  public static AuthenticationErrorContent unauthorized() {
    return unauthorized("Unauthorized access attempt");
  }

  public static AuthenticationErrorContent unauthorized(String message) {
    return new AuthenticationErrorContent(HttpServletResponse.SC_UNAUTHORIZED,
        UNAUTHORIZED.getReasonPhrase(), message);
  }

  public static AuthenticationErrorContent forbidden() {
    return new AuthenticationErrorContent(HttpServletResponse.SC_FORBIDDEN,
        FORBIDDEN.getReasonPhrase(), "Access denied");
  }

  public static AuthenticationErrorContent internalError() {
    return new AuthenticationErrorContent(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
        INTERNAL_SERVER_ERROR.getReasonPhrase(),
        "Internal authentication error, try again later or contact support");
  }
}
