package com.expohack.slm.authentication.handler;

import com.expohack.slm.authentication.model.dto.AuthenticationErrorContent;
import com.expohack.slm.authentication.util.AuthenticationErrorWriter;
import com.expohack.slm.commons.provider.DateTimeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  private final ObjectMapper mapper;

  private final DateTimeProvider dateTimeProvider;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    log.debug("Authentication exception for path %s".formatted(request.getServletPath()),
        authException);
    val errorContent = this.mapToAuthenticationErrorContent(authException);
    AuthenticationErrorWriter.writeErrorResponse(response, errorContent,
        dateTimeProvider, mapper, request.getServletPath());
  }

  private AuthenticationErrorContent mapToAuthenticationErrorContent(
      AuthenticationException authException) {
    if (authException instanceof InsufficientAuthenticationException e) {
      return AuthenticationErrorContent.unauthorized(e.getMessage());
    }
    return AuthenticationErrorContent.internalError();
  }
}
