package com.springreactive.userservice.util;

import com.springreactive.userservice.dto.TransactionRequestDto;
import com.springreactive.userservice.dto.TransactionResponseDto;
import com.springreactive.userservice.dto.TransactionStatus;
import com.springreactive.userservice.dto.UserDto;
import com.springreactive.userservice.entity.User;
import com.springreactive.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;



import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static User toEntity(UserDto dto){
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static UserTransaction toEntityT(TransactionRequestDto requestDto){
        UserTransaction ut = new UserTransaction();
        ut.setUserId(requestDto.getUserId());
        ut.setAmount(requestDto.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

    public static TransactionResponseDto toDtoT(TransactionRequestDto requestDto, TransactionStatus status){
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setAmount(requestDto.getAmount());
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setStatus(status);
        return responseDto;
    }


}
