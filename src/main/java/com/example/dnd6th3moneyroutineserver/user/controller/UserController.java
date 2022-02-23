package com.example.dnd6th3moneyroutineserver.user.controller;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.user.dto.JoinResponseDto;
import com.example.dnd6th3moneyroutineserver.user.dto.UserInfoDto;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입", notes = "이메일과 비밀번호로 회원가입을 진행한다.")
    public ResponseEntity join(@RequestBody UserInfoDto userInfoDto) {
        JoinResponseDto joinResponseDto = userService.join(userInfoDto);
        if (joinResponseDto.isExist()) {
            return new ResponseEntity(CustomResponse
                    .response(StatusCode.BAD_REQUEST, ResponseMessage.JOIN_FAIL_DUPLICATE, joinResponseDto), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(CustomResponse
                    .response(StatusCode.OK, ResponseMessage.JOIN_SUCCESS, joinResponseDto), HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일과 비밀번호로 로그인을 진행한다.")
    public ResponseEntity login(@RequestBody UserInfoDto userInfoDto) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, userService.login(userInfoDto)), HttpStatus.OK);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "로그아웃을 진행한다.")
    public ResponseEntity logout() {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.LOGOUT_SUCCESS, userService.logout()), HttpStatus.OK);
    }

    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴를 진행한다.")
    public ResponseEntity withdraw() {
        userService.withdraw();
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.WITHDRAW_SUCCESS), HttpStatus.OK);
    }

    @PostMapping("/issue")
    @ApiOperation(value = "토큰 재발급", notes = "refresh 토큰을 이용하여 access 토큰을 재발급받는다.")
    public ResponseEntity issue(HttpServletRequest request) {
        return ResponseEntity.ok().body(userService.issue(request));
    }

}
