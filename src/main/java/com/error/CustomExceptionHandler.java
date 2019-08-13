package com.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
@PropertySource("classpath:messages.properties")
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${validationFailed}")
    private String validationFailed;

    @Value("adaptationFailed")
    private String adaptationFailed;

    @Value("${foundRecordFailed}")
    private String foundRecordFailed;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorValidatingResponse error = new ErrorValidatingResponse(validationFailed , details, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException exception) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorRecordNotFoundResponse error = new ErrorRecordNotFoundResponse(foundRecordFailed, details, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExistException(Exception exception) {
        ErrorExistResponse error = new ErrorExistResponse(adaptationFailed, exception.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }
}
