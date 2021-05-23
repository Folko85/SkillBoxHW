package com.skillbox.webshop.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, MethodArgumentNotValidException.class})
    // для начала сделал метод максимально простым
    public final ResponseEntity handleException(Exception ex, WebRequest request) { // хз, что сюда ещё добавлять и зачем
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;
        String message;
        if (ex instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        }  // для этого экзепшена нот фаунд
        else  {
            status = HttpStatus.BAD_REQUEST;
            message = "Field cannot being empty";
        }                            // для остальных бэд реквест
        return new ResponseEntity<>(message, headers, status);
    }

}
