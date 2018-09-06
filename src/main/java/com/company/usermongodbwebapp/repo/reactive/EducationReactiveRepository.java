package com.company.usermongodbwebapp.repo.reactive;


import com.company.usermongodbwebapp.model.Education;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EducationReactiveRepository extends ReactiveMongoRepository<Education, String> {

    Mono<Education> findByAgencyName(String agencyName);
}
