package com.expohack.slm.authentication.util;

import com.expohack.slm.authentication.model.dto.AuthenticationErrorContent;
import com.expohack.slm.provider.DateTimeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.springframework.http.MediaType;

public class AuthenticationErrorWriter {

  public static void writeErrorResponse(
      HttpServletResponse response,
      AuthenticationErrorContent content,
      DateTimeProvider dateTimeProvider,
      ObjectMapper mapper,
      String path) throws IOException {
    response.setStatus(content.status());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    LinkedHashMap<String, Object> error = new LinkedHashMap<>();
    error.put("timestamp", dateTimeProvider.offsetDateTimeNowTruncatedToMillis());
    error.put("status", content.status());
    error.put("error", content.error());
    error.put("message", content.message());
    error.put("path", path);
    response.getWriter().write(mapper.writeValueAsString(error));
  }
}
