package com.expohack.slm.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.matching.service.MatchingService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class XlsxFileServiceTest {

  @Mock
  private MatchingService matchingService;

  @InjectMocks
  private XlsxFileService xlsxFileService;

  private InputStream createExcelInputStream() throws Exception {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Test Sheet");

    // Header row
    var headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("clientMobilePhone");
    headerRow.createCell(1).setCellValue("clientEmail");
    headerRow.createCell(2).setCellValue("sendDate");
    headerRow.createCell(3).setCellValue("productName");

    // Data row
    var dataRow = sheet.createRow(1);
    dataRow.createCell(0).setCellValue("1234567890");
    dataRow.createCell(1).setCellValue("test@example.com");
    dataRow.createCell(2).setCellValue("2023-08-01");
    dataRow.createCell(3).setCellValue("Test Product");

    // Write workbook to a ByteArrayOutputStream
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    workbook.write(bos);
    workbook.close();

    // Convert the ByteArrayOutputStream to ByteArrayInputStream
    return new ByteArrayInputStream(bos.toByteArray());
  }

  @Test
  public void testParseValidData() throws Exception {
    InputStream excelStream = createExcelInputStream();
    UUID companyId = UUID.randomUUID();

    List<SalesDTO> salesDTOS = xlsxFileService.parse(companyId, excelStream);

    assertEquals(1, salesDTOS.size());
    SalesDTO salesDTO = salesDTOS.getFirst();
    assertEquals("1234567890", salesDTO.getClientMobilePhone());
    assertEquals("test@example.com", salesDTO.getClientEmail());
    assertEquals("Test Product", salesDTO.getProductName());
    assertEquals(companyId, salesDTO.getCompanyId());
  }

  @Test
  public void testParseMissingHeader() throws Exception {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Test Sheet");

    // Header row with missing field
    var headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("clientMobilePhone");
    headerRow.createCell(1).setCellValue("clientEmail");

    // Data row
    var dataRow = sheet.createRow(1);
    dataRow.createCell(0).setCellValue("1234567890");
    dataRow.createCell(1).setCellValue("test@example.com");

    // Write workbook to a ByteArrayOutputStream
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    workbook.write(bos);
    workbook.close();

    // Convert the ByteArrayOutputStream to ByteArrayInputStream
    InputStream excelStream = new ByteArrayInputStream(bos.toByteArray());

    UUID companyId = UUID.randomUUID();

    List<SalesDTO> salesDTOS = xlsxFileService.parse(companyId, excelStream);

    assertEquals(1, salesDTOS.size());
    SalesDTO salesDTO = salesDTOS.getFirst();
    assertEquals("1234567890", salesDTO.getClientMobilePhone());
    assertEquals("test@example.com", salesDTO.getClientEmail());
    assertNull(salesDTO.getSendDate()); // Missing sendDate should be null
  }

  @Test
  public void testSendCallsMatchingService() throws Exception {
    InputStream excelStream = createExcelInputStream();
    MultipartFile mockFile = mock(MultipartFile.class);
    when(mockFile.getInputStream()).thenReturn(excelStream);

    CompanyDto companyDto = new CompanyDto(UUID.randomUUID(), "name", "inn", "address");

    // Call send method
    xlsxFileService.send(companyDto, mockFile);

    // Verify that MatchingService's matchSales method was called with the correct arguments
    verify(matchingService, times(1)).matchSales(anyList());
  }

  @Test
  public void testInvalidMethodMapping() throws Exception {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Test Sheet");

    // Header row with an invalid field
    var headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("invalidField");

    // Data row
    var dataRow = sheet.createRow(1);
    dataRow.createCell(0).setCellValue("Some Value");

    // Write workbook to a ByteArrayOutputStream
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    workbook.write(bos);
    workbook.close();

    // Convert the ByteArrayOutputStream to ByteArrayInputStream
    InputStream excelStream = new ByteArrayInputStream(bos.toByteArray());

    UUID companyId = UUID.randomUUID();

    List<SalesDTO> salesDTOS = xlsxFileService.parse(companyId, excelStream);

    // Invalid header should cause default method (companyId) to be invoked
    assertEquals(1, salesDTOS.size());
    assertEquals(companyId, salesDTOS.getFirst().getCompanyId());
  }
}
