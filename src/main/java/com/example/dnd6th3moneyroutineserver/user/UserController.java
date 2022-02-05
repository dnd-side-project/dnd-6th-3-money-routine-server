package com.example.dnd6th3moneyroutineserver.user;

import com.example.dnd6th3moneyroutineserver.security.JwtTokenProvider;
import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * Create User
     */
    @PostMapping("/join")
    public String join(@RequestBody UserInfoDto userInfoDto) {
        User user = userRepository.save(User.builder()
                .email(userInfoDto.getEmail())
                .password(passwordEncoder.encode(userInfoDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }

    /**
     * Login User
     */
    @PostMapping("/login")
    public String login(@RequestBody UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(userInfoDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exists"));
        if (!passwordEncoder.matches(userInfoDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }
}
