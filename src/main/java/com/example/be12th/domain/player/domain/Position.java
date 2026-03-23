package com.example.be12th.domain.player.domain;

public enum Position {
    GK,
    DF,
    CB,
    SW,
    FB,
    LB,
    RB,
    WB,
    LWB,
    RWB,
    MF,
    CM,
    DM,
    AM,
    LM,
    RM,
    LW,
    RW,
    FW,
    CF,
    SS;

    public static Position from(int positionId) {
        return switch (positionId){
            case 1 -> GK;
            case 2 -> DF;
            case 3 -> CB;
            case 4 -> SW;
            case 5 -> FB;
            case 6 -> LB;
            case 7 -> RB;
            case 8 -> WB;
            case 9 -> LWB;
            case 10 -> RWB;
            case 11 -> MF;
            case 12 -> CM;
            case 13 -> DM;
            case 14 -> AM;
            case 15 -> LM;
            case 16 -> RM;
            case 17 -> LW;
            case 18 -> RW;
            case 19 -> FW;
            case 20 -> CF;
            case 21 -> SS;
            default -> throw new IllegalArgumentException("Invalid position id: " + positionId);
        };
    }
}
