package carrotMarcket.marcket.board.exception;

import carrotMarcket.marcket.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public enum CommentExceptionCode implements ExceptionCode {

    NOT_EXIST_COMMENT("NOT_EXIST_COMMENT", "존재하지 않는 댓글 입니다.");

    private final String code;
    private final String message;

    CommentExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
