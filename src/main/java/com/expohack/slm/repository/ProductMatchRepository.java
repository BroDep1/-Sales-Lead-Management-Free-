package com.expohack.slm.repository;

import com.expohack.slm.model.Company;
import com.expohack.slm.model.ProductMatch;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMatchRepository extends JpaRepository<ProductMatch, UUID> {

  Optional<ProductMatch> findByCompanyAndCompanyProductName(Company company, String productName);
}
