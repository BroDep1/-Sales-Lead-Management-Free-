package com.expohack.slm.authentication.service;

import com.expohack.slm.authentication.mapper.AuthenticationMapper;
import com.expohack.slm.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository repository;

  private final AuthenticationMapper mapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    val user = repository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User - %s not found".formatted(username))
    );
    return mapper.mapToUserCredentialsContainer(user);
  }
}
