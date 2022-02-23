package com.example.dnd6th3moneyroutineserver.user.service;

import com.example.dnd6th3moneyroutineserver.security.JwtTokenProvider;
import com.example.dnd6th3moneyroutineserver.user.dto.JoinResponseDto;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.dto.LoginResponseDto;
import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public JoinResponseDto join(UserInfoDto userInfoDto) {
        boolean exists = userRepository.existsByEmail(userInfoDto.getEmail());
        if (exists) {
            return new JoinResponseDto(true, null, null);
        }
        else {
            User user = userRepository.save(User.builder()
                    .email(userInfoDto.getEmail())
                    .password(passwordEncoder.encode(userInfoDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());

            String accessToken =  jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
            String refreshToken =  jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRoles());
            return new JoinResponseDto(false, accessToken, refreshToken);
        }
    }

    @Transactional
    public LoginResponseDto login(UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(userInfoDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exists"));
        if (!passwordEncoder.matches(userInfoDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken =  jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
        String refreshToken =  jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRoles());
        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public Long currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    @Transactional
    public LoginResponseDto issue(HttpServletRequest request) {
        String accessToken = "";
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String userPk = jwtTokenProvider.getUserPk(refreshToken);
            User user = userRepository.findById(Long.valueOf(userPk)).orElseThrow(() -> new IllegalArgumentException("User doesn't exists"));
            accessToken =  jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
            refreshToken =  jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRoles());
        }
        return new LoginResponseDto(accessToken, refreshToken);
    }
}
