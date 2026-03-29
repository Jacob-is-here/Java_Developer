package com.example.java_developer.controller;

import com.example.java_developer.dto.ProducerResponse;
import com.example.java_developer.service.ProducerService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public List<ProducerResponse> getProducers() {
        return producerService.getAllProducers();
    }
}

