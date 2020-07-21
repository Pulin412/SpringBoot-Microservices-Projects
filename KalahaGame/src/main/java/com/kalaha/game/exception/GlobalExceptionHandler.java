/**
 * 
 */
package com.kalaha.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kalaha.game.model.ErrorResponse;

/**
 * @author Pulin
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(GameNotFoundException exception) {
    	ErrorResponse response = new ErrorResponse();
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setError(exception.getMessage());
        response.setId(exception.getId());
        response.setUrl(exception.getUrl());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidMoveException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(InvalidMoveException exception) {
    	ErrorResponse response = new ErrorResponse();

        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setError(exception.getMessage());
        response.setId(exception.getId());
        response.setStatus(exception.getStatus());
        response.setUrl(exception.getUrl());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
