package com.expohack.slm.authentication.configurer;

import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("UnusedReturnValue")
public class RestLoginConfigurer extends AbstractHttpConfigurer<RestLoginConfigurer, HttpSecurity> {

  public static RestLoginConfigurer restLoginConfigurer() {
    return new RestLoginConfigurer();
  }

  private AuthenticationManager authenticationManager;

  private AntPathRequestMatcher requiresAuthenticationRequestMatcher;

  private AuthenticationSuccessHandler authenticationSuccessHandler;

  private AuthenticationFailureHandler authenticationFailureHandler;

  private RememberMeServices rememberMeServices;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    val filter = new UsernamePasswordAuthenticationFilter(authenticationManager);
    filter.setRequiresAuthenticationRequestMatcher(this.requiresAuthenticationRequestMatcher);
    filter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
    filter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
    filter.setRememberMeServices(this.rememberMeServices);
    http.addFilter(filter);
  }

  public RestLoginConfigurer authenticationManager(AuthenticationManager value) {
    this.authenticationManager = value;
    return this;
  }

  public RestLoginConfigurer requiresAuthenticationRequestMatcher(AntPathRequestMatcher value) {
    this.requiresAuthenticationRequestMatcher = value;
    return this;
  }

  public RestLoginConfigurer authenticationSuccessHandler(AuthenticationSuccessHandler value) {
    this.authenticationSuccessHandler = value;
    return this;
  }

  public RestLoginConfigurer authenticationFailureHandler(AuthenticationFailureHandler value) {
    this.authenticationFailureHandler = value;
    return this;
  }

  public RestLoginConfigurer rememberMeServices(RememberMeServices value) {
    this.rememberMeServices = value;
    return this;
  }
}
