package com.expohack.slm.matching.service;

import com.expohack.slm.matching.model.Product;
import com.expohack.slm.matching.model.ProductMatch;
import com.expohack.slm.matching.model.Sale;
import com.expohack.slm.matching.repository.ProductMatchRepository;
import com.expohack.slm.commons.model.Company;
import java.util.Optional;

import com.expohack.slm.matching.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
  private static final int INCREMENT_VALUE = 1;
  private final ProductMatchRepository productMatchRepository;
  private final ProductRepository productRepository;

  public Optional<Product> matchProduct(Company company, String productName) {
    log.info(
        "Retrieving product match for company with id {} with company product name {}",
        company.getId(),
        productName
    );

    Optional<ProductMatch> productMatchOptional =
        productMatchRepository.findByCompanyAndCompanyProductName(
            company,
            productName
        );

    return productMatchOptional.map(ProductMatch::getProduct);
  }

  public void increaseTheNumberOfPurchases(Sale newSale) {
    productRepository.updateNumberOfSales(INCREMENT_VALUE, newSale.getProduct().getId());
  }
}
