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

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(String email, String password, String name, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
    }
}
