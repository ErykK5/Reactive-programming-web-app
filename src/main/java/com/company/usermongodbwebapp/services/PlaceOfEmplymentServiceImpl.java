package com.company.usermongodbwebapp.services;

import com.company.usermongodbwebapp.model.PlaceOfEmployment;
import com.company.usermongodbwebapp.repo.PlaceOfEmploymentRepository;
import com.company.usermongodbwebapp.repo.reactive.PlaceOfEmploymentReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PlaceOfEmplymentServiceImpl implements PlaceOfEmplymentService {

    private final PlaceOfEmploymentReactiveRepository placeOfEmploymentReactiveRepository;

    public PlaceOfEmplymentServiceImpl(PlaceOfEmploymentReactiveRepository placeOfEmploymentReactiveRepository) {
        this.placeOfEmploymentReactiveRepository = placeOfEmploymentReactiveRepository;
    }

    @Override
    public Flux<PlaceOfEmployment> findAllPlacesOfEmployment() {
        return placeOfEmploymentReactiveRepository.findAll();
    }
}
