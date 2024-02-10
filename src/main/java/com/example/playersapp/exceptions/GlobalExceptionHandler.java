/*
 * @author Eli Aviv
 */
package com.example.playersapp.exceptions;

import com.example.playersapp.communication.responses.RestResponse;
import com.example.playersapp.communication.responses.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: Infinite time - Support various of dedicated exceptions and their handling

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<? extends RestResponse> handleRuntimeException(RuntimeException exception) {
        final var result = new Result("ERROR", exception.getMessage());

        final RestResponse restResponse = new RestResponse();
        restResponse.setResult(result);

        return new ResponseEntity<>(restResponse ,HttpStatus.BAD_REQUEST);
    }
}