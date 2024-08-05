package com.expohack.slm.model;

import java.time.LocalDate;
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

  private LocalDate clientBirthdate;

  private String clientMobilePhone;

  private String clientEmail;

  private String clientPassportSeries;

  private String clientPassportNumber;

  private String clientDriverLicenseSeries;

  private String clientDriverLicenseNumber;

  private String productName;

  private String productAdditionalData;

  private LocalDate validity;

  private LocalDate sendDate;

  private LocalDate saleDate;

  private String status;
}
