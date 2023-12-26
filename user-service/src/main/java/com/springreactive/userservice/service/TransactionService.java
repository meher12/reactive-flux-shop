package com.springreactive.userservice.service;

import com.springreactive.userservice.dto.TransactionRequestDto;
import com.springreactive.userservice.dto.TransactionResponseDto;
import com.springreactive.userservice.dto.TransactionStatus;
import com.springreactive.userservice.entity.UserTransaction;
import com.springreactive.userservice.repository.UserRepository;
import com.springreactive.userservice.repository.UserTransactionRepository;
import com.springreactive.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    // Method to handle transaction creation
    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {

        // Attempt to update user balance in the database
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())

                // Filter to proceed only if balance update was successful
                .filter(Boolean::booleanValue)

                // Map to Transaction entity if balance update succeeded
                .map(b -> EntityDtoUtil.toEntityT(requestDto))

                // Save the transaction entity to the database
                .flatMap(this.transactionRepository::save)

                // Map saved transaction to response DTO with APPROVED status
                .map(ut -> EntityDtoUtil.toDtoT(requestDto, TransactionStatus.APPROVED))

                // Handle failure: provide a DECLINED response if transaction was not saved
                .defaultIfEmpty(EntityDtoUtil.toDtoT(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId){
        return this.transactionRepository.findByUserId(userId);
    }


}
