package com.dastan.microservice.product.repository;

import com.dastan.microservice.product.model.Product;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveSortingRepository<Product, String> {
    Flux<Product> findByName(String name);

    Flux<Product> findBySku(String sku);
}
