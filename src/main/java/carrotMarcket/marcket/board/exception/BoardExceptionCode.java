package carrotMarcket.marcket.board.exception;

import carrotMarcket.marcket.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public enum BoardExceptionCode implements ExceptionCode {

    NOT_EXIST_BOARD("NOT_EXIST_BOARD", "존재하지 않는 게시글 입니다.");

    private final String code;
    private final String message;

    BoardExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
