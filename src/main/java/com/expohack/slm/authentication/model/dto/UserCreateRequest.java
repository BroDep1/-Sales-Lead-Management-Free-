package com.expohack.slm.authentication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserCreateRequest {

  private UserSaveDto user;

  private String password;
}
