package com.expohack.slm.repository;

import com.expohack.slm.model.Sale;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, UUID> {

}
