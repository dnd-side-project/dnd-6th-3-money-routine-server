package com.example.dnd6th3moneyroutineserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private boolean success;
    private String accessToken;
    private String refreshToken;
}
