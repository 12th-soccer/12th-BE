package com.example.be12th.domain.notice.domain;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "tbl_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id",nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id",nullable = false)
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false,name = "notice_description")
    private String description;
}
