package com.expohack.slm.matching.repository;

import com.expohack.slm.matching.model.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
