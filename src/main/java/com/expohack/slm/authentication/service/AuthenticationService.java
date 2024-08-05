package com.expohack.slm.authentication.service;

import com.expohack.slm.authentication.model.dto.AuthenticatedUser;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

  AuthenticatedUser getAuthenticatedUser(Authentication authentication);
}
