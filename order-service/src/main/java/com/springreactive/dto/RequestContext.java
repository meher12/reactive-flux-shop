package com.springreactive.dto;

import lombok.Data;
import lombok.ToString;


/**
 * This class seems to be used to encapsulate and hold various request-related data objects,
 * providing a way to bundle them together in a single context.
 * The specific purpose or usage of this class would depend on the broader context of owr code and application.
 *
 */
@Data
@ToString
public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }
}
