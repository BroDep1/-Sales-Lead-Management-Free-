package com.expohack.slm.rabbitMq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String SALES_DTO_QUEUE = "salesDtoQueue";
  public static final String RECOMMENDATION_LEAD_QUEUE = "recommendationLeadQueue";

  @Bean
  public Queue salesDtoQueue() {
    return new Queue(SALES_DTO_QUEUE, true);
  }

  @Bean
  public Queue RecommendationLeadQueue(){
    return new Queue(RECOMMENDATION_LEAD_QUEUE, true);
  }

  @Bean
  MessageConverter jsonMessageConverter(){
    return new Jackson2JsonMessageConverter();
  }
}
