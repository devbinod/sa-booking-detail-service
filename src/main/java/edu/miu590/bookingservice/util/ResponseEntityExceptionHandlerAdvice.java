package edu.miu590.bookingservice.util;

import edu.miu590.bookingservice.exception.BookingNotFoundException;
import edu.miu590.bookingservice.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseEntityExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BookingNotFoundException.class})
    public ResponseEntity<ErrorResponse> badRequestException(BookingNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }
}
