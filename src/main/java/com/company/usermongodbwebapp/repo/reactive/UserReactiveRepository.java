package com.company.usermongodbwebapp.repo.reactive;

import com.company.usermongodbwebapp.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserReactiveRepository extends ReactiveMongoRepository<User, String
        > {
}
