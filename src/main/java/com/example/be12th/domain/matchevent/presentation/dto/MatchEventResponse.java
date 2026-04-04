package com.example.be12th.domain.matchevent.presentation.dto;

import com.example.be12th.domain.matchevent.domain.MatchEvent;
import com.example.be12th.domain.matchevent.domain.MatchEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchEventResponse {
    private Long eventId;
    private String playerName;
    private Integer eventMinute;
    private MatchEventType matchEventType;

    public static MatchEventResponse from(MatchEvent matchEvent) {
        return new MatchEventResponse(
                matchEvent.getId(),
                matchEvent.getPlayer().getName(),
                matchEvent.getEventMinute(),
                matchEvent.getEventType()
        );
    }
}
