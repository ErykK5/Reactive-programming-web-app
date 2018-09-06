package com.company.usermongodbwebapp.services;

import com.company.usermongodbwebapp.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getUsers();

    Mono<User> findById(String id );

    Mono<User> createUser(User user );

    Mono<Void> deleteUser( String id );

    Mono<Void> deleteUser( User user );
}
