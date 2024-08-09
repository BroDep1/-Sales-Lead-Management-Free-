package com.expohack.slm.matching.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.expohack.slm.commons.model.Company;
import com.expohack.slm.commons.model.SalesDTO;
import com.expohack.slm.commons.repository.CompanyRepository;
import com.expohack.slm.matching.model.Client;
import com.expohack.slm.matching.model.Product;
import com.expohack.slm.matching.model.Sale;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MatchingServiceTest {

  public static final UUID COMPANY_ID = UUID.randomUUID();
  @Mock
  private ErrorService errorService;

  @Mock
  private ClientService clientService;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private ProductService productService;

  @Mock
  private SaleService saleService;

  @InjectMocks
  private MatchingService matchingService;


  @Test
  public void testCompanyIdOrProductNameIsNull() {
    SalesDTO saleWithNullCompanyId = new SalesDTO();
    saleWithNullCompanyId.setProductName("Product A");

    SalesDTO saleWithNullProductName = new SalesDTO();
    saleWithNullProductName.setCompanyId(UUID.randomUUID());

    matchingService.matchSales(List.of(saleWithNullCompanyId, saleWithNullProductName));

    verify(companyRepository, never()).findById(any(UUID.class));
    verify(productService, never()).matchProduct(any(Company.class), anyString());
  }

  @Test
  public void testCompanyNotFound() {
    SalesDTO sale = new SalesDTO();
    sale.setCompanyId(COMPANY_ID);
    sale.setProductName("Product A");

    when(companyRepository.findById(COMPANY_ID)).thenReturn(Optional.empty());

    matchingService.matchSales(List.of(sale));

    verify(companyRepository).findById(COMPANY_ID);
    verify(errorService, never()).createError(anyString(), anyString(), any(), any());
  }

  @Test
  public void testProductNotFound() {
    String mobilePhone = "+123456789";
    SalesDTO sale = new SalesDTO();
    sale.setCompanyId(COMPANY_ID);
    sale.setClientMobilePhone(mobilePhone);
    sale.setProductName("Product A");

    Client client = new Client();
    client.setMobilePhone(mobilePhone);

    Company company = new Company();
    when(clientService.getByMobilePhone(mobilePhone)).thenReturn(Optional.of(client));
    when(companyRepository.findById(COMPANY_ID)).thenReturn(Optional.of(company));
    when(productService.matchProduct(company, "Product A")).thenReturn(Optional.empty());

    matchingService.matchSales(List.of(sale));

    verify(productService).matchProduct(company, "Product A");
    verify(errorService).createError(eq("Product listed in the sales data doesn't exist"),
        eq("Product A"), eq(sale.getSendDate()), eq(company));
  }

  @Test
  public void testClientIdentificationDataMissing() {
    SalesDTO sale = new SalesDTO();
    sale.setCompanyId(COMPANY_ID);
    sale.setProductName("Product A");

    Company company = new Company();
    when(companyRepository.findById(COMPANY_ID)).thenReturn(Optional.of(company));

    matchingService.matchSales(List.of(sale));

    verify(errorService).createError(eq("Sales data has no information to identify client"),
        eq("Product A"), eq(sale.getSendDate()), eq(company));
    verify(saleService, never()).save(any(Sale.class));
  }

  @Test
  public void testSuccessfulSaleCreation() {
    SalesDTO sale = new SalesDTO();
    sale.setCompanyId(COMPANY_ID);
    sale.setProductName("Product A");
    sale.setClientMobilePhone("1234567890");

    Company company = new Company();
    Product product = new Product();
    Client client = new Client();

    when(companyRepository.findById(COMPANY_ID)).thenReturn(Optional.of(company));
    when(productService.matchProduct(company, "Product A")).thenReturn(Optional.of(product));
    when(clientService.getByMobilePhone("1234567890")).thenReturn(Optional.of(client));

    matchingService.matchSales(List.of(sale));

    verify(saleService).save(any(Sale.class));
    verify(errorService, never()).createError(anyString(), anyString(), any(), any());
  }
}
