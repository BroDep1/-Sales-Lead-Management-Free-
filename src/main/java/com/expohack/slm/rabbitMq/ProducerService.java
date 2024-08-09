package com.expohack.slm.rabbitMq;

import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.matching.model.Sale;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

  private final RabbitTemplate rabbitTemplate;

  @SneakyThrows
  public void sendListOfSalesDto(List<SalesDTO> salesDTOS, CompanyDto companyDto) {
    rabbitTemplate.convertAndSend("salesDtoQueue", salesDTOS);
    log.info("Список продуктов отправлен матчиться. Файл загружен компанией {}.", companyDto.name());
  }

  public void sendRecommendationLead(){
    rabbitTemplate.convertAndSend("recommendationLeadQueue");
    log.info("New recommendation lead {} sent to controller");
  }
}

