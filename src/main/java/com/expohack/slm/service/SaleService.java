package com.expohack.slm.service;

import com.expohack.slm.model.Sale;
import com.expohack.slm.repository.SaleRepository;
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
  }
}
