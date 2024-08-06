package com.expohack.slm.matching.service;

import com.expohack.slm.matching.model.Sale;
import com.expohack.slm.matching.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {

  private final SaleRepository saleRepository;

  public void save(Sale newSale) {
    saleRepository.save(newSale);
    log.info("Saved sale with id {}", newSale.getId());
  }
}
