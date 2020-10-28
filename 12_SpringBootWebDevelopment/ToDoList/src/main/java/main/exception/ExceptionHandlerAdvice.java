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
        HttpStatus status;
        if(ex instanceof EntityNotFoundException) status = HttpStatus.NOT_FOUND;  // для этого экзепшена нот фаунд
        else status = HttpStatus.BAD_REQUEST;                            // для остальных бэд реквест
        return new ResponseEntity<>(ex.getMessage(), headers, status);
    }

}
