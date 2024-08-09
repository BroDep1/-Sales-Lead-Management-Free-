package com.expohack.slm.recommendation.service;

import com.expohack.slm.matching.model.Client;
import com.expohack.slm.matching.model.Product;
import com.expohack.slm.matching.model.Sale;
import com.expohack.slm.rabbitMq.ProducerService;
import com.expohack.slm.recommendation.model.dto.RecommendationLead;
import com.expohack.slm.recommendation.model.entity.RelatedProduct;
import com.expohack.slm.recommendation.repository.RecommendationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RecommendationService {
    private RecommendationRepository recommendationRepository;

    private final ProducerService producerService;

    public void getRecommendationProducts(Sale sale){
        List<RecommendationLead> recommendationAllLead = new ArrayList<>();
        Product soldProduct = sale.getProduct();
        int numberOfSalesFirstProduct = 0;
        try {
            numberOfSalesFirstProduct = soldProduct.getNumberOfSales();
        }
        catch (Exception ignored){

        }

        List<RelatedProduct> allCombination = recommendationRepository.findAllBySoldProduct(soldProduct.getId());
        Client client = sale.getClient();

        for (RelatedProduct potentialProduct : allCombination){
            RecommendationLead recommendationLead = getRecommendationLead(potentialProduct, numberOfSalesFirstProduct, client);

            recommendationAllLead.add(recommendationLead);
        }
        producerService.sendRecommendationLead(recommendationAllLead);
    }

    private static RecommendationLead getRecommendationLead(RelatedProduct potentialProduct,
                                                            int numberOfSalesFirstProduct, Client client) {
        Product product = potentialProduct.getRecommendationProduct();
        int numberSalesThisCombination = potentialProduct.getNumberSalesThisCombination();
        int numberOfSalesSecondProduct = product.getNumberOfSales();

        float frequencyPurchases = (float) numberSalesThisCombination
                                   / (numberOfSalesFirstProduct + numberOfSalesSecondProduct);

        RecommendationLead recommendationLead = new RecommendationLead();
        recommendationLead.setEmail(client.getEmail());
        recommendationLead.setMobilePhone(client.getMobilePhone());
        recommendationLead.setProductName(product.getName());
        recommendationLead.setFrequencyPurchases(frequencyPurchases);

        return recommendationLead;
    }
}
