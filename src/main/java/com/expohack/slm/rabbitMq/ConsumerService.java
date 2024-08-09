package com.expohack.slm.rabbitMq;

import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.matching.service.MatchingService;
import com.expohack.slm.recommendation.model.dto.RecommendationLead;
import java.util.ArrayList;
import java.util.List;
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
  public List<RecommendationLead> consumeRecommendations(List<RecommendationLead> leads){
    recommendationLeads.addAll(leads);
    return leads;
  }

  public Object[] getAllLead(){
    var res = recommendationLeads.toArray().clone();
    recommendationLeads.clear();
    return res;
  }
}
