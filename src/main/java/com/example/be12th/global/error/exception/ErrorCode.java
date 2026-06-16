package com.example.be12th.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // common
    BAD_REQUEST(400, "잘못된 요청입니다."),
    UNAUTHORIZED(401, "인증이 필요합니다."),
    FORBIDDEN(403, "권한이 없습니다."),
    METHOD_NOT_ALLOWED(405, "지원하지 않는 메서드 형식입니다."),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류가 발생했습니다."),

    // auth
    EXPIRED_JWT_TOKEN(401, "만료된 JWT 토큰입니다."),
    INVALID_JWT_TOKEN(401, "유효하지 않은 JWT 토큰입니다."),
    MISSING_WEBSOCKET_AUTHORIZATION(401, "웹소켓 인증 헤더가 없습니다."),
    INVALID_PASSWORD(401, "비밀번호가 일치하지 않습니다."),
    AUTHENTICATION_REQUIRED(401, "인증되지 않은 사용자입니다."),

    // user
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    DELETED_USER(403, "탈퇴한 회원입니다."),
    ALREADY_DELETED_USER(409, "이미 탈퇴한 회원입니다."),
    EMAIL_ALREADY_EXISTS(409, "이미 회원가입된 이메일입니다."),
    EMAIL_ALREADY_VERIFIED(409, "이미 인증이 완료되었습니다."),
    OIDC_EMAIL_NOT_FOUND(400, "OIDC 제공자에서 이메일을 제공하지 않았습니다."),
    INVALID_FIREBASE_TOKEN(401, "유효하지 않은 Firebase 토큰입니다."),
    FIREBASE_USER_NOT_FOUND(404, "Firebase 유저를 찾을 수 없습니다."),
    FIREBASE_INITIALIZE_FAILED(500, "Firebase 초기화에 실패했습니다."),
    PHONE_VERIFICATION_REQUIRED(403, "휴대전화번호 인증이 필요합니다."),
    PHONE_NUMBER_REQUIRED(400, "전화번호는 비어 있을 수 없습니다."),
    PHONE_NUMBER_ALREADY_EXISTS(409, "이미 사용 중인 전화번호입니다."),
    SMS_CODE_EXPIRED(400, "인증번호가 만료되었거나 존재하지 않습니다."),
    SMS_CODE_MISMATCH(400, "인증번호가 일치하지 않습니다."),
    SMS_SENDER_NOT_CONFIGURED(500, "Solapi 발신번호가 설정되지 않았습니다."),
    SMS_SEND_FAILED(502, "문자 발송에 실패했습니다. Solapi 설정과 발신번호 승인 상태를 확인해주세요."),
    SMS_EMPTY_RESPONSE(502, "솔라피 응답이 비어있습니다."),
    SMS_UNKNOWN_ERROR(502, "문자 발송 중 오류가 발생했습니다."),
    SMS_UNEXPECTED_ERROR(502, "문자 발송 중 예상하지 못한 오류가 발생했습니다."),

    // recruitment
    INVALID_RECRUITMENT_LEAGUE_GROUP(400, "k1Group 또는 k2Group 중 하나만 선택해야 합니다."),
    RECRUITMENT_NOT_FOUND(404, "해당 모집 게시글을 찾을 수 없습니다."),
    RECRUITMENT_EXPIRED(400, "마감된 모집글입니다."),
    ALREADY_JOINED_RECRUITMENT(409, "이미 가입 신청한 그룹입니다."),
    RECRUITMENT_FULL(409, "모집 인원이 가득 찼습니다."),

    // notice/comment
    NOTICE_NOT_FOUND(404, "해당 공지를 찾을 수 없습니다."),
    NOTICE_CREATE_FORBIDDEN(403, "공지글은 모집글 작성자만 작성할 수 있습니다."),
    NOTICE_MIN_MEMBER_REQUIRED(400, "공지글은 최소 4명이 모여야 작성할 수 있습니다."),
    NOTICE_READ_FORBIDDEN(403, "해당 모집게시물에 참여한 사람만 공지를 조회할 수 있습니다."),
    COMMENT_CREATE_FORBIDDEN(403, "해당 모집방에 참여한 사람만 댓글을 작성할 수 있습니다."),
    COMMENT_READ_FORBIDDEN(403, "해당 모집방에 참여한 사람만 댓글을 조회할 수 있습니다."),

    // favorite
    FAVORITE_PLAYER_ALREADY_EXISTS(409, "이미 즐겨찾기한 선수입니다."),
    FAVORITE_PLAYER_NOT_FOUND(404, "해당 즐겨찾기한 선수를 찾을 수 없습니다."),
    FAVORITE_TEAM_ALREADY_EXISTS(409, "이미 즐겨찾기된 구단입니다."),
    FAVORITE_TEAM_NOT_FOUND(404, "즐겨찾기한 구단을 찾을 수 없습니다."),

    // football
    PLAYER_NOT_FOUND(404, "해당 선수를 찾을 수 없습니다."),
    TEAM_NOT_FOUND(404, "해당 구단을 찾을 수 없습니다."),
    INVALID_LEAGUE(400, "지원하지 않는 리그입니다."),
    LEAGUE_STAT_NOT_FOUND(404, "해당 리그 통계를 찾을 수 없습니다."),
    PLAYER_GOALS_STAT_NOT_FOUND(404, "선수 goals 통계가 없습니다."),

    // fcm
    FCM_TOKEN_EMPTY(400, "FCM 토큰이 비어 있습니다."),
    FCM_SEND_FAILED(502, "FCM 전송에 실패했습니다.");

    private final int statusCode;
    private final String errorMessage;
}
