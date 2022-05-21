package com.dream.study01.domain.entity;

import com.dream.study01.enums.role.UserRole;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }
}
