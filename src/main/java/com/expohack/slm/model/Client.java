package com.expohack.slm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  private String middleName;

  private String surname;

  private LocalDate birthdate;

  private String mobilePhone;

  private String email;

  private String passportSeries;

  private String passportNumber;

  private String driverLicenseSeries;

  private String driverLicenseNumber;
}
