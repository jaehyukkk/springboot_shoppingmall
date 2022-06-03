package com.dream.study01.dto;

import com.dream.study01.domain.entity.User;
import com.dream.study01.enums.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String email;
    private String name;
    private String userRole;


    public static UserResponseDto of(User user){
        return new UserResponseDto(user.getId(),user.getEmail(), user.getName(),user.getUserRole());
    }
}
