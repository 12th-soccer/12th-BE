package com.example.be12th.domain.matchevent.presentation.dto;

import com.example.be12th.domain.matchevent.domain.MatchEvent;
import com.example.be12th.domain.matchevent.domain.MatchEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchEventResponse {
    private Long eventId;
    private Long clubId;
    private String playerName;
    private String playerImageUrl;
    private Integer eventMinute;
    private MatchEventType matchEventType;

    public static MatchEventResponse from(MatchEvent matchEvent) {
        return new MatchEventResponse(
                matchEvent.getId(),
                matchEvent.getPlayer().getClub().getId(),
                matchEvent.getPlayer().getName(),
                matchEvent.getPlayer().getPlayerImageUrl(),
                matchEvent.getEventMinute(),
                matchEvent.getEventType()
        );
    }
}
