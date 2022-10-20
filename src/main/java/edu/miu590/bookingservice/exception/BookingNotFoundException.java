package edu.miu590.bookingservice.exception;

public class BookingNotFoundException extends RuntimeException{


    public BookingNotFoundException(String message) {
        super(message);
    }
}
