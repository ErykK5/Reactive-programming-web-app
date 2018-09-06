package com.company.usermongodbwebapp.repo;

import com.company.usermongodbwebapp.model.Education;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EducationRepository extends CrudRepository<Education, String> {

    Optional<Education> findByAgencyName(String agencyName);
}
