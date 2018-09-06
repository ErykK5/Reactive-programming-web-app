package com.company.usermongodbwebapp.services;

import com.company.usermongodbwebapp.model.Education;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface EducationService {

    Mono<Education> findByUserIdAndEducationId(String userId, String educationId );

    Mono<Education> saveEducation(Education education);

    Mono<Education> createEducation(Education education);

    Mono<Void> deleteById(String userId, String educationId );
}