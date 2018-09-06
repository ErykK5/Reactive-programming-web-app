package com.company.usermongodbwebapp.repo;

import com.company.usermongodbwebapp.model.PlaceOfEmployment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlaceOfEmploymentRepository extends CrudRepository<PlaceOfEmployment, String> {
    Optional<PlaceOfEmployment> findByEmployer(String employer);
}
