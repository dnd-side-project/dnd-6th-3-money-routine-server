package com.example.dnd6th3moneyroutineserver.category;

import javax.persistence.*;

@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    private String detail;

}
