package com.dream.study01.service;

import com.dream.study01.domain.repository.UserRepository;
import com.dream.study01.dto.UserResponseDto;
import com.dream.study01.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(String email) {
        return userRepository.findByEmail(email)
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo(){
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .map(UserResponseDto::of)
                .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
    }
}
