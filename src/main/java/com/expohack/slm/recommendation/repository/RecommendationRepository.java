package com.expohack.slm.recommendation.repository;

import com.expohack.slm.recommendation.model.entity.RelatedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecommendationRepository extends JpaRepository<RelatedProduct, UUID> {

    @Query("SELECT rp FROM RelatedProduct rp WHERE rp.soldProduct.id = :soldProductId")
    List<RelatedProduct> findAllBySoldProduct(@Param("soldProductId") UUID id);
}


