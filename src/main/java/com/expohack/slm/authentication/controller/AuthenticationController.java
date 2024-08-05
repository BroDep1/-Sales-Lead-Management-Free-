package com.expohack.slm.authentication.controller;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
import static org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices.DEFAULT_PARAMETER;

import com.expohack.slm.authentication.model.dto.AuthenticatedUserResponse;
import com.expohack.slm.authentication.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Авторизация")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(summary = "Получить информацию о текущем аутентифицированном пользователе")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = AuthenticatedUserResponse.class))}),
      @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
          content = @Content)})
  @GetMapping(value = "/api/v1/auth/user", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticatedUserResponse> getAuthenticatedUser(
      Authentication authentication) {
    return ResponseEntity.ok(
        new AuthenticatedUserResponse(service.getAuthenticatedUser(authentication)));
  }

  @Operation(summary = "Войти в систему",
      description = "Демопользователи: admin - 1312cc7b, demo1 - d9e1872b, demo2 - 399166b3," +
          " demo3 - a4b33713, demo4 - 72ba79f6, demo5 - 5a5bfe45")
  @Parameter(name = SPRING_SECURITY_FORM_USERNAME_KEY, description = "Имя пользователя",
      schema = @Schema(type = "string", example = "demo2"))
  @Parameter(name = SPRING_SECURITY_FORM_PASSWORD_KEY, description = "Пароль",
      schema = @Schema(type = "string", example = "399166b3"))
  @Parameter(name = DEFAULT_PARAMETER, description = "Запомнить меня",
      schema = @Schema(type = "boolean", example = "false"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = AuthenticatedUserResponse.class))}),
      @ApiResponse(responseCode = "400",
          description = "Login or password is incorrect, Account is disabled or locked or expired",
          content = @Content)})
  @PostMapping(value = "/api/v1/auth/sign-in", consumes = {
      MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public void openApiDocsSignIn() {
    throw new IllegalStateException(
        "This method shouldn't be called. It's implemented by Spring Security filters."
    );
  }

  @Operation(summary = "Выйти из системы")
  @ApiResponse(responseCode = "200", description = "Ok", content = @Content)
  @PostMapping("/api/v1/auth/sign-out")
  public void openApiDocsSignOut() {
    throw new IllegalStateException(
        "This method shouldn't be called. It's implemented by Spring Security filters."
    );
  }
}
