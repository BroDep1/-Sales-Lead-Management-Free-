package com.expohack.slm.matching.service;

import com.expohack.slm.matching.model.Company;
import com.expohack.slm.matching.model.Error;
import com.expohack.slm.matching.repository.ErrorRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ErrorService {

  private final ErrorRepository errorRepository;

  public void save(Error error) {
    errorRepository.save(error);
    log.info("Saved error with id {}", error.getId());
  }

  public void createError(String message, String productName, LocalDate sendDate, Company company) {
      Error error = new Error();
      error.setMessage(message);
      error.setCompany(company);
      error.setSendDate(sendDate);
      error.setProductName(productName);
      save(error);
  }
}
