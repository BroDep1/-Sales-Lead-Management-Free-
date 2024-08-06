package com.expohack.slm.api.controller;

import com.expohack.slm.api.service.XlsxFileService;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

  @PostMapping
  public ResponseEntity<Void> uploadXlsx(@RequestParam("xlsx-file") MultipartFile file)
      throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    xlsxFileService.send(file);
    return ResponseEntity.noContent().build();
  }
}
