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
public class Sale {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID companyId;

  private String product;

  private String productAdditionalData;

  private LocalDate validity;

  private LocalDate saleDate;

  private String status;
}