package com.dream.study01.service;

import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.repository.UserRepository;
import com.dream.study01.dto.UserResponseDto;
import com.dream.study01.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public List<UserResponseDto> searchUser(String keyword) {
        List<User> userList = userRepository.findByEmailContaining(keyword);
        if (userList.isEmpty()) {
            throw new RuntimeException("검색된 유저 결과가 없습니다.");
        }
        List<UserResponseDto> userDtoList = new ArrayList<>();
        for(User user : userList){
            userDtoList.add(UserResponseDto.of(user));
        }
        return userDtoList;
    }
}
