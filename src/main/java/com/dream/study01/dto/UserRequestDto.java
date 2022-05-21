package com.dream.study01.dto;

import com.dream.study01.domain.entity.User;
import com.dream.study01.enums.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .userRole(UserRole.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(email, password);
    }


}
