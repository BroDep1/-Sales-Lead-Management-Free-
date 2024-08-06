package com.expohack.slm.authentication.mapper;

import com.expohack.slm.authentication.model.dto.AuthenticatedUser;
import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.authentication.model.dto.UserCredentialsContainer;
import com.expohack.slm.model.Company;
import com.expohack.slm.authentication.model.entity.User;
import java.util.LinkedHashSet;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapperImpl implements AuthenticationMapper {

  @Override
  public UserCredentialsContainer mapToUserCredentialsContainer(@NonNull User source) {
    return UserCredentialsContainer.builder()
        .id(source.getId())
        .company(mapToCompanyDto(source.getCompany()))
        .username(source.getUsername())
        .password(source.getPassword())
        .name(source.getName())
        .accountNonExpired(source.isAccountNonExpired())
        .accountNonLocked(source.isAccountNonLocked())
        .credentialsNonExpired(source.isCredentialsNonExpired())
        .enabled(source.isEnabled())
        .admin(source.isAdmin())
        .manager(source.isManager())
        .employee(source.isEmployee())
        .grantedAuthorities(new LinkedHashSet<>(source.getAuthorities()))
        .build();
  }

  @Override
  public Optional<UserCredentialsContainer> obtainUserCredentialsContainer(
      Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated() &&
        authentication.getPrincipal() != null &&
        authentication.getPrincipal() instanceof UserCredentialsContainer user) {
      return Optional.of(user);
    }
    return Optional.empty();
  }

  @Override
  public AuthenticatedUser mapToAuthenticatedUser(@NonNull User source) {
    return new AuthenticatedUser(
        source.getId(),
        mapToCompanyDto(source.getCompany()),
        source.getUsername(),
        source.getName(),
        source.isAdmin(),
        source.isManager(),
        source.isEmployee());
  }

  @Override
  public AuthenticatedUser mapToAuthenticatedUser(@NonNull UserCredentialsContainer source) {
    return new AuthenticatedUser(
        source.getId(),
        source.getCompany(),
        source.getUsername(),
        source.getName(),
        source.isAdmin(),
        source.isManager(),
        source.isEmployee());
  }

  @Override
  public CompanyDto mapToCompanyDto(@NonNull Company source) {
    return new CompanyDto(source.getId(), source.getName());
  }
}
