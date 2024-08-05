package com.expohack.slm.matching.service;

import com.expohack.slm.matching.model.Company;
import com.expohack.slm.matching.model.Product;
import com.expohack.slm.matching.model.ProductMatch;
import com.expohack.slm.matching.repository.ProductMatchRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

  private final ProductMatchRepository productMatchRepository;

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
}
