package com.springreactive.service;

import com.springreactive.dto.ProductDto;
import com.springreactive.repository.ProductRepository;
import com.springreactive.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    // Retrieves all products from the repository and converts them to DTOs.
    public Flux<ProductDto> getAllProduct() {
        return productRepository.findAll()  // Fetch all products
                .map(EntityDtoUtil::toDto); // Convert each product to DTO
    }

    // Retrieves a product by its ID, converting it to a DTO.
    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id)  // Find product by ID
                .map(EntityDtoUtil::toDto);   // Convert to DTO if found
    }

    // Retrieves products within a specified price range. The method queries the repository
    // for products with prices falling within the closed range [min, max], maps the results
    // to ProductDto using EntityDtoUtil, and returns a Flux of ProductDto.
    public Flux<ProductDto> getProductByPriceRange(int min, int max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }

    // Inserts a new product, handling DTO conversion and repository interaction.
    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono                // Start with the product DTO
                .map(EntityDtoUtil::toEntity)  // Convert to entity
                .flatMap(productRepository::insert)  // Insert into repository
                .map(EntityDtoUtil::toDto)   // Convert back to DTO for response
                .doOnNext(this.sink::tryEmitNext); // Using Sink
    }

    // Updates an existing product, ensuring ID consistency and conversion.
    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)  // Find existing product by ID
                .flatMap(p -> productDtoMono   // Ensure ID is set correctly
                        .map(EntityDtoUtil::toEntity)  // Convert to entity
                        .doOnNext(e -> e.setId(id)))  // Set entity ID to match request
                .flatMap(productRepository::save)  // Save updated entity
                .map(EntityDtoUtil::toDto);    // Convert back to DTO for response
    }

    // Deletes a product by its ID.
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);  // Delete product from repository
    }


}
