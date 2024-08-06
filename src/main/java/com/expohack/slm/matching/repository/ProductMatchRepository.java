package com.expohack.slm.matching.repository;

import com.expohack.slm.matching.model.ProductMatch;
import com.expohack.slm.model.Company;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMatchRepository extends JpaRepository<ProductMatch, UUID> {

  Optional<ProductMatch> findByCompanyAndCompanyProductName(Company company, String productName);
}
