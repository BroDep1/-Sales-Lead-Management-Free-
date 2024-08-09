package com.expohack.slm.commons.model;

import java.time.LocalDate;
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

  private UUID id;

  private UUID companyId;

  private String clientName;

  private String clientMiddleName;

  private String clientSurname;

  private LocalDate clientBirthDate;

  private String clientMobilePhone;

  private String clientEmail;

  private String clientPassportSeries;

  private String clientPassportNumber;

  private String clientDriverLicenseSeries;

  private String clientDriverLicenseNumber;

  private String productName;

  private String productAdditionalData;

  private LocalDate validityDate;

  private LocalDate sendDate;

  private LocalDate saleDate;

  private String status;
}
