package com.expohack.slm.recommendation.model.entity;

import com.expohack.slm.matching.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class RelatedProduct {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sold_product_id")
    private Product soldProduct;

    @ManyToOne
    @JoinColumn(name = "recommended_product_id")
    private Product recommendationProduct;

    @Column(name = "number_sales_this_combination")
    private Integer numberSalesThisCombination;
}
