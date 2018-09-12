# Microservice Sample
This is a sample project to demonstrate how a microservice could be developed based on the Spring stack.
The sample is including one service for products and one for reviews. 
Beside that there is a composite service which uses both services. The configuration is handled by the config-server.
 

## Prerequisites
* Install [MongoDB](http://www.mongodb.org/downloads)
* run `./mongod --dbpath <path-to-data>`

## Techstack
* Spring Cloud and Spring Boot Reactive
* MongoDB
* Docker

## Addressed concepts
* using reactive repositories to handle the data layer
* enabling MongoDB auditing with `@EnableMongoAuditing`
* using auditing fields like `@CreatedDate` in the entity classes
* writing integration tests based on Spring Boot testing APIs 
* using a configuration server
* enabling resilience with Hystrix
* using event streams for eventual consistency

## How to run
