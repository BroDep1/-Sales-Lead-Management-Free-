package com.expohack.slm.recommendation.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationLead {
    private String mobilePhone;
    private String email;
    private String productName;
    private Float frequencyPurchases;
}
