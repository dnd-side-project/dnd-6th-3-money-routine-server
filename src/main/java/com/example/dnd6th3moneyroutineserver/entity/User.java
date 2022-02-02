package com.example.dnd6th3moneyroutineserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String email;

    private String password;

    private String image;

    protected User() {}

    public User(String email, String password, String image) {
        this.email = email;
        this.password = password;
        this.image = image;
    }
}