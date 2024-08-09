package com.expohack.slm.rabbitMq;

import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.matching.service.MatchingService;
import com.expohack.slm.recommendation.model.dto.RecommendationLead;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ConsumerService {

  private final List<RecommendationLead> recommendationLeads = new ArrayList<>();

  private final MatchingService matchingService;

  @RabbitListener(queues = "salesDtoQueue")
  public void consumeAnswer(List<SalesDTO> salesDTOS)  {
    log.info("Лист продаж пришел на обработку.");
    matchingService.matchSales(salesDTOS);
  }

  @RabbitListener(queues = "recommendationLeadQueue")
  public void consumeRecommendations(List<RecommendationLead> leads){
    log.info("Получен список рекомендаций");
    recommendationLeads.addAll(leads);
  }

  public List<RecommendationLead> getAllLead(){
    var res = recommendationLeads.stream().toList();
    recommendationLeads.clear();
    return res;
  }
}
