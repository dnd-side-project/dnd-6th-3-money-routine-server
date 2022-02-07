package com.example.dnd6th3moneyroutineserver.user;

import com.example.dnd6th3moneyroutineserver.security.JwtTokenProvider;
import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
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
    public String join(UserInfoDto userInfoDto) {
        User user = userRepository.save(User.builder()
                .email(userInfoDto.getEmail())
                .password(passwordEncoder.encode(userInfoDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }

    @Transactional
    public String login(UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(userInfoDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exists"));
        if (!passwordEncoder.matches(userInfoDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }
}
