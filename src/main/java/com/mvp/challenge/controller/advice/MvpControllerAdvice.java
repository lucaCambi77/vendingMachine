package com.mvp.challenge.controller.advice;

import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.NotEnoughDepositException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.exception.UserNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class MvpControllerAdvice {

    @ExceptionHandler({CoinInputException.class, NotEnoughDepositException.class, ProductAlreadyExistsException.class
            , ProductNotExistsException.class, ProductTemporarilyNotAvailable.class, UserCredentialException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    ResponseEntity<ErrorResponse> badRequestResponse(Exception ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .status(httpStatus.value())
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler({UserNotAuthorizedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public
    @ResponseBody
    ResponseEntity<ErrorResponse> unauthorizedRequestResponse(Exception ex) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .status(httpStatus.value())
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
