package com.expohack.slm.api.controller;

import static com.expohack.slm.authentication.modelattribute.AuthenticatedUserModelAttributes.AUTHENTICATED_USER_COMPANY;

import com.expohack.slm.api.service.XlsxFileService;
import com.expohack.slm.authentication.model.dto.CompanyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Загрузка файла")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/upload-data")
public class UpdateController {

  private final XlsxFileService xlsxFileService;

  @Operation(summary = "Загрузить xlsx файл с продажами в сервис")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content = @Content),
      @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
          content = @Content)})
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> uploadXlsx(
      @Parameter(hidden = true)
      @ModelAttribute(AUTHENTICATED_USER_COMPANY) CompanyDto company,
      @Parameter(description = "Файл формата XLSX", required = true)
      @RequestParam("xlsx-file") MultipartFile file) throws IOException {
    xlsxFileService.send(company, file);
    return ResponseEntity.ok().build();
  }
}
