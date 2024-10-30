package org.example.expert.config;

import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.exception.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequestException(InvalidRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorCode = "ERR001";
        String errorMessage = "요청값의 형식이 맞지 않습니다.";
        return getErrorResponse(status, errorCode, errorMessage);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String errorCode = "ERR002";
        String errorMessage = "인증에 실패하였습니다. 다시 로그인 해주시기 바랍니다.";
        return getErrorResponse(status, errorCode, errorMessage);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Map<String, Object>> handleServerException(ServerException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorCode = "ERR003";
        String errorMessage = "네트워크 요청에 실패했습니다. 다시 시도해주시기 바랍니다.";
        return getErrorResponse(status, errorCode, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorCode = "ERR999";
        String errorMessage = "알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.";
        return getErrorResponse(status, errorCode, errorMessage);
    }

    private ResponseEntity<Map<String, Object>> getErrorResponse(HttpStatus status, String errorCode, String errorMessage) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.name());
        errorResponse.put("code", status.value());
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("errorMessage", errorMessage);

        return new ResponseEntity<>(errorResponse, status);
    }
}
