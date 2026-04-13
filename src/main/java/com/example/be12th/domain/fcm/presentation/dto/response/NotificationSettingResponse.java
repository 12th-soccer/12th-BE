package com.example.be12th.domain.fcm.presentation.dto.response;

import com.example.be12th.domain.fcm.domain.NotificationSetting;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationSettingResponse {
    private Boolean notificationEnabled;
    private Boolean oneHourBeforeEnabled;
    private Boolean thirtyMinutesBeforeEnabled;
    private Boolean fifteenMinutesBeforeEnabled;
    private Boolean matchStartEnabled;

    public static NotificationSettingResponse from(NotificationSetting setting) {
        return new NotificationSettingResponse(
                setting.getNotificationEnabled(),
                setting.getOneHourBeforeEnabled(),
                setting.getThirtyMinutesBeforeEnabled(),
                setting.getFifteenMinutesBeforeEnabled(),
                setting.getMatchStartEnabled()
        );
    }
}
