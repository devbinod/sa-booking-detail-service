package edu.miu590.bookingservice.service;

import edu.miu590.bookingservice.model.BookingRequestDto;
import edu.miu590.bookingservice.model.BookingResponseDto;
import edu.miu590.bookingservice.model.BookingUpdateRequestDto;
import edu.miu590.bookingservice.model.SearchBookingDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    BookingResponseDto save(BookingRequestDto bookingRequestDto);

    BookingResponseDto update(String id, BookingRequestDto bookingRequestDto);

    BookingResponseDto findByBookingId(String bookingId);

    void delete(String bookingId);

    List<BookingResponseDto> findAll();

    List<String> filterByPickupDateAndReturnDate(SearchBookingDto searchBookingDto);

    BookingResponseDto updateBookingStatus(BookingUpdateRequestDto bookingUpdateRequestDto);

}
