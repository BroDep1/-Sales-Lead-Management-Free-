package com.expohack.slm.mdm.controller;

import static com.expohack.slm.authentication.modelattribute.AuthenticatedUserModelAttributes.AUTHENTICATED_USER_COMPANY;
import static org.springframework.http.HttpStatus.CREATED;

import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.commons.provider.DateTimeProvider;
import com.expohack.slm.mdm.model.dto.CompanySaveDto;
import com.expohack.slm.mdm.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Компании")
@RequiredArgsConstructor
@RestController
public class CompanyController {

  private final DateTimeProvider dateTimeProvider;

  private final CompanyService service;

  @Operation(summary = "Получить компанию по идентификатору")
  @GetMapping(value = "/api/v1/companies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompanyDto> getCompanyById(@PathVariable UUID id) {
    return ResponseEntity.of(service.findById(id));
  }

  @Operation(summary = "Получить все компании")
  @GetMapping(value = "/api/v1/companies", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CompanyDto>> getAllCompanies() {
    return ResponseEntity.ok(service.findAllOrderByName());
  }

  @Operation(summary = "Добавить компанию")
  @PostMapping("/api/v1/companies")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CompanySaveDto dto) {
    return ResponseEntity.status(CREATED)
        .body(service.create(dto));
  }

  @Operation(summary = "Изменить данные компанию")
  @PutMapping("/api/v1/companies/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CompanyDto> updateCompany(@PathVariable UUID id,
      @Valid @RequestBody CompanySaveDto dto) {
    return ResponseEntity.ok(service.update(id, dto));
  }

  @Operation(summary = "Удалить компанию")
  @DeleteMapping("/api/v1/companies/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteCompany(
      @PathVariable UUID id,
      @Parameter(hidden = true) @ModelAttribute(AUTHENTICATED_USER_COMPANY) CompanyDto companyDto) {
    if (id.equals(companyDto.id())) {
      return ResponseEntity.badRequest().build();
    }
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
