package com.example.be12th.domain.fcm.presentation.dto.request;

import lombok.Getter;

@Getter
public class NotificationSettingRequest {
    private Boolean notificationEnabled;
    private Boolean oneHourBeforeEnabled;
    private Boolean thirtyMinutesBeforeEnabled;
    private Boolean fifteenMinutesBeforeEnabled;
    private Boolean matchStartEnabled;
}