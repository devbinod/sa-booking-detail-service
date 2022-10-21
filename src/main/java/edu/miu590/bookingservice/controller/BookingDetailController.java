package edu.miu590.bookingservice.controller;

import edu.miu590.bookingservice.api.BookingsApi;
import edu.miu590.bookingservice.model.BookingRequestDto;
import edu.miu590.bookingservice.model.BookingResponseDto;

import edu.miu590.bookingservice.model.SearchBookingDto;
import edu.miu590.bookingservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingDetailController implements BookingsApi {

    private final BookingService bookingService;

    public BookingDetailController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public ResponseEntity<Void> deleteBookingDetailById(String id) {
        bookingService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<BookingResponseDto>> getAllBookingDetail() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @Override
    public ResponseEntity<BookingResponseDto> getBookingDetailById(String id) {
        return ResponseEntity.ok(bookingService.findByBookingId(id));
    }

    @Override
    public ResponseEntity<BookingResponseDto> saveBooking(BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.save(bookingRequestDto));
    }

    @Override
    public ResponseEntity<BookingResponseDto> updateBookingDetailById(String id, BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.update(id, bookingRequestDto));
    }

    @Override
    public ResponseEntity<List<String>> filterBooking(SearchBookingDto searchBookingDto) {
        return ResponseEntity.ok(bookingService.filterByPickupDateAndReturnDate(searchBookingDto));
    }


}
