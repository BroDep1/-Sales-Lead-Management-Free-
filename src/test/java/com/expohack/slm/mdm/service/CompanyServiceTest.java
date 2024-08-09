package com.expohack.slm.mdm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.expohack.slm.authentication.mapper.AuthenticationMapper;
import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.commons.exception.ResourceNotFoundException;
import com.expohack.slm.commons.model.Company;
import com.expohack.slm.commons.repository.CompanyRepository;
import com.expohack.slm.mdm.model.dto.CompanySaveDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private AuthenticationMapper authenticationMapper;

  @InjectMocks
  private CompanyService companyService;

  private UUID companyId;
  private Company company;
  private CompanyDto companyDto;
  private CompanySaveDto companySaveDto;

  @BeforeEach
  public void setUp() {
    companyId = UUID.randomUUID();
    company = new Company();
    company.setId(companyId);
    company.setName("Test Company");
    company.setInn("123456789");
    company.setNotificationAddress("test@example.com");

    companyDto = new CompanyDto(companyId, "Test Company", "inn", "address");

    companySaveDto = new CompanySaveDto("Updated Company", "987654321", "updated@example.com");
  }

  @Test
  @Transactional(readOnly = true)
  public void testFindById_CompanyExists() {
    when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(authenticationMapper.mapToCompanyDto(company)).thenReturn(companyDto);

    Optional<CompanyDto> result = companyService.findById(companyId);

    assertTrue(result.isPresent());
    assertEquals(companyDto, result.get());
    verify(companyRepository, times(1)).findById(companyId);
    verify(authenticationMapper, times(1)).mapToCompanyDto(company);
  }

  @Test
  @Transactional(readOnly = true)
  public void testFindById_CompanyDoesNotExist() {
    when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

    Optional<CompanyDto> result = companyService.findById(companyId);

    assertFalse(result.isPresent());
    verify(companyRepository, times(1)).findById(companyId);
    verify(authenticationMapper, never()).mapToCompanyDto(any());
  }

  @Test
  @Transactional(readOnly = true)
  public void testFindAllOrderByName() {
    List<Company> companies = List.of(company);
    List<CompanyDto> companyDtos = List.of(companyDto);

    when(companyRepository.findAllByOrderByName()).thenReturn(companies);
    when(authenticationMapper.mapToCompanyDto(company)).thenReturn(companyDto);

    List<CompanyDto> result = companyService.findAllOrderByName();

    assertEquals(companyDtos, result);
    verify(companyRepository, times(1)).findAllByOrderByName();
    verify(authenticationMapper, times(1)).mapToCompanyDto(company);
  }

  @Test
  @Transactional
  public void testCreate() {
    when(authenticationMapper.mapToCompanyDto(any())).thenReturn(companyDto);

    CompanyDto result = companyService.create(companySaveDto);

    assertEquals(companyDto, result);
    verify(companyRepository, times(1)).save(any(Company.class));
    verify(authenticationMapper, times(1)).mapToCompanyDto(any(Company.class));
  }

  @Test
  @Transactional
  public void testUpdate_CompanyExists() {
    when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(authenticationMapper.mapToCompanyDto(company)).thenReturn(companyDto);

    CompanyDto result = companyService.update(companyId, companySaveDto);

    assertEquals(companyDto, result);
    verify(companyRepository, times(1)).findById(companyId);
    verify(authenticationMapper, times(1)).mapToCompanyDto(company);
  }

  @Test
  @Transactional
  public void testUpdate_CompanyDoesNotExist() {
    when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> companyService.update(companyId, companySaveDto));

    verify(companyRepository, times(1)).findById(companyId);
    verify(companyRepository, never()).save(any());
    verify(authenticationMapper, never()).mapToCompanyDto(any());
  }

  @Test
  @Transactional
  public void testDeleteById() {
    doNothing().when(companyRepository).deleteById(companyId);

    companyService.deleteById(companyId);

    verify(companyRepository, times(1)).deleteById(companyId);
  }
}
