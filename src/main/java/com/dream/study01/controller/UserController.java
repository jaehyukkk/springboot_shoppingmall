package com.dream.study01.controller;

import com.dream.study01.dto.UserResponseDto;
import com.dream.study01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserInfo(email));
    }
}
