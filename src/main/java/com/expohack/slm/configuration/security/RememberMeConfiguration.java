package com.expohack.slm.configuration.security;

import com.expohack.slm.configuration.SlmProperties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@RequiredArgsConstructor
@Configuration
public class RememberMeConfiguration {

  private final SlmProperties properties;

  private final DataSource dataSource;

  private final UserDetailsService userDetailsService;

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    tokenRepository.setDataSource(dataSource);
    tokenRepository.setCreateTableOnStartup(false);
    return tokenRepository;
  }

  @Bean
  public AbstractRememberMeServices rememberMeServices() {
    PersistentTokenBasedRememberMeServices services =
        new PersistentTokenBasedRememberMeServices(properties.rememberMe().key(),
            userDetailsService, persistentTokenRepository());
    services.setTokenValiditySeconds(properties.rememberMe().tokenValiditySeconds());
    services.setUseSecureCookie(properties.rememberMe().useSecureCookie());
    return services;
  }
}
