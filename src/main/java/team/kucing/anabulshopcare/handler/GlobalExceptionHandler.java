package team.kucing.anabulshopcare.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import team.kucing.anabulshopcare.entity.ErrorObject;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException e, WebRequest request){
        ErrorObject errObj = new ErrorObject();

        errObj.setStatusCode(HttpStatus.NOT_FOUND.value());
        errObj.setErrorMessage(Collections.singletonList(e.getMessage()));
        errObj.setTimestamp(new Date());

        return new ResponseEntity<>(errObj, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException e, WebRequest request){
        ErrorObject errObj = new ErrorObject();

        errObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errObj.setErrorMessage(Collections.singletonList(e.getMessage()));
        errObj.setTimestamp(new Date());

        return new ResponseEntity<>(errObj, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleGeneralException(Exception e, WebRequest request){
        ErrorObject errObj = new ErrorObject();

        errObj.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errObj.setErrorMessage(Collections.singletonList(e.getMessage()));
        errObj.setTimestamp(new Date());

        return new ResponseEntity<>(errObj, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request){
        List<String> errors = e.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());

        ErrorObject errObj = new ErrorObject();

        errObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errObj.setErrorMessage(errors);
        errObj.setTimestamp(new Date());

        return new ResponseEntity<>(errObj, HttpStatus.BAD_REQUEST);
    }
}