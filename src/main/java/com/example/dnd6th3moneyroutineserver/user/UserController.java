package com.example.dnd6th3moneyroutineserver.user;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.user.dto.AccessTokenDto;
import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입", notes = "이메일과 비밀번호로 회원가입을 진행한다.")
    public ResponseEntity join(@RequestBody UserInfoDto userInfoDto) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.JOIN_SUCCESS, userService.join(userInfoDto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일과 비밀번호로 로그인을 진행한다.")
    public ResponseEntity login(@RequestBody UserInfoDto userInfoDto) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, userService.login(userInfoDto)), HttpStatus.OK);
    }

}
