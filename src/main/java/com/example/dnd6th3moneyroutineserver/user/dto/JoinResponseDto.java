package com.example.dnd6th3moneyroutineserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinResponseDto {
    private boolean exist;
    private String accessToken;
    private String refreshToken;
}