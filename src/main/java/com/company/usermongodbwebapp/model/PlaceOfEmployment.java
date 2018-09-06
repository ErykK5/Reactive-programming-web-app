package com.company.usermongodbwebapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
public class PlaceOfEmployment {

    @Id
    private String id;
    private String employer;
}
