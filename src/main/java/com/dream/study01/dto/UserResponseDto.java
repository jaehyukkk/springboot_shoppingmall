package com.dream.study01.dto;

import com.dream.study01.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String email;

    public static UserResponseDto of(User user){
        return new UserResponseDto(user.getEmail());
    }
}
