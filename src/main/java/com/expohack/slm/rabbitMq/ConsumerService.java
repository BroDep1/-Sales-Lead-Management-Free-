package com.expohack.slm.rabbitMq;

import com.expohack.slm.api.service.XlsxFileService;
import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.matching.model.Sale;
import com.expohack.slm.matching.service.MatchingService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ConsumerService {

  private final MatchingService matchingService;

  private final XlsxFileService xlsxFileService;

  @RabbitListener(queues = "salesDtoQueue")
  public void consumeAnswer(List<SalesDTO> salesDTOS)  {
    log.info("Лист продаж пришел на обработку.");
    matchingService.matchSales(salesDTOS);
  }
}
