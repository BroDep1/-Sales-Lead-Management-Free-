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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  private final ObjectMapper mapper;

  private final DateTimeProvider dateTimeProvider;

  @Override
  public void handle(HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.debug("Access denied exception for path %s".formatted(request.getServletPath()),
        accessDeniedException);
    AuthenticationErrorWriter.writeErrorResponse(response, AuthenticationErrorContent.forbidden(),
        dateTimeProvider, mapper, request.getServletPath());
  }
}
