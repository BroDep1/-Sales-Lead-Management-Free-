package com.expohack.slm.matching.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sale {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private String productAdditionalData;

  private LocalDate validity;

  private LocalDate saleDate;

  private String status;

  public Sale(
      Client client, Product product,
      String productAdditionalData, LocalDate validity,
      LocalDate saleDate, String status
  ) {
    this.client = client;
    this.product = product;
    this.productAdditionalData = productAdditionalData;
    this.validity = validity;
    this.saleDate = saleDate;
    this.status = status;
  }
}