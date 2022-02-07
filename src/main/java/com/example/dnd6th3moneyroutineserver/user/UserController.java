package com.example.dnd6th3moneyroutineserver.user;

import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입", notes = "이메일과 비밀번호로 회원가입을 진행한다.")
    public String join(@RequestBody UserInfoDto userInfoDto) {
        return userService.join(userInfoDto);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일과 비밀번호로 로그인을 진행한다.")
    public String login(@RequestBody UserInfoDto userInfoDto) {
        return userService.login(userInfoDto);
    }
}
