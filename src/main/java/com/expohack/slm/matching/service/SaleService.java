package com.expohack.slm.matching.service;

import com.expohack.slm.matching.model.Sale;
import com.expohack.slm.matching.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {

  private final SaleRepository saleRepository;
  private final ProductService productService;

  public void save(Sale newSale) {
    saleRepository.save(newSale);
    productService.increaseTheNumberOfPurchases(newSale);
    log.info("Saved sale with id {}", newSale.getId());
  }
}
