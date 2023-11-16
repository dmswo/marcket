package carrotMarcket.marcket.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getCode());
        this.exceptionCode = exceptionCode;
    }
}
