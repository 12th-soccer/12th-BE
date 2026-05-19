package com.example.be12th.domain.spoiler.domain;

import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_spoiler")
public class SpoilerSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spoiler_id",nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false, unique = true)
    private User user;

    @Builder.Default
    private Boolean spoilerEnabled = true;

    public void update(
            Boolean spoilerEnabled
    ){
        if(spoilerEnabled != null ) this.spoilerEnabled = spoilerEnabled;
    }
}
