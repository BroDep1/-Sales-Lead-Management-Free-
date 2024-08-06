package com.expohack.slm.matching.service;

import com.expohack.slm.matching.model.Client;
import com.expohack.slm.matching.model.Product;
import com.expohack.slm.matching.model.Sale;
import com.expohack.slm.model.Company;
import com.expohack.slm.model.SalesDTO;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchingService {

  private final ErrorService errorService;
  private final ClientService clientService;
  private final CompanyService companyService;
  private final ProductService productService;
  private final SaleService saleService;

  // for each raw sales record,
  // create a regular sales record if a product match is possible
  // if an error occurs, we save it for further feedback to the company
  @Transactional
  public void matchSales(List<SalesDTO> rawSales) {
    for (SalesDTO sale : rawSales) {
      if (sale.getCompanyId() == null || sale.getProductName() == null) {
        log.error(
            "No information about company or product in raw sales data with id {}",
            sale.getId()
        );
        continue;
      }

      log.info(
          "Processing raw sales data from company with id {} and product with name {}",
          sale.getCompanyId(),
          sale.getProductName()
      );

      Optional<Company> companyOptional = companyService.getById(sale.getCompanyId());

      if (companyOptional.isEmpty()) {
        log.error(
            "Company listed in the sales data with id {} doesn't exist",
            sale.getId()
        );
        continue;
      }

      Company company = companyOptional.get();
      Optional<Client> clientOptional;

      // trying to match client by mobile phone, email, passport data or driver license data
      if (sale.getClientMobilePhone() != null) {
        clientOptional = clientService.getByMobilePhone(sale.getClientMobilePhone());
      } else if (sale.getClientEmail() != null) {
        clientOptional = clientService.getByEmail(sale.getClientEmail());
      } else if (
          sale.getClientPassportSeries() != null
              && sale.getClientPassportNumber() != null
      ) {
        clientOptional = clientService.getByPassportSeriesAndPassportNumber(
            sale.getClientPassportSeries(),
            sale.getClientPassportNumber()
        );
      } else if (
          sale.getClientPassportSeries() != null
              && sale.getClientDriverLicenseNumber() != null
      ) {
        clientOptional = clientService.getByDriverLicenseSeriesAndDriverLicenseNumber(
            sale.getClientDriverLicenseSeries(),
            sale.getClientDriverLicenseNumber()
        );
      } else {
        log.error(
            "Sales data with id {} has no information to identify client",
            sale.getId()
        );
        errorService.createError(
            "Sales data has no information to identify client",
            sale.getProductName(),
            sale.getSendDate(),
            company
        );
        continue;
      }

      Optional<Product> productOptional = productService.matchProduct(
          company,
          sale.getProductName()
      );

      if (productOptional.isEmpty()) {
        log.error(
            "Product listed in the sales data with id {} doesn't exist",
            sale.getId()
        );
        errorService.createError(
            "Product listed in the sales data doesn't exist",
            sale.getProductName(),
            sale.getSendDate(),
            company
        );
        continue;
      }

      Client client = clientOptional.orElse(
          new Client(
              sale.getClientName(),
              sale.getClientMiddleName(),
              sale.getClientSurname(),
              sale.getClientBirthdate(),
              sale.getClientMobilePhone(),
              sale.getClientEmail(),
              sale.getClientPassportSeries(),
              sale.getClientPassportNumber(),
              sale.getClientDriverLicenseSeries(),
              sale.getClientDriverLicenseNumber()
          )
      );

      Sale newSale = new Sale(
          client,
          productOptional.get(),
          sale.getProductAdditionalData(),
          sale.getValidity(),
          sale.getSaleDate(),
          sale.getStatus()
      );

      saleService.save(newSale);

      // process the errors by sending notifications to the companies
      // then just delete them from the table
    }
  }
}
