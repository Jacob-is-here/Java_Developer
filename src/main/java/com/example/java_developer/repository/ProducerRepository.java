package com.example.java_developer.repository;

import com.example.java_developer.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
}

