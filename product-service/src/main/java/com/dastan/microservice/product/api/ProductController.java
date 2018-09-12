package com.dastan.microservice.product.api;

import com.dastan.microservice.product.model.Product;
import com.dastan.microservice.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductRepository products;

    @Autowired
    public ProductController(final ProductRepository products) {
        this.products = products;
    }

    @GetMapping("")
    public Flux<Product> getAll() {
        return products.findAll();
    }

    @PostMapping("")
    public Mono<Product> create(@RequestBody final Product product) {
        return products.save(product);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getById(@PathVariable("id") final String id) {
        return products.findById(id).map(ResponseEntity::ok)
                       .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity> deleteById(@PathVariable("id") final String id) {
        return products.findById(id)
                       .flatMap(p -> products.delete(p).then(Mono.just(new ResponseEntity(HttpStatus.OK))))
                       .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> update(@PathVariable("id") final String id, @RequestBody final Product product) {

        return products.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setSku(product.getSku());
                    return products.save(existingProduct);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
