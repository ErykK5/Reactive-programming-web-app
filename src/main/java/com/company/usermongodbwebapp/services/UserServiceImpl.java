package com.company.usermongodbwebapp.services;

import com.company.usermongodbwebapp.model.User;
import com.company.usermongodbwebapp.repo.reactive.UserReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger( UserServiceImpl.class.getName() );

    private final UserReactiveRepository userReactiveRepository;

    public UserServiceImpl(UserReactiveRepository userReactiveRepository) {
        this.userReactiveRepository = userReactiveRepository;
    }

    @Override
    public Flux<User> getUsers() {
        log.info("LOGGER: added users" );
        return userReactiveRepository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        return userReactiveRepository.findById( id );
    }

    @Override
    public Mono<User> createUser(User user) {
       return userReactiveRepository.save(user);
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        userReactiveRepository.deleteById( id ).block();
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteUser(User user) {
        userReactiveRepository.delete(user).block();
        return Mono.empty();
    }
}
