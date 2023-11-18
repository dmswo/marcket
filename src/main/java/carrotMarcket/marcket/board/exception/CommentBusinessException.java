package carrotMarcket.marcket.board.exception;

import carrotMarcket.marcket.global.exception.BusinessException;
import carrotMarcket.marcket.global.exception.ExceptionCode;

public class CommentBusinessException extends BusinessException {

    public CommentBusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
