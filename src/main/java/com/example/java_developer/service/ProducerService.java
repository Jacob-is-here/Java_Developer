package com.example.java_developer.service;

import com.example.java_developer.dto.ProducerResponse;
import com.example.java_developer.repository.ProducerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public List<ProducerResponse> getAllProducers() {
        return producerRepository.findAll().stream()
                .map(p -> new ProducerResponse(p.getId(), p.getName(), p.getCountry()))
                .toList();
    }
}

