package com.example.be12th.domain.recruitment.domain;

import com.example.be12th.domain.recruitment.domain.category.AgeGroup;
import com.example.be12th.domain.recruitment.domain.category.GenderGroup;
import com.example.be12th.domain.recruitment.domain.category.K1Group;
import com.example.be12th.domain.recruitment.domain.category.K2Group;
import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "tbl_recruitment")
public class Recruitment {
    @Id
    @Column(name = "recruitment_Id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitmentId;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "head_count", nullable = false)
    private Integer headCount;

    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    private GenderGroup genderGroup;

    @Enumerated(EnumType.STRING)
    private K1Group k1Group;

    @Enumerated(EnumType.STRING)
    private K2Group k2Group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
