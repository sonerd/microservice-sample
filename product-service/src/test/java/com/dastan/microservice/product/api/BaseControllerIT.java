package com.dastan.microservice.product.api;

import com.dastan.microservice.product.model.Product;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public abstract class BaseControllerIT {

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
                                                                                                                                .maxDocuments(100)));

        StepVerifier.create(recreateCollection).expectNextCount(1).verifyComplete();
    }
}
