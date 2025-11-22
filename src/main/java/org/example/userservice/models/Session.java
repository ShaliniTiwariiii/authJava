package org.example.userservice.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;

import java.util.Date;

public class Session extends BaseModel{
    private String token;
    private Date expiration;
    @ManyToMany
    private User user ;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;

}
