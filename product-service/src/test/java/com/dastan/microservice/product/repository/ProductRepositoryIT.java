package com.dastan.microservice.product.repository;

import com.dastan.microservice.product.model.Product;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static org.junit.Assert.assertThat;


public class ProductRepositoryIT extends BaseRepositoryIT {

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        final Product product1 = Product.builder().name("Nokia 6").sku("NN-33-fff").build();
        final Product product2 = Product.builder().name("IPhone 7").sku("II-44-fff").build();

        final Flux<Product> insertAll = operations.insertAll(Flux.just(product1, product2).collectList());

        StepVerifier.create(insertAll).expectNextCount(2).verifyComplete();
    }

    @Test
    public void shouldFindByName() {
        StepVerifier.create(productRepository.findByName("Nokia 6")).expectNextCount(1).verifyComplete();
    }

    @Test
    public void shouldFindBySku() {
        StepVerifier.create(productRepository.findBySku("II-44-fff")).expectNextCount(1).verifyComplete();
    }

    @Test
    public void shouldFindAll() {
        StepVerifier.create(productRepository.findAll()).expectNextCount(2).verifyComplete();
    }

    @Test
    public void shouldFindAllSortedByName() {
        final Flux<Product> sortedByName = productRepository.findAll(Sort.by("name"));

        assertThat(sortedByName.blockFirst().getName(), Is.is("IPhone 7"));
    }

}