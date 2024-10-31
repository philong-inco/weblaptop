package com.dantn.weblaptop.exception;


import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.dto.response.ApiResponseError;
import com.dantn.weblaptop.dto.response.ResponseLong;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllException(Exception exception) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setError(exception.getMessage());
        apiResponse.setMessage("Exception : " + ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(errorCode.getCode());
        apiResponse.setError(errorCode.getMessage());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ApiResponse<Object>> handleNumberFormatException(NumberFormatException exception) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setError(exception.getMessage());
        apiResponse.setMessage("Cannot format number");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoResourceFoundException(NoResourceFoundException exception) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(ErrorCode.NOT_FOUND.getCode());
        apiResponse.setError(ErrorCode.NOT_FOUND.getMessage());
        apiResponse.setMessage("404 : Not found");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ApiResponseError>>> handleValidationError(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        List<ApiResponseError> errors = fieldErrors.stream()
                .map(fieldError -> {
                    String enumKey = fieldError.getDefaultMessage();
                    ErrorCode errorCode;
                    try {
                        errorCode = ErrorCode.valueOf(enumKey);
                    } catch (IllegalArgumentException e) {
                        errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
                    }
                    ApiResponseError apiError = new ApiResponseError();
                    apiError.setField(fieldError.getField());
                    apiError.setMessages(errorCode.getMessage());
                    apiError.setErrorCode(errorCode.getCode());
                    return apiError;
                }).toList();
        ApiResponse<List<ApiResponseError>> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setError("Validation failed 1");
        apiResponse.setMessage(errors);
        return ResponseEntity.badRequest().body(apiResponse);
    }
    // exception JWT
    @ExceptionHandler(value = MyJwtException.class)
    public ResponseEntity<ResponseLong<String>> expiredJwtException(MyJwtException ex) {
        ResponseLong<String> apiResponse = new ResponseLong<>(
                ex.getStatusCode(), ex.getMessage(), null, null, null, null, null
        );
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(apiResponse);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseLong<String>> badCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseLong<String>(
            705, "Thông tin đăng nhập không chính xác.", null, null, null, null, null
        ));
    }

    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ResponseLong<String>> authenticationCredentialsNotFoundException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseLong<String>(
                706, "Bạn cần đăng nhập trước khi truy cập tài nguyên này.", null, null, null, null, null
        ));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ResponseLong<String>> accessDeniedException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseLong<String>(
                707, "Bạn không có quyền truy cập tài nguyên này.", null, null, null, null, null
        ));
    }
}
