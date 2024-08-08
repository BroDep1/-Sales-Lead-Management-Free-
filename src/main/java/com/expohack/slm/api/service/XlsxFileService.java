package com.expohack.slm.api.service;

import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.matching.service.MatchingService;
import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.api.utils.ExcelCellsConverter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class XlsxFileService {

  private final MatchingService matchingService;

  public List<SalesDTO> parse(UUID companyId, InputStream stream)
      throws IOException, InvocationTargetException, IllegalAccessException {
    //Получение страницы эксель файла
    var workbook = new XSSFWorkbook(stream);
    var sheet = workbook.getSheetAt(0);

    //Инициализация списка продуктов для матчинга
    List<SalesDTO> salesDTOS = new ArrayList<>();
    var rawIterator = sheet.iterator();

    //Инициализация названий полей в дто и получение доступа для их изменения
    var firstRaw = rawIterator.next(); //skip header
    var firstRawCellIterator = firstRaw.cellIterator();

    LinkedHashMap<String, Method> methods = new LinkedHashMap<>();
    var builderClass = SalesDTO.SalesDTOBuilder.class;

    //Маппинг названий полей из таблицы к дто
    while (firstRawCellIterator.hasNext()){
      var name = firstRawCellIterator.next().getStringCellValue();
      try {
        if (name.equals("companyId")) {
          methods.put(name, builderClass.getMethod(name, UUID.class));
          continue;
        }
        if (name.contains("id") || name.contains("Id")) {
          methods.put(name, builderClass.getMethod(name, UUID.class));
          continue;
        }
        if (name.contains("date") || name.contains("Date")){
          methods.put(name, builderClass.getMethod(name, LocalDate.class));
          continue;
        }
        methods.put(name, builderClass.getMethod(name, String.class));
      }
      catch (NoSuchMethodException exception) {
        log.error("No such field in SalesDto");
      }
    }
    if (!methods.containsKey("companyId")){
      try {
        methods.put("companyId", builderClass.getMethod("companyId", UUID.class));
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
    //Получение итератора по полям
    //Итерация происходит по списку методов, которые заполняют поля ДТО
    var keyIterator = methods.sequencedKeySet().iterator();
    //Итерация по строкам в таблице
    while (rawIterator.hasNext()) {
      //Получение текущей строки, ее итератора и билдера
      var row = rawIterator.next();
      var cellIterator = row.cellIterator();
      var builder = SalesDTO.builder();

      //Итерация по полям строки
      while (cellIterator.hasNext() && keyIterator.hasNext()) {
        //Получение доступа к методу, получение значения поля, и инвокация метода
        var methodInvoke = methods.get(keyIterator.next());
        String value = ExcelCellsConverter.convertToString(cellIterator.next());
        var parameterType = methodInvoke.getParameterTypes()[0];
        if (parameterType.equals(String.class)){
          methodInvoke.invoke(builder, value);
          continue;
        }
        if (parameterType.equals(UUID.class)){
          if (methodInvoke.getName().equals("companyId")){
            methodInvoke.invoke(builder, companyId);
          }
          else {
            try {
              methodInvoke.invoke(builder, UUID.fromString(value));
            }
            catch (IllegalArgumentException exception){
              log.error("invalid uuid format");
            }

          }
          continue;
        }
        if (parameterType.equals(LocalDate.class)){
          try {
            methodInvoke.invoke(builder, LocalDate.parse(value));
          }
          catch (IllegalArgumentException exception){
            log.error("invalid LocalDate format");
          }
        }
      }
      //Добавление обьекта в список ДТО
      salesDTOS.add((builder.build()));
    }
    return salesDTOS;
  }

  public Optional<Void> send(CompanyDto companyDto, MultipartFile file)
      throws IOException, InvocationTargetException, IllegalAccessException {
    var listOfDto = parse(companyDto.id(), file.getInputStream());
    log.info(listOfDto.getFirst().toString());
    matchingService.matchSales(listOfDto);
    return Optional.empty();
  }
}

