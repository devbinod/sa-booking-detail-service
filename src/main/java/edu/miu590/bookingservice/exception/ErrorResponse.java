package edu.miu590.bookingservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {


    private int code;
    private String status;
    private String message;
    private String details;

    public ErrorResponse(HttpStatus httpStatus, String message, String description) {
        this.code=httpStatus.value();
        this.status= httpStatus.toString();
        this.message=message;
        this.details=description;
    }
}
