package com.example.be12th.domain.favorite.domain;

import com.example.be12th.domain.club.domain.Club;
import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "tbl_favorite_club",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "club_id"})
        }
)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FavoriteClub {
    @Id
    @Column(name = "favorite_club_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
