package com.expohack.slm.authentication.model.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

  private UUID id;

  private CompanyDto company;

  private String username;

  private String name;

  private boolean admin;

  private boolean manager;

  private boolean employee;
}
