package edu.miu590.bookingservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfigTopic {

    @Value("${booking.notification-info.service.kafka.topic}")
    private String bookingTopic;


    @Bean
    public NewTopic createBookingTopic(){
        return TopicBuilder.name(bookingTopic)
                .partitions(10)
                .build();
    }
}
