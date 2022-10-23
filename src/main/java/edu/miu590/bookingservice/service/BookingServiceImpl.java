package edu.miu590.bookingservice.service;

import edu.miu590.bookingservice.client.VehicleServiceClient;
import edu.miu590.bookingservice.entity.BookingDetail;
import edu.miu590.bookingservice.exception.BookingNotFoundException;
import edu.miu590.bookingservice.mapper.BookingMapper;
import edu.miu590.bookingservice.model.*;
import edu.miu590.bookingservice.producers.NotificationProducer;
import edu.miu590.bookingservice.repository.BookingRepository;
import edu.miu590.bookingservice.util.ApplicationUtil;

import edu.miu590.bookingservice.util.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final VehicleServiceClient vehicleClient;
    private final NotificationProducer notificationProducer;

    public BookingServiceImpl(BookingMapper bookingMapper, BookingRepository bookingRepository, VehicleServiceClient vehicleClient, NotificationProducer notificationProducer) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.vehicleClient = vehicleClient;
        this.notificationProducer = notificationProducer;
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

    @Override
    public List<String> filterByPickupDateAndReturnDate(SearchBookingDto searchBookingDto) {

        return bookingRepository.findAllBookingBetweenPickUpAndReturnDate(searchBookingDto.getPickupDate(),
                searchBookingDto.getReturnDate());

    }


    @Override
    public BookingResponseDto updateBookingStatus(BookingUpdateRequestDto bookingUpdateRequestDto) {
        BookingDetail bookingDetail = getBookingDetailById(bookingUpdateRequestDto.getBookingId());
        bookingDetail.setBookingStatus(bookingUpdateRequestDto.getBookingStatus());
        BookingResponseDto bookingResponseDto = convertToDto(bookingRepository.save(bookingDetail));


        notificationProducer.sendNotification(
                NotificationEmailDto.builder()
                        .bookingStatus(bookingUpdateRequestDto.getBookingStatus())
                        .email(bookingUpdateRequestDto.getEmail())
                        .totalPrice(bookingResponseDto.getTotalPrice())
                        .build()
        );

        return bookingResponseDto;

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
