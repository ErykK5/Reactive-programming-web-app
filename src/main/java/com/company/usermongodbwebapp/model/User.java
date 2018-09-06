package com.company.usermongodbwebapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document
public class User {

    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private Integer age;
    private Gender gender;
    @DBRef
    private List<Education> educations = new ArrayList<>();

    private PlaceOfEmployment placeOfEmployment;

    public User addEducation(Education education){
        this.educations.add(education);

        return this;
    }
}
