package com.expohack.slm.service;

import com.expohack.slm.model.SalesDTO;
import com.expohack.slm.utils.ExcelCellsConverter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

  public List<SalesDTO> parse(InputStream stream)
      throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    //Получение страницы эксель файла
    var workbook = new XSSFWorkbook(stream);
    var sheet = workbook.getSheetAt(0);

    //Инициализация списка продуктов для матчинга
    List<SalesDTO> salesDTOS = new ArrayList<>();
    var rawIterator = sheet.iterator();

    //Инициализация названий полей в дто и получение доступа для их изменения
    var firstRaw = rawIterator.next(); //skip header
    var firstRawCellIterator = firstRaw.cellIterator();
    List<Method> methodsList = new LinkedList<>();

    //Маппинг названий полей из таблицы к дто
    while (firstRawCellIterator.hasNext()){
      var name = firstRawCellIterator.next().getStringCellValue().toLowerCase();
      Method method = SalesDTO.SalesDTOBuilder.class.getMethod(name, String.class);
      methodsList.add(method);
    }
    //Получение итератора по полям
    //Итерация происходит по списку методов, которые заполняют поля ДТО
    var namesIterator = methodsList.iterator();

    //Итерация по строкам в таблице
    while (rawIterator.hasNext()) {
      //Получение текущей строки, ее итератора и билдера
      var row = rawIterator.next();
      var cellIterator = row.cellIterator();
      var builder = SalesDTO.builder();

      //Итерация по полям строки
      while (cellIterator.hasNext()) {
        //Получение доступа к методу, получение значения поля, и инвокация метода
        var methodInvoke = namesIterator.next();
        String value = ExcelCellsConverter.convertToString(cellIterator.next());
        methodInvoke.invoke(builder, value);
      }
      //Добавление обьекта в список ДТО
      salesDTOS.add((builder.build()));
    }
    return salesDTOS;
  }

  public Optional<Void> send(MultipartFile file)
      throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

    var listOfDto = parse(file.getInputStream());
    log.info(listOfDto.getFirst().toString());
    //TODO matchingService.add(listOfDto)
    return Optional.empty();
  }

}

