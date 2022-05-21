package com.dream.study01.controller;

import com.dream.study01.domain.repository.UserRepository;
import com.dream.study01.dto.TokenDto;
import com.dream.study01.dto.TokenRequestDto;
import com.dream.study01.dto.UserRequestDto;
import com.dream.study01.dto.UserResponseDto;
import com.dream.study01.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(authService.signup(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
