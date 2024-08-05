package com.expohack.slm.authentication.controller;

import static com.expohack.slm.authentication.modelattribute.AuthenticatedUserModelAttributes.AUTHENTICATED_USER_ID;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

import com.expohack.slm.authentication.model.dto.PasswordDto;
import com.expohack.slm.authentication.model.dto.UserCreateRequest;
import com.expohack.slm.authentication.model.dto.UserDto;
import com.expohack.slm.authentication.model.dto.UserSaveDto;
import com.expohack.slm.authentication.service.UserService;
import com.expohack.slm.provider.DateTimeProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Пользователи")
@RequiredArgsConstructor
@RestController
public class UserController {

  private final DateTimeProvider dateTimeProvider;

  private final UserService service;

  @Operation(summary = "Получить пользователя по идентификатору")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserDto.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
      @ApiResponse(responseCode = "404", description = "Пользователь не найден",
          content = @Content)})
  @GetMapping(value = "/api/v1/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
    return ResponseEntity.of(service.findById(id));
  }

  @Operation(summary = "Получить всех пользователей")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content = {
          @Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
  @GetMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Добавить пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created", content =
          {@Content(mediaType = "application/json", schema =
          @Schema(implementation = UserDto.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
      @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content),
      @ApiResponse(responseCode = "409",
          description = "Пользователь с такким логином уже существует", content = @Content)})
  @PostMapping("/api/v1/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateRequest requestDto) {
    return ResponseEntity.status(CREATED)
        .body(service.create(requestDto.getUser(), requestDto.getPassword()));
  }

  @Operation(summary = "Изменить данные пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content =
          {@Content(mediaType = "application/json", schema =
          @Schema(implementation = UserDto.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
      @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content),
      @ApiResponse(responseCode = "409",
          description = "Пользователь с такким логином уже существует", content = @Content)})
  @PutMapping("/api/v1/users/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDto> updateUser(@PathVariable UUID id,
      @Valid @RequestBody UserSaveDto saveDto) {
    return ResponseEntity.ok(service.update(id, saveDto));
  }

  @Operation(summary = "Изменить пароль пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content =
          {@Content(mediaType = "application/json", schema =
          @Schema(implementation = UserDto.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
  @PutMapping("/api/v1/users/{id}/password")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDto> changePassword(@PathVariable UUID id,
      @Valid @RequestBody PasswordDto passwordDto) {
    return ResponseEntity.ok(service.setPassword(id, passwordDto.getPassword()));
  }

  @Operation(summary = "Удалить пользователя")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
      @ApiResponse(responseCode = "400", description = "Нельзя удалить свой аккаунт",
          content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
  @DeleteMapping("/api/v1/users/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteUser(
      @PathVariable UUID id,
      @Parameter(hidden = true) @ModelAttribute(AUTHENTICATED_USER_ID) UUID userId) {
    if (id.equals(userId)) {
      return ResponseEntity.badRequest().build();
    }
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    Map<String, Object> errorResponse = new LinkedHashMap<>();
    errorResponse.put("timestamp", dateTimeProvider.offsetDateTimeNowTruncatedToMillis());
    errorResponse.put("status", CONFLICT.value());
    errorResponse.put("message", "Data integrity violation");
    if (ex.getCause() instanceof ConstraintViolationException constraintViolationException) {
      if ("un_slm_user_username".equals(constraintViolationException.getConstraintName())) {
        errorResponse.put("cause", "Пльзователь с таким логином уже существует");
        errorResponse.put("type", "unique");
        errorResponse.put("fields", new String[]{"name"});
        return ResponseEntity.status(CONFLICT.value()).body(errorResponse);
      }
    }
    return ResponseEntity.status(CONFLICT.value()).body(errorResponse);
  }
}
