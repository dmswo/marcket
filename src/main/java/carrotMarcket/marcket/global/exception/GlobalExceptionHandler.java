package carrotMarcket.marcket.global.exception;

import carrotMarcket.marcket.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> invalidRequestHandler(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.failResponse(bindingResult));
    }

    @ExceptionHandler
    protected ResponseEntity<ApiResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.errorResponse(e.getExceptionCode()));
    }
}
