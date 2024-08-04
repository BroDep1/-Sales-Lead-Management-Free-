package com.expohack.slm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private String productAdditionalData;

  private LocalDate validity;

  private LocalDate saleDate;

  private String status;
}