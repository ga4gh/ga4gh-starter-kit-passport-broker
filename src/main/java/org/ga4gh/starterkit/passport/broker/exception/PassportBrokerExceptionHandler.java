package org.ga4gh.starterkit.passport.broker.exception;

import org.ga4gh.starterkit.common.constant.DateTimeConstants;
import org.ga4gh.starterkit.common.exception.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class PassportBrokerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        HttpStatus status = ex.getClass().getAnnotation(ResponseStatus.class).value();
        return handleException(ex, null, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleException(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleException(Exception ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String reason = (status instanceof HttpStatus httpStatus)
                ? httpStatus.getReasonPhrase()
                : "Unknown Status " + status.value();
        PassportBrokerCustomExceptionResponse response = new PassportBrokerCustomExceptionResponse();
        response.setStatusCode(status.value());
        response.setError(reason);
        response.setTimestamp(LocalDateTime.now().format(DateTimeConstants.DATE_FORMATTER));
        response.setMessage(ex.getMessage());
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        
        return ResponseEntity.status(status).headers(headers).body(response);
    }
}
