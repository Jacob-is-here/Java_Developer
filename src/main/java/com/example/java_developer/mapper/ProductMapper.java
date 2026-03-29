package com.example.java_developer.mapper;

import com.example.java_developer.dto.ProducerResponse;
import com.example.java_developer.dto.ProductResponse;
import com.example.java_developer.model.Product;
import java.util.LinkedHashMap;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                new ProducerResponse(
                        product.getProducer().getId(),
                        product.getProducer().getName(),
                        product.getProducer().getCountry()
                ),
                new LinkedHashMap<>(product.getAttributes())
        );
    }
}

