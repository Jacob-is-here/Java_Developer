package com.example.java_developer.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        ProducerResponse producer,
        Map<String, Object> attributes
) {
}

