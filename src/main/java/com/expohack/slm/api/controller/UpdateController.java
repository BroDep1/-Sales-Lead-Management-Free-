package com.expohack.slm.api.controller;

import static com.expohack.slm.authentication.modelattribute.AuthenticatedUserModelAttributes.AUTHENTICATED_USER_COMPANY;

import com.expohack.slm.api.service.XlsxFileService;
import com.expohack.slm.authentication.model.dto.AuthenticatedUserResponse;
import com.expohack.slm.authentication.model.dto.CompanyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/upload-data")
public class UpdateController {

  private final XlsxFileService xlsxFileService;

  @Operation(summary = "Загрузить xlsx файл с продажами в сервис")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok", content = @Content),
      @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
          content = @Content)})
  @PostMapping
  public ResponseEntity<Void> uploadXlsx(
      @Parameter(hidden = true)
      @ModelAttribute(AUTHENTICATED_USER_COMPANY) CompanyDto company,
      @RequestParam("xlsx-file") MultipartFile file)
      throws IOException, InvocationTargetException, IllegalAccessException {
    xlsxFileService.send(company, file);
    return ResponseEntity.ok().build();
  }
}
