package com.expohack.slm.model;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesDTO {

  private UUID id;

  private UUID companyId;

  private String clientName;

  private String clientMiddleName;

  private String clientSurname;

  private Date clientBirthdate;

  private String clientMobilePhone;

  private String clientEmail;

  private String clientPassportSeries;

  private String clientPassportNumber;

  private String clientDriverLicenseSeries;

  private String clientDriverLicenseNumber;

  private String product;

  private String productAdditionalData;

  private Date validity;

  private String status;
}
