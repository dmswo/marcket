package carrotMarcket.marcket.exeption;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    EXCEPTION_CODE("Question001", "존재하지 않는 질문입니다.");

    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
