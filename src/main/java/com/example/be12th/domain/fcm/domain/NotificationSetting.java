package com.example.be12th.domain.fcm.domain;

import com.example.be12th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NotificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Builder.Default
    private Boolean oneHourBeforeEnabled = true;

    @Builder.Default
    private Boolean thirtyMinutesBeforeEnabled = true;

    @Builder.Default
    private Boolean fifteenMinutesBeforeEnabled = true;

    @Builder.Default
    private Boolean matchStartEnabled = true;

    @Builder.Default
    private Boolean notificationEnabled = true;

    public void update(
            Boolean notificationEnabled,
            Boolean oneHourBeforeEnabled,
            Boolean thirtyMinutesBeforeEnabled,
            Boolean fifteenMinutesBeforeEnabled,
            Boolean matchStartEnabled
    ) {
        if (notificationEnabled != null) this.notificationEnabled = notificationEnabled;
        if (oneHourBeforeEnabled != null) this.oneHourBeforeEnabled = oneHourBeforeEnabled;
        if (thirtyMinutesBeforeEnabled != null) this.thirtyMinutesBeforeEnabled = thirtyMinutesBeforeEnabled;
        if (fifteenMinutesBeforeEnabled != null) this.fifteenMinutesBeforeEnabled = fifteenMinutesBeforeEnabled;
        if (matchStartEnabled != null) this.matchStartEnabled = matchStartEnabled;
    }
}