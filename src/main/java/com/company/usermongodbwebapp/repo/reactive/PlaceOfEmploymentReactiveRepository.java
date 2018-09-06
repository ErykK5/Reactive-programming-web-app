package com.company.usermongodbwebapp.repo.reactive;

import com.company.usermongodbwebapp.model.PlaceOfEmployment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface PlaceOfEmploymentReactiveRepository extends ReactiveMongoRepository<PlaceOfEmployment, String> {

    Mono<PlaceOfEmployment> findByEmployer(String employer);
}
