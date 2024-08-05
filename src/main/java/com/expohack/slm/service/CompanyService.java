package com.expohack.slm.service;

import com.expohack.slm.model.Company;
import com.expohack.slm.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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
