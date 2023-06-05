package carrotMarcket.marcket.exeption;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
