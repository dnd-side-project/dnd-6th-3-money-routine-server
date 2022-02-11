package com.example.dnd6th3moneyroutineserver.user.service;

import com.example.dnd6th3moneyroutineserver.security.JwtTokenProvider;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.dto.AccessTokenDto;
import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public AccessTokenDto join(UserInfoDto userInfoDto) {
        User user = userRepository.save(User.builder()
                .email(userInfoDto.getEmail())
                .password(passwordEncoder.encode(userInfoDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        String token =  jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return new AccessTokenDto(token);
    }

    @Transactional
    public AccessTokenDto login(UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(userInfoDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exists"));
        if (!passwordEncoder.matches(userInfoDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String token =  jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return new AccessTokenDto(token);
    }

    @Transactional
    public Long currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}