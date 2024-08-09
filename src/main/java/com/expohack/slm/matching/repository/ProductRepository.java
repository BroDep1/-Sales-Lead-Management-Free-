package com.expohack.slm.matching.repository;

import com.expohack.slm.matching.model.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Modifying
    @Query("UPDATE Product p SET p.numberOfSales = p.numberOfSales + :increment WHERE p.id = :productId")
    void updateNumberOfSales(@Param("increment") int increment, @Param("productId") UUID productId);
}
