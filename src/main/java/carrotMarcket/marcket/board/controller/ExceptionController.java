package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> emptyResultDataHandler(EmptyResultDataAccessException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("존재하지 않는 데이터입니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(errorResponse);
    }

    @ExceptionHandler(BoardBusinessException.class)
    public ResponseEntity<ErrorResponse> CustomException(BoardBusinessException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getExceptionCode().getCode())
                .message(e.getExceptionCode().getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(errorResponse);
    }

}
