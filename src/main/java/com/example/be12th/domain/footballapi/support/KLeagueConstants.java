package com.example.be12th.domain.footballapi.support;

public final class KLeagueConstants {

    public static final Long KLEAGUE1_ID = 292L;
    public static final Long KLEAGUE2_ID = 293L;

    private KLeagueConstants() {
    }

    public static Long resolveLeagueId(String league) {
        return switch (league.toLowerCase()){
            case "kleague1" -> KLEAGUE1_ID;
            case "kleague2" -> KLEAGUE2_ID;
            default -> throw new IllegalArgumentException("지원하지 않는 리그입니다." + league);
        };
    }
}
