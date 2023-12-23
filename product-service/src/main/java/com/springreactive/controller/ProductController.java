package com.springreactive.controller;

import com.springreactive.dto.ProductDto;
import com.springreactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService service;

    // Endpoint to retrieve all products
    @GetMapping("all")
    public Flux<ProductDto> all() {
        return this.service.getAllProduct();
    }

    // Endpoint to retrieve a product by ID
    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id) {
        return this.service.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    // Endpoint to retrieve a product by price range
    @GetMapping("price-range")
    public Flux<ProductDto> getByPriceRange(@RequestParam("min") int min,@RequestParam("max") int max){
        return this.service.getProductByPriceRange(min, max);
    }

    // Endpoint to insert a new product
    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.service.insertProduct(productDtoMono);
    }

    // Endpoint to update an existing product by ID
    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono) {
        return this.service.updateProduct(id, productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Endpoint to delete a product by ID
    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return this.service.deleteProduct(id);
    }

}
