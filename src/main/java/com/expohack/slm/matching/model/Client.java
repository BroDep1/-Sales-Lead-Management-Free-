package com.expohack.slm.matching.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

  public Client(
      String name, String middleName, String surname, LocalDate birthdate,
      String mobilePhone, String email, String passportSeries, String passportNumber,
      String driverLicenseSeries, String driverLicenseNumber
  ) {
    this.name = name;
    this.middleName = middleName;
    this.surname = surname;
    this.birthdate = birthdate;
    this.mobilePhone = mobilePhone;
    this.email = email;
    this.passportSeries = passportSeries;
    this.passportNumber = passportNumber;
    this.driverLicenseSeries = driverLicenseSeries;
    this.driverLicenseNumber = driverLicenseNumber;
  }
}
