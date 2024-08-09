package com.expohack.slm.api.service;

import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.matching.service.MatchingService;
import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.api.utils.ExcelCellsConverter;
import com.expohack.slm.rabbitMq.ProducerService;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class XlsxFileService {

  private final ProducerService producerService;

  private final MatchingService matchingService;

  @SneakyThrows
  public List<SalesDTO> parse(UUID companyId, InputStream stream) {
    //Получение страницы эксель файла
    var workbook = new XSSFWorkbook(stream);
    var sheet = workbook.getSheetAt(0);

    //Инициализация списка продуктов для матчинга
    List<SalesDTO> salesDTOS = new ArrayList<>();
    var rawIterator = sheet.iterator();

    //Инициализация названий полей в дто и получение доступа для их изменения
    var firstRaw = rawIterator.next(); //skip header
    var firstRawCellIterator = firstRaw.cellIterator();

    List<Method> methods = new LinkedList<>();
    var builderClass = SalesDTO.SalesDTOBuilder.class;

    //Маппинг названий полей из таблицы к дто
    while (firstRawCellIterator.hasNext()){
      String name = firstRawCellIterator.next().getStringCellValue();
      try {
        if (name.contains("Id") || name.equals("id")) {
          methods.add(builderClass.getMethod(name, UUID.class));
          continue;
        }
        if (name.contains("Date")){
          methods.add(builderClass.getMethod(name, LocalDate.class));
          continue;
        }
        methods.add(builderClass.getMethod(name, String.class));
      }
      catch (NoSuchMethodException exception) {
        log.error("No such field in SalesDto");
        methods.add(builderClass.getMethod("companyId", UUID.class));
      }
    }
    //Получение итератора по полям
    //Итерация происходит по списку методов, которые заполняют поля ДТО

    //Итерация по строкам в таблице
    while (rawIterator.hasNext()) {
      //Получение текущей строки, ее итератора и билдера
      var row = rawIterator.next();
      var cellIterator = row.cellIterator();
      var builder = SalesDTO.builder();
      builderClass.getMethod("companyId", UUID.class).invoke(builder, companyId);
      var methodIterator = methods.iterator();
      //Итерация по полям строки
      while (cellIterator.hasNext() && methodIterator.hasNext()) {
        //Получение доступа к методу, получение значения поля, и инвокация метода
        var methodInvoke = methodIterator.next();
        var parameterType = methodInvoke.getParameterTypes()[0];
        var curCell = cellIterator.next();
        if (parameterType.equals(String.class)){
          var value = ExcelCellsConverter.convertToString(curCell);
          methodInvoke.invoke(builder, value);
          continue;
        }
        if (parameterType.equals(UUID.class) && !methodInvoke.getName().equals("companyId")){
            var uuid = ExcelCellsConverter.convertToUuid(curCell);
            methodInvoke.invoke(builder, uuid);
          continue;
        }
        if (parameterType.equals(LocalDate.class)){
            var date = ExcelCellsConverter.convertToLocalDate(curCell);
            methodInvoke.invoke(builder, date);
        }
      }
      //Добавление обьекта в список ДТО
      salesDTOS.add((builder.build()));
    }
    return salesDTOS;
  }

  public Optional<Void> send(CompanyDto companyDto, MultipartFile file)
      throws IOException {
    var listOfDto = parse(companyDto.id(), file.getInputStream());
    for (SalesDTO salesDTO : listOfDto) {
      log.info(salesDTO.toString());
    }
    producerService.sendListOfSalesDto(listOfDto, companyDto);
    return Optional.empty();
  }
}

