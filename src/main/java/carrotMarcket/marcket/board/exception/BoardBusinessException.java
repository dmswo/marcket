package carrotMarcket.marcket.board.exception;

import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.global.exception.BusinessException;
import carrotMarcket.marcket.global.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class BoardBusinessException extends BusinessException {

    public BoardBusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
