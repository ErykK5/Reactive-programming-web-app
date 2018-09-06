package com.company.usermongodbwebapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Getter
@Setter
@Document
public class Education {

    private String id = UUID.randomUUID().toString();
    private String agencyName;
    private Integer yearOfBeginning;
    private Integer yearOfEnding;
    @DBRef
    private User user;
}
