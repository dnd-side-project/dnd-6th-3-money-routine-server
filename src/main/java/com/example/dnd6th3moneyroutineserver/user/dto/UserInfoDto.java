package com.example.dnd6th3moneyroutineserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String email;
    private String password;
}
