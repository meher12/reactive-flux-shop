package com.springreactive.userservice.controller;

import com.springreactive.userservice.dto.UserDto;
import com.springreactive.userservice.service.UserService;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("all")
    public Flux<UserDto> all(){
        return this.service.getAllUser();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable("id") int id){
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> createUser(@RequestBody Mono<UserDto> userDtoMono){
        return this.service.createUser(userDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable int id, @RequestBody Mono<UserDto> userDtoMono){
        return this.service.updateUser(id, userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable int id){
        return this.service.deleteUser(id);
    }
}
