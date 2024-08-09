package com.expohack.slm.api.controller;

import static com.expohack.slm.authentication.modelattribute.AuthenticatedUserModelAttributes.AUTHENTICATED_USER_COMPANY;

import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.rabbitMq.ConsumerService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/results")
public class ResultController {
  private final ConsumerService consumerService;

  public Object[] getAllRecommendations(
      @Parameter(hidden = true)
      @ModelAttribute(AUTHENTICATED_USER_COMPANY) CompanyDto company){
    return consumerService.getAllLead();
  }
}
