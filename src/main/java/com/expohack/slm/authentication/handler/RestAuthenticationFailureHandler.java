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
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
@RequiredArgsConstructor
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper mapper;

  private final DateTimeProvider dateTimeProvider;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    log.debug("Authentication exception for path %s".formatted(request.getServletPath()),
        authException);
    val errorContent = this.obtainFailureAuthenticationErrorContent(authException);
    AuthenticationErrorWriter.writeErrorResponse(response, errorContent,
        dateTimeProvider, mapper, request.getServletPath()
    );
  }

  private AuthenticationErrorContent obtainFailureAuthenticationErrorContent(
      AuthenticationException authException) {
    if (authException instanceof UsernameNotFoundException ||
        authException instanceof BadCredentialsException) {
      return AuthenticationErrorContent.badRequest("Login or password is incorrect");
    } else if (authException instanceof LockedException) {
      return AuthenticationErrorContent.badRequest("Account is locked");
    } else if (authException instanceof DisabledException) {
      return AuthenticationErrorContent.badRequest("Account is disabled");
    } else if (authException instanceof CredentialsExpiredException) {
      return AuthenticationErrorContent.badRequest("Credentials have expired");
    } else if (authException instanceof AccountExpiredException) {
      return AuthenticationErrorContent.badRequest("Account has expired");
    }
    return AuthenticationErrorContent.internalError();
  }
}
