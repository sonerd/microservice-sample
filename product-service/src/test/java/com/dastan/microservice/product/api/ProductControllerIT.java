package com.dastan.microservice.product.api;

import com.dastan.microservice.product.model.Product;
import com.dastan.microservice.product.repository.ProductRepository;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductControllerIT extends BaseControllerIT {

    @Autowired
    private WebTestClient client;

    @Autowired
    private ProductRepository products;

    @Test
    public void shouldReturnAllProducts() {
        final Product p = createProduct("TV", "rrrrr-fff");
        products.save(p).block();

        client.get().uri("/products").exchange().expectBodyList(Product.class).hasSize(1);
    }

    @Test
    public void shouldReturnProductById() {
        final Product p = createProduct("Playstation 4", "ppp-fff");
        final Product savedProduct = products.save(p).block();

        client.get()
              .uri("/products/{id}", Collections.singletonMap("id", savedProduct.getId()))
              .exchange()
              .expectStatus()
              .isOk()
              .expectBody(Product.class)
              .isEqualTo(savedProduct);
    }

    @Test
    public void shouldReturn404ForNotExistingProduct() {
        client.get().uri("/products/1234").exchange().expectStatus().isNotFound();
    }

    @Test
    public void shouldDeleteExistingProduct() {
        final Product p = createProduct("to delete", "11ss-555-33");
        final Product toBeDeleted = products.save(p).block();

        client.delete().uri("/products/{id}", Collections.singletonMap("id", toBeDeleted.getId())).exchange().expectStatus().isOk();
    }

    @Test
    public void shouldCreateProduct() {
        final String name = "Car";
        final String sku = "hh-kk-w234";
        final Product p = createProduct(name, sku);

        client.post().uri("/products")
              .body(Mono.just(p), Product.class)
              .exchange()
              .expectStatus().isOk()
              .expectBody()
              .jsonPath("$.id").isNotEmpty()
              .jsonPath("$.name").isEqualTo(name)
              .jsonPath("$.sku").isEqualTo(sku);

    }

    private Product createProduct(final String name, final String sku) {
        return Product.builder().name(name).sku(sku).build();
    }
}