package edu.miu590.bookingservice.producers;

import edu.miu590.bookingservice.model.NotificationEmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    private KafkaTemplate<Object,Object> kafkaTemplate;



    @Value("${booking.notification-info.service.kafka.topic}")
    private String bookingTopic;

    public NotificationProducer(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendNotification(NotificationEmailDto notificationEmailDto){
        kafkaTemplate.send(bookingTopic,notificationEmailDto);

    }
}
