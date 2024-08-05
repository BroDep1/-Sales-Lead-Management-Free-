package com.expohack.slm.authentication.service;

import com.expohack.slm.authentication.mapper.AuthenticationMapper;
import com.expohack.slm.authentication.model.dto.AuthenticatedUser;
import com.expohack.slm.authentication.model.dto.UserCredentialsContainer;
import com.expohack.slm.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository repository;

  private final AuthenticationMapper mapper;

  @Transactional(readOnly = true)
  @Override
  public AuthenticatedUser getAuthenticatedUser(Authentication authentication) {
    return mapper.obtainUserCredentialsContainer(authentication)
        .map(UserCredentialsContainer::getUsername)
        .flatMap(repository::findByUsername)
        .map(mapper::mapToAuthenticatedUser)
        .orElse(null);
  }
}
