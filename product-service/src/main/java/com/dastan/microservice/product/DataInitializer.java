package com.dastan.microservice.product;

import com.dastan.microservice.product.model.Product;
import com.dastan.microservice.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository products;

    @Override
    public void run(final String... args) throws Exception {
        log.info("starting data init...");

        final Product p1 = Product.builder().name("IPhone X").sku("IP-XX-111").build();
        final Product p2 = Product.builder().name("Pixel XL").sku("PI-XX-333").build();
        final Product p3 = Product.builder().name("Nokia 7").sku("NO-778-163").build();

        products.deleteAll()
                .thenMany(Flux.just(p1, p2, p3).flatMap(c -> products.save(c)))
                .subscribe(null, throwable -> log.error("Could not save product", throwable), () -> log.info("Finished data init..."));

        products.count().subscribe(c -> log.info("Inserted " + c + " products!"));
    }
}
