package com.example.java_developer.service;

import com.example.java_developer.dto.ProductRequest;
import com.example.java_developer.dto.ProductResponse;
import com.example.java_developer.mapper.ProductMapper;
import com.example.java_developer.model.Producer;
import com.example.java_developer.model.Product;
import com.example.java_developer.repository.ProducerRepository;
import com.example.java_developer.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;

    public ProductService(ProductRepository productRepository, ProducerRepository producerRepository) {
        this.productRepository = productRepository;
        this.producerRepository = producerRepository;
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Producer producer = findProducerById(request.producerId());

        Product product = new Product();
        applyRequestOnProduct(product, request, producer);

        Product created = productRepository.save(product);
        return ProductMapper.toResponse(created);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product with id %d was not found".formatted(id)));
        Producer producer = findProducerById(request.producerId());

        applyRequestOnProduct(product, request, producer);
        Product updated = productRepository.save(product);

        return ProductMapper.toResponse(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Product with id %d was not found".formatted(id));
        }
        productRepository.deleteById(id);
    }

    private Producer findProducerById(Long producerId) {
        return producerRepository.findById(producerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producer with id %d was not found".formatted(producerId)));
    }

    private void applyRequestOnProduct(Product product, ProductRequest request, Producer producer) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setProducer(producer);
        product.setAttributes(request.attributes() == null ? new LinkedHashMap<>() : request.attributes());
    }
}

