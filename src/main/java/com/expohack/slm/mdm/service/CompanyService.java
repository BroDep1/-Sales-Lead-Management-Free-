package com.expohack.slm.mdm.service;

import com.expohack.slm.authentication.mapper.AuthenticationMapper;
import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.commons.exception.ResourceNotFoundException;
import com.expohack.slm.commons.model.Company;
import com.expohack.slm.commons.repository.CompanyRepository;
import com.expohack.slm.mdm.model.dto.CompanySaveDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository repository;

  private final AuthenticationMapper mapper;

  @Transactional(readOnly = true)
  public Optional<CompanyDto> findById(UUID id) {
    return repository.findById(id).map(mapper::mapToCompanyDto);
  }

  @Transactional(readOnly = true)
  public List<CompanyDto> findAllOrderByName() {
    return repository.findAllByOrderByName().stream()
        .map(mapper::mapToCompanyDto)
        .toList();
  }

  @Transactional
  public CompanyDto create(CompanySaveDto dto) {
    var company = new Company();
    applySaveDtoToEntity(company, dto);
    repository.save(company);
    return mapper.mapToCompanyDto(company);
  }

  @Transactional
  public CompanyDto update(UUID id, CompanySaveDto dto) {
    Company company = repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company Not Found"));
    applySaveDtoToEntity(company, dto);
    return mapper.mapToCompanyDto(company);
  }

  @Transactional
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }

  private void applySaveDtoToEntity(Company entity, CompanySaveDto dto) {
    entity.setName(dto.getName());
    entity.setInn(dto.getInn());
    entity.setNotificationAddress(dto.getNotificationAddress());
  }
}
