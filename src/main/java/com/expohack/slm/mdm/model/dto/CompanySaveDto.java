package com.expohack.slm.mdm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanySaveDto {

  private String name;

  private String inn;

  private String notificationAddress;
}
