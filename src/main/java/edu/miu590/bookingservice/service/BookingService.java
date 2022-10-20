package edu.miu590.bookingservice.service;

import edu.miu590.bookingservice.model.BookingRequestDto;
import edu.miu590.bookingservice.model.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto save(BookingRequestDto bookingRequestDto);

    BookingResponseDto update(String id, BookingRequestDto bookingRequestDto);

    BookingResponseDto findByBookingId(String bookingId);

    void delete(String bookingId);

    List<BookingResponseDto> findAll();

}
