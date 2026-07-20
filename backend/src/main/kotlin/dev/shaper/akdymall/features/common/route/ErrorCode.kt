package dev.shaper.akdymall.features.common.route

enum class ErrorCode(val code: Int, val message: String) {

    // 1xxx: 공통 / 요청
    INVALID_REQUEST(1000, "잘못된 요청입니다."),
    MISSING_PARAMETER(1001, "필수 파라미터가 누락되었습니다."),
    INVALID_PARAMETER_TYPE(1002, "파라미터 타입이 올바르지 않습니다."),
    INVALID_PARAMETER_VALUE(1003, "파라미터 값이 유효하지 않습니다."),
    INVALID_JSON_FORMAT(1004, "요청 본문(JSON) 형식이 올바르지 않습니다."),
    UNSUPPORTED_HTTP_METHOD(1005, "지원하지 않는 HTTP 메서드입니다."),
    UNSUPPORTED_MEDIA_TYPE(1006, "지원하지 않는 Content-Type입니다."),
    REQUEST_BODY_TOO_LARGE(1007, "요청 크기가 허용 범위를 초과했습니다."),
    TOO_MANY_REQUESTS(1008, "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),
    INVALID_HEADER(1009, "필수 헤더가 없거나 올바르지 않습니다."),
    INVALID_PAGINATION(1010, "페이지네이션 파라미터가 올바르지 않습니다."),
    INVALID_SORT_OPTION(1011, "정렬 옵션이 올바르지 않습니다."),
    INVALID_DATE_FORMAT(1012, "날짜 형식이 올바르지 않습니다."),
    INVALID_ENUM_VALUE(1013, "허용되지 않는 값입니다."),

    // 2xxx: 인증 / 인가
    UNAUTHORIZED(2000, "인증이 필요합니다."),
    INVALID_TOKEN(2001, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(2002, "토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(2003, "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(2004, "리프레시 토큰이 만료되었습니다."),
    FORBIDDEN(2005, "접근 권한이 없습니다."),
    LOGIN_FAILED(2006, "아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCOUNT_LOCKED(2007, "계정이 잠겨 있습니다."),
    ACCOUNT_SUSPENDED(2008, "정지된 계정입니다."),
    PASSWORD_ATTEMPTS_EXCEEDED(2009, "비밀번호 입력 횟수를 초과했습니다."),
    OAUTH_FAILED(2010, "소셜 로그인에 실패했습니다."),
    SESSION_EXPIRED(2011, "세션이 만료되었습니다."),
    INSUFFICIENT_ROLE(2012, "해당 작업을 수행할 권한 등급이 아닙니다."),

    // 3xxx: 사용자
    USER_NOT_FOUND(3000, "존재하지 않는 사용자입니다."),
    DUPLICATE_EMAIL(3001, "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(3002, "이미 사용 중인 닉네임입니다."),
    INVALID_EMAIL_FORMAT(3003, "이메일 형식이 올바르지 않습니다."),
    INVALID_PASSWORD_FORMAT(3004, "비밀번호 형식이 올바르지 않습니다."),
    SAME_AS_OLD_PASSWORD(3005, "이전 비밀번호와 동일합니다."),
    EMAIL_NOT_VERIFIED(3006, "이메일 인증이 완료되지 않았습니다."),
    VERIFICATION_CODE_MISMATCH(3007, "인증 코드가 일치하지 않습니다."),
    VERIFICATION_CODE_EXPIRED(3008, "인증 코드가 만료되었습니다."),
    ALREADY_WITHDRAWN_USER(3009, "탈퇴한 사용자입니다."),

    // 4xxx: 리소스 / 데이터
    RESOURCE_NOT_FOUND(4000, "요청한 리소스를 찾을 수 없습니다."),
    RESOURCE_ALREADY_EXISTS(4001, "이미 존재하는 리소스입니다."),
    RESOURCE_CONFLICT(4002, "리소스 상태가 충돌합니다."),
    RESOURCE_DELETED(4003, "삭제된 리소스입니다."),
    NOT_RESOURCE_OWNER(4004, "리소스의 소유자가 아닙니다."),
    RESOURCE_LIMIT_EXCEEDED(4005, "생성 가능한 개수를 초과했습니다."),
    OPTIMISTIC_LOCK_CONFLICT(4006, "다른 요청과 충돌했습니다. 다시 시도해주세요."),
    INVALID_STATE_TRANSITION(4007, "현재 상태에서 수행할 수 없는 작업입니다."),

    // 5xxx: 서버 내부
    INTERNAL_SERVER_ERROR(5000, "서버 내부 오류가 발생했습니다."),
    DATABASE_ERROR(5001, "데이터베이스 처리 중 오류가 발생했습니다."),
    DB_CONNECTION_FAILED(5002, "데이터베이스 연결에 실패했습니다."),
    TRANSACTION_FAILED(5003, "트랜잭션 처리에 실패했습니다."),
    CACHE_ERROR(5004, "캐시 처리 중 오류가 발생했습니다."),
    SERIALIZATION_ERROR(5005, "데이터 직렬화/역직렬화에 실패했습니다."),
    CONFIGURATION_ERROR(5006, "서버 설정 오류입니다."),
    SERVICE_UNAVAILABLE(5007, "서비스를 일시적으로 사용할 수 없습니다."),
    TIMEOUT(5008, "처리 시간이 초과되었습니다."),

    // 6xxx: 외부 연동
    EXTERNAL_API_ERROR(6000, "외부 API 호출에 실패했습니다."),
    EXTERNAL_API_TIMEOUT(6001, "외부 API 응답 시간이 초과되었습니다."),
    SMS_SEND_FAILED(6002, "SMS 발송에 실패했습니다."),
    EMAIL_SEND_FAILED(6003, "이메일 발송에 실패했습니다."),
    PUSH_SEND_FAILED(6004, "푸시 알림 발송에 실패했습니다."),

    // 7xxx: 파일 / 업로드
    FILE_UPLOAD_FAILED(7000, "파일 업로드에 실패했습니다."),
    FILE_NOT_FOUND(7001, "파일을 찾을 수 없습니다."),
    FILE_SIZE_EXCEEDED(7002, "파일 크기가 허용 범위를 초과했습니다."),
    UNSUPPORTED_FILE_TYPE(7003, "지원하지 않는 파일 형식입니다."),
    FILE_DOWNLOAD_FAILED(7004, "파일 다운로드에 실패했습니다."),

    // 8xxx: 결제 (필요하면)
    PAYMENT_FAILED(8000, "결제에 실패했습니다."),
    PAYMENT_CANCELED(8001, "결제가 취소되었습니다."),
    INSUFFICIENT_BALANCE(8002, "잔액이 부족합니다."),
    DUPLICATE_PAYMENT(8003, "중복 결제 요청입니다."),
    REFUND_FAILED(8004, "환불 처리에 실패했습니다.")
}