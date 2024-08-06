package com.expohack.slm.authentication.mapper;

import com.expohack.slm.authentication.model.dto.AuthenticatedUser;
import com.expohack.slm.authentication.model.dto.CompanyDto;
import com.expohack.slm.authentication.model.dto.UserCredentialsContainer;
import com.expohack.slm.commons.model.Company;
import com.expohack.slm.authentication.model.entity.User;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;


public interface AuthenticationMapper {

  UserCredentialsContainer mapToUserCredentialsContainer(@NonNull User source);

  Optional<UserCredentialsContainer> obtainUserCredentialsContainer(Authentication authentication);

  AuthenticatedUser mapToAuthenticatedUser(@NonNull User source);

  AuthenticatedUser mapToAuthenticatedUser(@NonNull UserCredentialsContainer source);

  CompanyDto mapToCompanyDto(@NonNull Company source);
}
