package carrotMarcket.marcket.user.exception;

import carrotMarcket.marcket.global.exception.BusinessException;
import carrotMarcket.marcket.global.exception.ExceptionCode;

public class UserBusinessException extends BusinessException {

    public UserBusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
