package com.expohack.slm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  private String inn;

  private String notificationAddress;
}
