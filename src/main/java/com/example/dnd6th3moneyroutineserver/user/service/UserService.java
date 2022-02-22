package com.example.dnd6th3moneyroutineserver.user.service;

import com.example.dnd6th3moneyroutineserver.security.JwtTokenProvider;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.dto.JwtTokenDto;
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
    public JwtTokenDto join(UserInfoDto userInfoDto) {
        User user = userRepository.save(User.builder()
                .email(userInfoDto.getEmail())
                .password(passwordEncoder.encode(userInfoDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        String accessToken =  jwtTokenProvider.createAccessToken(user.getId(), user.getRoles());
        String refreshToken =  jwtTokenProvider.createRefreshToken(user.getId(), user.getRoles());
        return new JwtTokenDto(accessToken, refreshToken);
    }

    @Transactional
    public JwtTokenDto login(UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(userInfoDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exists"));
        if (!passwordEncoder.matches(userInfoDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken =  jwtTokenProvider.createAccessToken(user.getId(), user.getRoles());
        String refreshToken =  jwtTokenProvider.createRefreshToken(user.getId(), user.getRoles());
        return new JwtTokenDto(accessToken, refreshToken);
    }

    @Transactional
    public Long currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    @Transactional
    public JwtTokenDto issue(HttpServletRequest request) {
        String accessToken = "";
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String userPk = jwtTokenProvider.getUserPk(refreshToken);
            User user = userRepository.findById(Long.valueOf(userPk)).orElseThrow(() -> new IllegalArgumentException("User doesn't exists"));
            accessToken =  jwtTokenProvider.createAccessToken(user.getId(), user.getRoles());
            refreshToken =  jwtTokenProvider.createRefreshToken(user.getId(), user.getRoles());
        }
        return new JwtTokenDto(accessToken, refreshToken);
    }
}
