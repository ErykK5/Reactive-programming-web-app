package com.company.usermongodbwebapp.services;

import com.company.usermongodbwebapp.model.Education;
import com.company.usermongodbwebapp.model.User;
import com.company.usermongodbwebapp.repo.EducationRepository;
import com.company.usermongodbwebapp.repo.UserRepository;
import com.company.usermongodbwebapp.repo.reactive.EducationReactiveRepository;
import com.company.usermongodbwebapp.repo.reactive.UserReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class EducationServiceImpl implements EducationService {

    private static final Logger log = Logger.getLogger( EducationServiceImpl.class.getName() );

    private final EducationReactiveRepository educationReactiveRepository;
    private final UserReactiveRepository userReactiveRepository;

    public EducationServiceImpl(EducationReactiveRepository educationReactiveRepository, UserReactiveRepository userReactiveRepository) {
        this.educationReactiveRepository = educationReactiveRepository;
        this.userReactiveRepository = userReactiveRepository;
    }

    @Override
    public Mono<Education> findByUserIdAndEducationId(String userId, String educationId ) {

        return userReactiveRepository
                .findById(userId)
                .flatMapIterable(User::getEducations)
                .filter( education -> education.getId().equalsIgnoreCase( educationId ) )
                .single()
                .map(education -> {
                    Education edu = education;
                    edu.getUser().setId( userId );
                    return edu;
                });
    }

    @Override
    public Mono<Education> saveEducation(Education education) {
        User user = userReactiveRepository.findById(education.getUser().getId()).block();

        if ( user == null ) {
            log.info("MEDICINE NOT FOUND");
            return Mono.just( new Education() );
        } else {

            Optional<Education> e = user.getEducations().stream().filter(edu -> edu.getId().equals(education.getId())).findFirst();

            if (e.isPresent()) {
                Education education1 = e.get();
                education1.setAgencyName(education.getAgencyName());
                education1.setYearOfBeginning(education.getYearOfBeginning());
                education1.setYearOfEnding(education.getYearOfEnding());
                education1.setUser(education.getUser());
                log.info(" e is present");
            } else {
                user.addEducation( education );  //check it
                log.info(" e is NOT present");
            }

            User user1 = userReactiveRepository.save(user).block();
            Optional<Education>  e2 = user1.getEducations().stream().filter(education1 -> education1.getId().equals(education.getId())).findFirst();
            log.info("[saveEducation] e2: " + e2.get().getAgencyName() );
            return Mono.just( e2.get() );
        }
    }

    @Override
    public Mono<Education> createEducation(Education education) {

        Education e = educationReactiveRepository.save(education).block();
        log.info("createEducation: Created education: " + e.getAgencyName() + " with id: " + e.getId() );
        return Mono.just( e );
    }

    @Override
    public Mono<Void> deleteById(String userId, String educationId) {

        User user = userReactiveRepository.findById( userId ).block();

        if( user != null ){
            Optional<Education> optionalEducation = user.getEducations().stream().filter(edu -> edu.getId().equals( educationId)).findFirst();

            if( optionalEducation.isPresent() ){
                Education educationToDelete = optionalEducation.get();
                educationToDelete.setUser( null );
                user.getEducations().remove( optionalEducation.get() );
                userReactiveRepository.save( user ).block();
            }
        } else {
            log.info( "Error: Education not found");
        }
        return Mono.empty();
    }
}
