package com.company.usermongodbwebapp.bootstrapTest;

import com.company.usermongodbwebapp.model.Education;
import com.company.usermongodbwebapp.model.Gender;
import com.company.usermongodbwebapp.model.User;
import com.company.usermongodbwebapp.model.PlaceOfEmployment;
import com.company.usermongodbwebapp.repo.EducationRepository;
import com.company.usermongodbwebapp.repo.PlaceOfEmploymentRepository;
import com.company.usermongodbwebapp.repo.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final PlaceOfEmploymentRepository placeOfEmploymentRepository;

    public UserBootstrap(UserRepository userRepository, EducationRepository educationRepository, PlaceOfEmploymentRepository placeOfEmploymentRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
        this.placeOfEmploymentRepository = placeOfEmploymentRepository;
    }

    public List<Education> loadEducations(){

        ArrayList<Education> eduList = new ArrayList<>(9);

        Education user1PrimaryEducation = new Education();
        user1PrimaryEducation.setAgencyName("Boston primary school nr. 23");
        user1PrimaryEducation.setYearOfBeginning(2004);
        user1PrimaryEducation.setYearOfEnding(2012);

        Education user2PrimaryEducation = new Education();
        user2PrimaryEducation.setAgencyName("New York primary school nr. 228");
        user2PrimaryEducation.setYearOfBeginning(2002);
        user2PrimaryEducation.setYearOfEnding(2010);

        Education user3PrimaryEducation = new Education();
        user3PrimaryEducation.setAgencyName("Miami primary school nr. 7");
        user3PrimaryEducation.setYearOfBeginning(1994);
        user3PrimaryEducation.setYearOfEnding(2004);

        Education user1SecondaryEducation = new Education();
        user1SecondaryEducation.setAgencyName("Boston secondary school nr. 4");
        user1SecondaryEducation.setYearOfBeginning(2013);
        user1SecondaryEducation.setYearOfEnding(2016);

        Education user2SecondaryEducation = new Education();
        user2SecondaryEducation.setAgencyName("New York secondary school nr.90");
        user2SecondaryEducation.setYearOfBeginning(2011);
        user2SecondaryEducation.setYearOfEnding(2014);

        Education user3SecondaryEducation = new Education();
        user3SecondaryEducation.setAgencyName("Miami secondary school nr. 21");
        user3SecondaryEducation.setYearOfBeginning(2005);
        user3SecondaryEducation.setYearOfEnding(2008);

        Education user1HigherEducation = new Education();
        user1HigherEducation.setAgencyName("Harvard");
        user1HigherEducation.setYearOfBeginning(2017);
        user1HigherEducation.setYearOfEnding(2022);

        Education user2HigherEducation = new Education();
        user2HigherEducation.setAgencyName("Yale");
        user2HigherEducation.setYearOfBeginning(2015);
        user2HigherEducation.setYearOfEnding(2020);

        Education user3HigherEducation = new Education();
        user3HigherEducation.setAgencyName("Stanford");
        user3HigherEducation.setYearOfBeginning(2009);
        user3HigherEducation.setYearOfEnding(2014);

        eduList.add( user1PrimaryEducation );
        eduList.add( user1SecondaryEducation );
        eduList.add( user1HigherEducation );
        eduList.add( user2PrimaryEducation );
        eduList.add( user2SecondaryEducation );
        eduList.add( user2HigherEducation );
        eduList.add( user3PrimaryEducation );
        eduList.add( user3SecondaryEducation );
        eduList.add( user3HigherEducation );

        return eduList;
    }

    public void loadPlaceOfEmployment(){
        PlaceOfEmployment user1Employment = new PlaceOfEmployment();
        PlaceOfEmployment user2Employment = new PlaceOfEmployment();
        PlaceOfEmployment user3Employment = new PlaceOfEmployment();

        user1Employment.setEmployer("US Pharmacia");
        user2Employment.setEmployer("Polfarmex SA");
        user3Employment.setEmployer("YSI SA");

        placeOfEmploymentRepository.save( user1Employment );
        placeOfEmploymentRepository.save( user2Employment );
        placeOfEmploymentRepository.save( user3Employment );

    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadPlaceOfEmployment();
        loadUsers( loadEducations() );
    }

    public void loadUsers( List<Education> eduList ) {

        //get Place of employment
        Optional<PlaceOfEmployment> optUser1PlaceOfEmployment = placeOfEmploymentRepository.findByEmployer("US Pharmacia");
        Optional<PlaceOfEmployment> optUser2PlaceOfEmployment = placeOfEmploymentRepository.findByEmployer("Polfarmex SA");
        Optional<PlaceOfEmployment> optUser3PlaceOfEmployment = placeOfEmploymentRepository.findByEmployer("YSI SA");

        PlaceOfEmployment place1 = optUser1PlaceOfEmployment.get();
        PlaceOfEmployment place2 = optUser2PlaceOfEmployment.get();
        PlaceOfEmployment place3 = optUser3PlaceOfEmployment.get();

        Education edu1 = eduList.get(0);
        Education edu2 = eduList.get(1);
        Education edu3 = eduList.get(2);
        Education edu4 = eduList.get(3);
        Education edu5 = eduList.get(4);
        Education edu6 = eduList.get(5);
        Education edu7 = eduList.get(6);
        Education edu8 = eduList.get(7);
        Education edu9 = eduList.get(8);

        //User1
        User user1 = new User();
        user1.setFirstName("Emma");
        user1.setLastName("Doe");
        user1.setGender(Gender.FEMALE);
        user1.setAge(21);
        user1.setPlaceOfEmployment( place1 );
        edu1.setUser( user1.addEducation( edu1 ) );
        edu2.setUser( user1.addEducation( edu2 ) );
        edu3.setUser( user1.addEducation( edu3 ) );

        //User2
        User user2 = new User();
        user2.setFirstName("Natalie");
        user2.setLastName("Smith");
        user2.setGender(Gender.FEMALE);
        user2.setAge(23);
        user2.setPlaceOfEmployment( place2 );
        edu4.setUser( user2.addEducation( edu4 ) );
        edu5.setUser( user2.addEducation( edu5 ) );
        edu6.setUser( user2.addEducation( edu6 ) );

        //User3
        User user3 = new User();
        user3.setFirstName("Tom");
        user3.setLastName("Stephenson");
        user3.setGender(Gender.MALE);
        user3.setAge(31);
        user3.setPlaceOfEmployment( place3 );
        edu7.setUser( user3.addEducation( edu7 ) );
        edu8.setUser( user3.addEducation( edu8 ) );
        edu9.setUser( user3.addEducation( edu9 ) );

        userRepository.save( user1 );
        userRepository.save( user2 );
        userRepository.save( user3 );

        user1.setPlaceOfEmployment( place1 );
        user2.setPlaceOfEmployment( place2 );
        user3.setPlaceOfEmployment( place3 );

        educationRepository.save( edu1 );
        educationRepository.save( edu2 );
        educationRepository.save( edu3 );
        educationRepository.save( edu4 );
        educationRepository.save( edu5 );
        educationRepository.save( edu6 );
        educationRepository.save( edu7 );
        educationRepository.save( edu8 );
        educationRepository.save( edu9 );
    }
}
