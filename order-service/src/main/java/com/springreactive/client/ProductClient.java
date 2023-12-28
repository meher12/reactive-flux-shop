package com.springreactive.client;

import com.springreactive.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {


    //If we prefer manual control over the instantiation and want to ensure immutability
    private final WebClient webClient;

    /*@Autowired
    private WebClient webClient;*/

    /**
     * this constructor is responsible for creating an instance of ProductClient and initializing its webClient
     * field with a WebClient instance configured with the base URL obtained from the application's configuration.
     * @param url
     */
    public ProductClient(@Value("${product.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ProductDto> getProductById(final String productId){
        return this.webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Flux<ProductDto> getAllProducts(){
        return this.webClient
                .get()
                .uri("all")
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }

}