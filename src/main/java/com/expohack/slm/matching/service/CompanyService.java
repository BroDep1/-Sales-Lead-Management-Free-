package com.expohack.slm.matching.service;

import com.expohack.slm.matching.model.Company;
import com.expohack.slm.matching.repository.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;

  public Optional<Company> getById(UUID id) {
    log.info("Retrieving company with id {}", id);
    return companyRepository.findById(id);
  }
}
