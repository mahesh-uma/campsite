package com.upgrade.campsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class CampsiteControllerAdvice {

    @ExceptionHandler(BookingDetailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleNonExisting(BookingDetailNotFoundException e) {
        return new ResponseEntity<>(new Error("NOT FOUND",e.getMessage(), HttpStatus.NOT_FOUND.value(),LocalDate.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservedDateNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleNotAvailbe(ReservedDateNotAvailableException e) {
        return new ResponseEntity<>(new Error("NOT AVAILABLE",e.getMessage(), HttpStatus.NOT_FOUND.value(),LocalDate.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> general(Exception e) {
        return new ResponseEntity<>(new Error("NOT AVAILABLE",e.getMessage(), HttpStatus.BAD_REQUEST.value(),LocalDate.now()), HttpStatus.BAD_REQUEST);
    }

    private static final class Error {
        private final String error;
        private final String message;
        private final Integer status;
        private final LocalDate localDate;

        public Error(String error, String message, Integer status, LocalDate localDate) {
            this.error = error;
            this.message = message;
            this.status = status;
            this.localDate = localDate;
        }
    }

}
