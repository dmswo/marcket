package carrotMarcket.marcket.controller;

import carrotMarcket.marcket.exeption.CustomException;
import carrotMarcket.marcket.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(errorResponse);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> emptyResultDataHandler(EmptyResultDataAccessException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("존재하지 않는 데이터입니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> CustomException(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getExceptionCode().getCode())
                .message(e.getExceptionCode().getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(errorResponse);
    }

}
