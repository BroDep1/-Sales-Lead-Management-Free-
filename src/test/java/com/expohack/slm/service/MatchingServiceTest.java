package com.expohack.slm.service;

import com.expohack.slm.model.Client;
import com.expohack.slm.model.Company;
import com.expohack.slm.model.Product;
import com.expohack.slm.model.Sale;
import com.expohack.slm.model.SalesDTO;
import com.expohack.slm.repository.ClientRepository;
import com.expohack.slm.repository.CompanyRepository;
import com.expohack.slm.repository.ProductRepository;
import com.expohack.slm.repository.SaleRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MatchingServiceTest {

  @Autowired
  private MatchingService matchingService;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private SaleRepository saleRepository;

  private Company company;
  private Product product;
  private Client client;

  @BeforeEach
  public void setUp() {
    company = new Company();
    company.setId(UUID.fromString("6c5f593a-e210-4edd-8599-c0e804ec6e7d"));
    company.setName("Test Company");
    company.setInn("1234567890");
    company.setNotificationAddress("Test Address");

    product = new Product();
    product.setId(UUID.fromString("6370d44c-60e1-45a3-9f87-5e7322bd3c0f"));
    product.setName("Test Product");

    client = new Client();
    client.setId(UUID.fromString("bf4baafb-075a-40c5-81ff-e00ddf34b582"));
    client.setName("John");
    client.setMiddleName("A.");
    client.setSurname("Doe");
    client.setBirthdate(LocalDate.of(1990, 1, 1));
    client.setMobilePhone("+1234567890");
    client.setEmail("john.doe@example.com");
    client.setPassportSeries("AB");
    client.setPassportNumber("1234567");
    client.setDriverLicenseSeries("CD");
    client.setDriverLicenseNumber("7654321");
  }

  @Test
  public void testMatchSales() {
    SalesDTO salesDTO = new SalesDTO();
    salesDTO.setId(UUID.randomUUID());
    salesDTO.setCompanyId(company.getId());
    salesDTO.setClientName(client.getName());
    salesDTO.setClientMiddleName(client.getMiddleName());
    salesDTO.setClientSurname(client.getSurname());
    salesDTO.setClientBirthdate(client.getBirthdate());
    salesDTO.setClientMobilePhone(client.getMobilePhone());
    salesDTO.setClientEmail(client.getEmail());
    salesDTO.setClientPassportSeries(client.getPassportSeries());
    salesDTO.setClientPassportNumber(client.getPassportNumber());
    salesDTO.setClientDriverLicenseSeries(client.getDriverLicenseSeries());
    salesDTO.setClientDriverLicenseNumber(client.getDriverLicenseNumber());
    salesDTO.setProductName(product.getName());
    salesDTO.setProductAdditionalData("Additional Data");
    salesDTO.setValidity(LocalDate.of(2025, 12, 31));
    salesDTO.setStatus("Pending");

    matchingService.matchSales(List.of(salesDTO));

  }
}
