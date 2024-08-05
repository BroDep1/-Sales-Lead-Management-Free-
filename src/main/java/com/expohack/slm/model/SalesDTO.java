package com.expohack.slm.model;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SalesDTO {

  private String id;

  private String companyId;

  private String clientName;

  private String clientMiddleName;

  private String clientSurname;

  private String clientBirthdate;

  private String clientMobilePhone;

  private String clientEmail;

  private String clientPassportSeries;

  private String clientPassportNumber;

  private String clientDriverLicenseSeries;

  private String clientDriverLicenseNumber;

  private String product;

  private String productAdditionalData;

  private String validity;

  private String status;
}
