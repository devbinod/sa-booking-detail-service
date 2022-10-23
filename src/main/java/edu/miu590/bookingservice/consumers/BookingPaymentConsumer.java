package edu.miu590.bookingservice.consumers;

import edu.miu590.bookingservice.model.BookingUpdateRequestDto;
import edu.miu590.bookingservice.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingPaymentConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingPaymentConsumer.class);

    private BookingService bookingService;

    public BookingPaymentConsumer(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = "${booking.payment-info.service.kafka.topic}",groupId = "${booking.service.kafka.group}")
    public void consume(@Payload BookingUpdateRequestDto bookingUpdateRequestDto) {
        LOGGER.info("message"+bookingUpdateRequestDto);
        bookingService.updateBookingStatus(bookingUpdateRequestDto);
    }
}
