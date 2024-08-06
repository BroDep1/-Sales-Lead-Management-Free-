package com.expohack.slm.matching.model;

import com.expohack.slm.model.Company;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

// model to match product names of the sender company and application
@Entity
@Getter
@Setter
public class ProductMatch {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  private String companyProductName;
}
