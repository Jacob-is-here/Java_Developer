package com.example.java_developer.repository;

import com.example.java_developer.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = "producer")
    List<Product> findAll();
}

