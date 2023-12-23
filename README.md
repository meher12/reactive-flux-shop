# Reactive Microservices With Spring WebFlux

## Introduction
This table provides a detailed comparison between two pivotal components within the Spring framework: **Spring MVC** and **Spring Web Reactive**.

| Feature                  | Spring MVC                  | Spring Web Reactive          |
|--------------------------|-----------------------------|------------------------------|
| Programming Model        | Synchronous                | Reactive                     |
| Annotations              | @Controller, @RequestMapping | @Controller, @RequestMapping |
| Underlying Technologies  | Servlet API                 | Reactive HTTP                |
| Servlet Container        | Yes (Servlet 3.1)           | Servlet 3.1, Netty, Undertow |

## Spring WebFlux
Spring WebFlux is a key component of Spring Web Reactive. It provides a reactive programming model for building scalable and non-blocking web applications.
## Functional Endpoints
Explore the power of functional endpoints, a feature available in Spring Web Reactive, offering a concise and expressive way to define your web application's behavior.
## WebClient
Learn how to utilize WebClient, an essential tool in Spring Web Reactive for making reactive, non-blocking HTTP requests.

**In the "webflux-demo" project, you'll find a comprehensive integration of Spring WebFlux, Functional Endpoints, WebClient and takes center stage as a vital component of Spring Web Reactive**.
## Architecture  Reactive Microservices:
<div>
     <img src="reactiveSystem.jpg" width="auto">
</div>

- This diagram shows the three reactive microservices: a product service, an order service, and a user service. These microservices are designed to be independent and self-contained, and they communicate with each other using asynchronous messaging. This means that each microservice can process requests without waiting for a response from another microservice.
- The diagram also shows two different types of databases being used:
   * NoSQL: The product service is using a NoSQL database, such as MongoDB. NoSQL databases are not relational, and they are better suited for storing unstructured data.
   * RDBMS: The order service is using a relational database, such as PostgreSQL. RDBMS databases are relational, and they are better suited for storing structured data.

### Spring Data Reactive MongoDB/CRUD Application Development:
1. Spring Data Reactive MongoDB:

    | Blocking Driver | Reactive Driver |
    |-----------------|-----------------|
    | Optional(T)     | Mono(T)         |
    | List(T)         | Flux(T)         |
    | long(count)     | Mono(Long)      |
    | boolean         | Mono(Boolean)   |

2. Create Product-Service project
 - `EntityDtoUtil` class contains two static methods for mapping between an entity class (Product) and a data transfer object (DTO) class (ProductDto). The code uses the BeanUtils.copyProperties method to copy property values from one object to another.
 - | Benefit                   | Description                                                                                           |
   |---------------------------|-------------------------------------------------------------------------------------------------------|
   | Code Reusability          | Easily reuse mapping logic across the application.                                                    |
   | Consistent Mapping        | Ensure consistent application of mapping rules.                                                       |
   | Encapsulation             | Hide mapping details, making code more maintainable.                                                  |
   | Simplified Client Code    | Clients use the utility methods without dealing with property copying specifics.                      |
   | Ease of Updates           | Update mapping logic in one place for changes in entity or DTO structures.                             |
   | Adaptability to Changes    | Reflect changes in one location for modifications in the entire application.                           |
   | Reduced Boilerplate Code  | Minimize repetitive code using the `BeanUtils.copyProperties` method.                                 |
   | Testing Isolation         | Facilitate independent testing of mapping logic in the utility class.                                  |
3. Derived Query Methods in Spring









