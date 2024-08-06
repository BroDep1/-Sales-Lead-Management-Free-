package com.expohack.slm.authentication.service;

import com.expohack.slm.authentication.mapper.AuthenticationMapper;
import com.expohack.slm.authentication.model.dto.UserDto;
import com.expohack.slm.authentication.model.dto.UserSaveDto;
import com.expohack.slm.model.Company;
import com.expohack.slm.authentication.model.entity.User;
import com.expohack.slm.repository.CompanyRepository;
import com.expohack.slm.authentication.repository.UserRepository;
import com.expohack.slm.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  private final CompanyRepository companyRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationMapper mapper;

  @Transactional(readOnly = true)
  public Optional<UserDto> findById(UUID id) {
    return repository.findById(id).map(this::map);
  }

  @Transactional(readOnly = true)
  public List<UserDto> findAll() {
    return repository.findAll().stream()
        .map(this::map)
        .toList();
  }

  @Transactional()
  public UserDto create(UserSaveDto saveDto, String rawPassword) {
    val company = companyRepository
        .findById(saveDto.getCompanyId())
        .orElseThrow(() -> new ResourceNotFoundException("Компания не найдена"));
    val entity = new User();
    applySaveDtoToEntity(entity, saveDto, company);
    entity.setAccountNonExpired(true);
    entity.setAccountNonLocked(true);
    entity.setCredentialsNonExpired(true);
    entity.setEnabled(true);
    entity.setPassword(passwordEncoder.encode(rawPassword));
    return map(repository.save(entity));
  }

  @Transactional()
  public UserDto update(UUID id, UserSaveDto saveDto) {
    val company = companyRepository
        .findById(saveDto.getCompanyId())
        .orElseThrow(() -> new ResourceNotFoundException("Компания не найдена"));
    User entity = repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    applySaveDtoToEntity(entity, saveDto, company);
    return map(entity);
  }

  @Transactional()
  public UserDto setPassword(UUID id, String rawPassword) {
    User entity = repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    entity.setPassword(passwordEncoder.encode(rawPassword));
    return map(entity);
  }

  @Transactional()
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }

  private UserDto map(User entity) {
    return UserDto.builder()
        .id(entity.getId())
        .company(mapper.mapToCompanyDto(entity.getCompany()))
        .username(entity.getUsername())
        .name(entity.getName())
        .admin(entity.isAdmin())
        .manager(entity.isManager())
        .employee(entity.isEmployee())
        .build();
  }

  private void applySaveDtoToEntity(User entity, UserSaveDto dto, Company company) {
    entity.setCompany(company);
    entity.setUsername(dto.getUsername());
    entity.setName(dto.getName());
    entity.setAdmin(dto.isAdmin());
    entity.setManager(dto.isManager());
    entity.setEmployee(dto.isEmployee());
  }
}
