package main.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, EmptyFieldException.class})  // для начала сделал метод максимально простым
    public final ResponseEntity handleException(Exception ex,  WebRequest request) { // хз, что сюда ещё добавлять и зачем
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ex.getMessage(), headers, status);
    }

}
