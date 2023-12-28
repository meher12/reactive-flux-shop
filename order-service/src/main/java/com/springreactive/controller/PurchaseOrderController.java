package com.springreactive.controller;

import com.springreactive.dto.PurchaseOrderRequestDto;
import com.springreactive.dto.PurchaseOrderResponseDto;
import com.springreactive.service.OrderFulfillmentService;
import com.springreactive.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService queryService;

    /**
     * Controller method to handle the creation of a purchase order.
     *
     * @param requestDtoMono A Mono containing the PurchaseOrderRequestDto.
     * @return A Mono of ResponseEntity containing the PurchaseOrderResponseDto.
     */
    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return this.orderFulfillmentService.processOrder(requestDtoMono)
                .map(ResponseEntity::ok) // Map the result to a ResponseEntity with OK status.
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build()) // Handle WebClientResponseException with bad request status.
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()); // Handle WebClientRequestException with service unavailable status.
    }

    /**
     * Controller method to retrieve purchase orders for a specific user.
     *
     * @param userId The ID of the user.
     * @return A Flux of PurchaseOrderResponseDto representing the user's purchase orders.
     */
    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable int userId) {
        return this.queryService.getProductsByUserId(userId);
    }
}