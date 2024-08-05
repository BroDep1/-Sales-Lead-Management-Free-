package com.expohack.slm.authentication.configuration;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class AuthenticationConfiguration {

  public final static int STRENGTH = 12;

  private final UserDetailsService userDetailsService;

  private final AuthenticationEventPublisher authenticationEventPublisher;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(STRENGTH);
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    val provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    val manager = new ProviderManager(Collections.singletonList(authenticationProvider()));
    manager.setAuthenticationEventPublisher(authenticationEventPublisher);
    return manager;
  }
}
