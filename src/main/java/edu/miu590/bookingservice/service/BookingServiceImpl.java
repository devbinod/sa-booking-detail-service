package edu.miu590.bookingservice.service;

import edu.miu590.bookingservice.entity.BookingDetail;
import edu.miu590.bookingservice.exception.BookingNotFoundException;
import edu.miu590.bookingservice.mapper.BookingMapper;
import edu.miu590.bookingservice.model.BookingRequestDto;
import edu.miu590.bookingservice.model.BookingResponseDto;
import edu.miu590.bookingservice.repository.BookingRepository;
import edu.miu590.bookingservice.util.ApplicationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingMapper bookingMapper, BookingRepository bookingRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingResponseDto save(BookingRequestDto bookingRequestDto) {
        return convertToDto(createOrSave(null, bookingRequestDto));
    }

    @Override
    public BookingResponseDto update(String id, BookingRequestDto bookingRequestDto) {
        findByBookingId(id);
        return convertToDto(createOrSave(id, bookingRequestDto));
    }

    @Override
    public BookingResponseDto findByBookingId(String bookingId) {
        return convertToDto(getBookingDetailById(bookingId));
    }

    @Override
    public void delete(String bookingId) {
        bookingRepository.delete(getBookingDetailById(bookingId));
    }

    @Override
    public List<BookingResponseDto> findAll() {
        return bookingMapper.toDtoList(bookingRepository.findAll());
    }

    BookingDetail getBookingDetailById(String bookingId) {
        Optional<BookingDetail> optionalBookingDetail = bookingRepository.findById(bookingId);
        if (optionalBookingDetail.isEmpty())
            throw new BookingNotFoundException("Booking not found with id" + bookingId);
        return optionalBookingDetail.get();
    }


    private BookingResponseDto convertToDto(BookingDetail bookingDetail) {
        return bookingMapper.toDto(bookingDetail);
    }

    private BookingDetail createOrSave(String id, BookingRequestDto bookingRequestDto) {

        BookingDetail bookingDetail = bookingMapper.toEntity(bookingRequestDto);
        if (id == null) {
            bookingDetail.setUserId(ApplicationUtil.getCurrentUser());
        } else {
            bookingDetail.setBookingId(id);
        }
        return bookingRepository.save(bookingDetail);

    }
}
