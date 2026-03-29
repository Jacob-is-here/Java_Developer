package com.example.java_developer.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldListSeededProducers() throws Exception {
        mockMvc.perform(get("/api/producers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void shouldCreateUpdateAndDeleteProduct() throws Exception {
        ProductPayload payload = new ProductPayload(
                "Galaxy TV",
                "4K Smart TV",
                new BigDecimal("1299.90"),
                1L,
                Map.of(
                        "width", 145,
                        "height", 86,
                        "energy_rating", "A+",
                        "certifications", "CE"
                )
        );

        String createResponse = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Galaxy TV"))
                .andExpect(jsonPath("$.producer.id").value(1))
                .andExpect(jsonPath("$.attributes.width").value(145))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long productId = objectMapper.readTree(createResponse).get("id").asLong();

        ProductPayload updatePayload = new ProductPayload(
                "Galaxy TV Pro",
                "Updated model",
                new BigDecimal("1499.90"),
                1L,
                Map.of("warranty_period", "36 months")
        );

        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePayload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galaxy TV Pro"))
                .andExpect(jsonPath("$.attributes.warranty_period").value("36 months"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(productId));

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());
    }

    private record ProductPayload(
            String name,
            String description,
            BigDecimal price,
            Long producerId,
            Map<String, Object> attributes
    ) {
    }
}

