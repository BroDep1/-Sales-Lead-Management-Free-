package com.expohack.slm.commons.configuration.security;

import static org.springframework.http.HttpMethod.GET;

import com.expohack.slm.authentication.configurer.RestLoginConfigurer;
import com.expohack.slm.authentication.handler.RestAuthenticationFailureHandler;
import com.expohack.slm.authentication.handler.RestAuthenticationSuccessHandler;
import com.expohack.slm.authentication.mapper.AuthenticationMapper;
import com.expohack.slm.commons.provider.DateTimeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@RequiredArgsConstructor
@Configuration
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 60 * 60)
@EnableMethodSecurity
public class SecurityConfiguration {

  private final ObjectMapper objectMapper;

  private final DateTimeProvider dateTimeProvider;

  private final AuthenticationMapper mapper;

  private final AuthenticationManager authenticationManager;

  public final static AntPathRequestMatcher SIGN_IN_PATH_MATCHER =
      new AntPathRequestMatcher("/api/v1/auth/sign-in", "POST");

  private final AuthenticationEntryPoint authenticationEntryPoint;

  private final AccessDeniedHandler accessDeniedHandler;

  private final AbstractRememberMeServices rememberMeServices;

  @Value("${server.servlet.session.cookie.name}")
  private String sessionIdCookieName;

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(@NonNull HttpSecurity http)
      throws Exception {

    http
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(GET, "/static/**", "/v3/api-docs/**", "/swagger-ui.html",
                "/swagger-ui/**", "/openapi/openapi.yml", "/error", "/error/**").permitAll()
            .requestMatchers(GET, "/api/v1/auth/user").authenticated()
            .requestMatchers("/api/v1/users/**").authenticated()
            .requestMatchers("/api/v1/dictionaries/**").authenticated()
            .requestMatchers("/api/upload-data/**").authenticated()
            .anyRequest().denyAll())
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .with(RestLoginConfigurer.restLoginConfigurer(), restLogin -> restLogin
            .authenticationManager(authenticationManager)
            .requiresAuthenticationRequestMatcher(SIGN_IN_PATH_MATCHER)
            .authenticationSuccessHandler(
                new RestAuthenticationSuccessHandler(objectMapper, mapper))
            .authenticationFailureHandler(
                new RestAuthenticationFailureHandler(objectMapper, dateTimeProvider))
            .rememberMeServices(rememberMeServices));

    http.exceptionHandling(exceptionHandling -> exceptionHandling
        .authenticationEntryPoint(authenticationEntryPoint)
        .accessDeniedHandler(accessDeniedHandler));

    http.securityContext((securityContext) ->
        securityContext.requireExplicitSave(false));

    http.sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .maximumSessions(10)
        .maxSessionsPreventsLogin(true));

    http.rememberMe(rememberMe -> rememberMe.rememberMeServices(rememberMeServices));

    http.logout(logout -> logout
        .logoutUrl("/api/v1/auth/sign-out").permitAll()
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .addLogoutHandler(rememberMeServices)
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
        .deleteCookies(sessionIdCookieName));

    return http.build();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }
}
