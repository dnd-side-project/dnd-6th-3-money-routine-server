package com.example.dnd6th3moneyroutineserver.user;

import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    /**
     * Create User
     */
    @PostMapping("/join")
    public Long join(@RequestBody UserInfoDto userInfoDto) {
        return 1L;
    }

    /**
     * Login User
     */
    @PostMapping("/login")
    public Long login(@RequestBody UserInfoDto userInfoDto) {
        return 1L; // 유저 정보 반환
    }
}
