package com.example.be12th.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_user")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true , length = 100)
    private String email;

    @Column(name = "name",length = 100)
    private String name;

    @Column(name = "password",nullable = false)
    private String password;
    
}
