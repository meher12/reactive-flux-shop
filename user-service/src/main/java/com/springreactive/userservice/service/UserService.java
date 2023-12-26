package com.springreactive.userservice.service;

import com.springreactive.userservice.dto.UserDto;
import com.springreactive.userservice.repository.UserRepository;
import com.springreactive.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<UserDto> getAllUser() {
        return this.userRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserById(final int userId) {
        return userRepository.findById(userId)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(int id, Mono<UserDto> userDtoMono){
        return this.userRepository.findById(id)
                .flatMap(u -> userDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(ue -> ue.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(int id){
        return userRepository.deleteById(id);
    }
}
