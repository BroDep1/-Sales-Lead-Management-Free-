package com.expohack.slm.api.utils;

import java.time.LocalDate;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

@UtilityClass
public class ExcelCellsConverter {
  public String convertToString(Cell cell) {
    if (cell == null) {
      return null;
    }
    if (cell.getCellType() == CellType.STRING) {
      return cell.getStringCellValue();
    }
    return null;
  }
  public UUID convertToUuid(Cell cell){
    if (cell == null) {
      return null;
    }
    if (cell.getCellType() == CellType.STRING) {
      return UUID.fromString(cell.getStringCellValue());
    }
    return null;
  }
  public LocalDate convertToLocalDate(Cell cell){
    if (cell == null) {
      return null;
    }
    if (cell.getCellType() == CellType.NUMERIC) {
      var value = cell.getLocalDateTimeCellValue();
      return value.toLocalDate();
    }
    return null;
  }
}
