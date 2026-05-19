package com.example.be12th.domain.join.domain;

import com.example.be12th.domain.recruitment.domain.Recruitment;
import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "tbl_join",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_join_user_recruitment", columnNames = {"user_id", "recruitment_id"})
        }
)
public class Join {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id" , nullable = false)
    private Recruitment recruitment;

}
