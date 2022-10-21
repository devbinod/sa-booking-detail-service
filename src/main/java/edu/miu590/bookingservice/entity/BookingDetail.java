package edu.miu590.bookingservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.miu590.bookingservice.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingDetail {

    @Id
    private String bookingId;
    private String vehicleId;
    private LocalDate pickupDate;
    private LocalDate returnDate;
    private String userId;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @Column(updatable = false)
    private LocalDate createdDate;

    @PrePersist
    public void prePersist() {
        if (this.createdDate == null) this.createdDate = LocalDate.now();
        if (this.bookingId == null) this.bookingId = UUID.randomUUID().toString();
        if(this.bookingStatus == null) this.bookingStatus = BookingStatus.PENDING;
    }
}
