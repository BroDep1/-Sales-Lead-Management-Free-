package com.expohack.slm.matching.repository;

import com.expohack.slm.matching.model.Sale;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, UUID> {

}
