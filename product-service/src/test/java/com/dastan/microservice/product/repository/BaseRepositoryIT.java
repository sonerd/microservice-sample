package com.dastan.microservice.product.repository;

import com.dastan.microservice.product.model.Product;
import com.mongodb.reactivestreams.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public abstract class BaseRepositoryIT {

    @Autowired
    protected ReactiveMongoOperations operations;

    @Before
    public void setUpCollection() {
        final Mono<MongoCollection<Document>> recreateCollection = operations.collectionExists(Product.class)
                                                                             .flatMap(exists -> exists ? operations.dropCollection(Product.class) : Mono.just(
                                                                                     exists))
                                                                             .then(operations.createCollection(Product.class,
                                                                                                               CollectionOptions.empty()
                                                                                                                                .size(1024 * 1024)
                                                                                                                                .maxDocuments(100)
                                                                                                                                .capped()));

        StepVerifier.create(recreateCollection).expectNextCount(1).verifyComplete();
    }
}
