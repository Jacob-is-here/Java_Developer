package com.example.java_developer.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public record ProductRequest(
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @NotNull Long producerId,
        Map<String, Object> attributes
) {
}

