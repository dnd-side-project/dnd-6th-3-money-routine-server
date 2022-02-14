package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomCategory {

    @Id @GeneratedValue
    @Column(name = "CUSTOM_CATEGORY_ID")
    private Long id;

    private String name;

    private String detail;

    private String emoji;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
