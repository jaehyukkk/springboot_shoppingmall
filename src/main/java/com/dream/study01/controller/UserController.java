package com.dream.study01.controller;

import com.dream.study01.dto.UserResponseDto;
import com.dream.study01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public ResponseEntity<UserResponseDto> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserInfo(email));
    }

    @GetMapping(value = "/api/v1/user/search")
    public ResponseEntity<List<UserResponseDto>> searchUser(@RequestParam(value = "email") String keyword){
        List<UserResponseDto> userResponseDtoList = userService.searchUser(keyword);
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }
}
