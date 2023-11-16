package carrotMarcket.marcket.user.exception;

import carrotMarcket.marcket.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public enum UserExceptionCode implements ExceptionCode {

    EXIST_USER("EXIST_USER", "이미 가입되어 있는 유저입니다."),
    INCORRECT_REFRESH_TOKEN("INCORRECT_REFRESH_TOKEN", "Refresh Token 이 유효하지 않습니다."),
    LOGOUT_USER("LOGOUT_USER", "로그아웃 된 사용자입니다."),
    INCORRECT_TOKEN("INCORRECT_TOKEN", "토큰의 유저 정보가 일치하지 않습니다."),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "자격 증명에 실패하였습니다.");

    private final String code;
    private final String message;

    UserExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
