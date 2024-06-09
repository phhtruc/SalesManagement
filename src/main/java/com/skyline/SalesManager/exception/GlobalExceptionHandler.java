package com.skyline.SalesManager.exception;

import com.skyline.SalesManager.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handlerValidationException(Exception e, WebRequest request) {
        System.out.println("============== handlerValidationException ==============");
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setPart(request.getDescription(false).replace("uri=", ""));

        String message = e.getMessage();
        if(e instanceof MethodArgumentNotValidException){
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            message = message.substring(start + 1, end -1 );
            error.setError("Payload Invalid");
        }
        else if(e instanceof RuntimeException){
            error.setError("PathVariable Invalid");
        }
        error.setMessage(message);
        return error;
    }
}
