package com.example.dnd6th3moneyroutineserver.category;

import com.example.dnd6th3moneyroutineserver.user.User;

import javax.persistence.*;

@Entity
public class CustomCategory {

    @Id @GeneratedValue
    @Column(name = "CUSTOM_CATEGORY_ID")
    private Long id;

    private String name;

    private String detail;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
