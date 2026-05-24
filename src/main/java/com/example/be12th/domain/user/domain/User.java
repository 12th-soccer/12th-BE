package com.example.be12th.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "phoneNumber", unique = true , length = 12)
    private String phoneNumber;

    @Column(name = "name",length = 100)
    private String name;

    @Column(name = "password",nullable = false)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    private boolean deleted = false;

    private LocalDateTime deletedAt;

    public void withdraw() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    public void updateName(String name){
        this.name = name;
    }

    public boolean isPhoneVerified() {
        return phoneNumber != null;
    }
}
