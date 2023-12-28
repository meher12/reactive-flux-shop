package com.springreactive.service;

import com.springreactive.dto.PurchaseOrderResponseDto;
import com.springreactive.repository.PurchaseOrderRepository;
import com.springreactive.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    /**
     * Retrieves purchase orders for a specific user ID in a reactive manner.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A Flux of PurchaseOrderResponseDto representing the orders for the specified user.
     */
    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
        // Convert synchronous stream to a reactive Flux
        return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream()) // blocking operation
                .map(EntityDtoUtil::getPurchaseOrderResponseDto) // Map PurchaseOrder entities to PurchaseOrderResponseDto
                .subscribeOn(Schedulers.boundedElastic()); // Subscribe on a bounded elastic scheduler for parallel processing.
    }

}
