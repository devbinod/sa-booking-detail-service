package edu.miu590.bookingservice.service;

import edu.miu590.bookingservice.client.VehicleClient;
import edu.miu590.bookingservice.entity.BookingDetail;
import edu.miu590.bookingservice.exception.BookingNotFoundException;
import edu.miu590.bookingservice.mapper.BookingMapper;
import edu.miu590.bookingservice.model.BookingRequestDto;
import edu.miu590.bookingservice.model.BookingResponseDto;
import edu.miu590.bookingservice.model.VehicleDto;
import edu.miu590.bookingservice.repository.BookingRepository;
import edu.miu590.bookingservice.util.ApplicationUtil;

import edu.miu590.bookingservice.util.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final VehicleClient vehicleClient;

    public BookingServiceImpl(BookingMapper bookingMapper, BookingRepository bookingRepository, VehicleClient vehicleClient) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.vehicleClient = vehicleClient;
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

        VehicleDto vehicleDto = vehicleClient.findById(bookingRequestDto.getVehicleId());

        BookingDetail bookingDetail = bookingMapper.toEntity(bookingRequestDto);
        if (id == null) {
            bookingDetail.setUserId(ApplicationUtil.getCurrentUser());
        } else {
            bookingDetail.setBookingId(id);
        }

        bookingDetail.setTotalPrice(
                vehicleDto.getPricePerDay().multiply(DateUtils.getNoOfDaysBetweenPickupAndReturn(bookingRequestDto.getPickupDate(), bookingDetail.getReturnDate()))
        );
        return bookingRepository.save(bookingDetail);

    }


}
