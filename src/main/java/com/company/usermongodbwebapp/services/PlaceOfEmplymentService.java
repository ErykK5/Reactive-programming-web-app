package com.company.usermongodbwebapp.services;

import com.company.usermongodbwebapp.model.PlaceOfEmployment;
import reactor.core.publisher.Flux;


public interface PlaceOfEmplymentService {

    Flux<PlaceOfEmployment> findAllPlacesOfEmployment();
}
