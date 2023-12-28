package com.springreactive.service;

import com.springreactive.client.ProductClient;
import com.springreactive.client.UserClient;
import com.springreactive.dto.PurchaseOrderRequestDto;
import com.springreactive.dto.PurchaseOrderResponseDto;
import com.springreactive.dto.RequestContext;
import com.springreactive.repository.PurchaseOrderRepository;
import com.springreactive.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    /**
     * Processes a purchase order asynchronously.
     *
     * @param requestDtoMono A Mono containing the PurchaseOrderRequestDto.
     * @return A Mono of PurchaseOrderResponseDto.
     */
    /**
     *  * **Key steps:**
     *  * 1. **Wraps the request in a RequestContext:** Creates a new RequestContext object to hold the request and associated data throughout the process.
     *  * 2. **Fetches product details:** Calls the `productClient.getProductById` method asynchronously to retrieve product information using the product ID from the request.
     *  * 3. **Sets transaction details:** Prepares the transaction request using data from the request and fetched product details.
     *  * 4. **Authorizes the transaction:** Calls the `userClient.authorizeTransaction` method asynchronously to validate and approve the transaction.
     *  * 5. **Extracts and saves the purchase order:** Extracts the PurchaseOrder object from the context and saves it to the database using the `orderRepository`.
     *  * 6. **Converts to response DTO:** Maps the saved PurchaseOrder to a PurchaseOrderResponseDto for returning to the caller.
     *  * 7. **Subscribes on a bounded elastic scheduler:** Ensures efficient execution of the asynchronous operations, handling backpressure appropriately.
     */
    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono
                .map(RequestContext::new) // Wrap the requestDtoMono in a RequestContext.
                .flatMap(this::productRequestResponse) // Fetch product details.
                .doOnNext(EntityDtoUtil::setTransactionRequestDto) // Set transaction details in the context.
                .flatMap(this::userRequestResponse) // Authorize the transaction.
                .map(EntityDtoUtil::getPurchaseOrder) // Extract and save the purchase order.
                .map(this.orderRepository::save) // Save the purchase order (blocking operation).
                .map(EntityDtoUtil::getPurchaseOrderResponseDto) // Convert to PurchaseOrderResponseDto.
                .subscribeOn(Schedulers.boundedElastic()); // Subscribe on a bounded elastic scheduler.
    }

    /**
     * Fetches product details and updates the RequestContext.
     *
     * @param rc The RequestContext containing the PurchaseOrderRequestDto.
     * @return A Mono of RequestContext.
     */
    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto) // Update the RequestContext with product details.
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1))) // Retry logic with fixed delay.
                .thenReturn(rc); // Return the updated RequestContext.
    }

    /**
     * Authorizes the transaction and updates the RequestContext.
     *
     * @param rc The RequestContext containing the transaction details.
     * @return A Mono of RequestContext.
     */
    private Mono<RequestContext> userRequestResponse(RequestContext rc) {
        return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto) // Update the RequestContext with transaction response.
                .thenReturn(rc); // Return the updated RequestContext.
    }
}
