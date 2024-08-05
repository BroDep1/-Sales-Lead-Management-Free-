package com.expohack.slm.authentication.modelattribute;

import com.expohack.slm.authentication.mapper.AuthenticationMapper;
import com.expohack.slm.authentication.model.dto.AuthenticatedUser;
import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.authentication.model.dto.UserCredentialsContainer;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@RequiredArgsConstructor
@ControllerAdvice
public class AuthenticatedUserModelAttributes {

  public static final String AUTHENTICATED_USER_ID = "authenticatedUserId";

  public static final String AUTHENTICATED_USER_COMPANY = "authenticatedUserCompany";

  public static final String AUTHENTICATED_USER_MODEL = "authenticatedUserModel";

  private final AuthenticationMapper mapper;

  @ModelAttribute(name = AUTHENTICATED_USER_ID, binding = false)
  public Optional<UUID> authenticatedUserId(Authentication authentication) {
    return mapper.obtainUserCredentialsContainer(authentication)
        .map(UserCredentialsContainer::getId);
  }

  @ModelAttribute(name = AUTHENTICATED_USER_COMPANY, binding = false)
  public Optional<CompanyDto> authenticatedUserCompany(Authentication authentication) {
    return mapper.obtainUserCredentialsContainer(authentication)
        .map(UserCredentialsContainer::getCompany);
  }

  @ModelAttribute(name = AUTHENTICATED_USER_MODEL, binding = false)
  public Optional<AuthenticatedUser> authenticatedUserInfo(Authentication authentication) {
    return mapper.obtainUserCredentialsContainer(authentication)
        .map(mapper::mapToAuthenticatedUser);
  }
}
