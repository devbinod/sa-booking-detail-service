package edu.miu590.bookingservice.repository;

import edu.miu590.bookingservice.entity.BookingDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingDetail,String> {


    @Query("select b.vehicleId from BookingDetail b where b.pickupDate <= :pickupDate and  b.returnDate >= :returnDate ")
//    SELECT * FROM booking_detail WHERE pickup_date <= '2022-10-24' and return_date >= '2022-10-25'

    List<String> findAllBookingBetweenPickUpAndReturnDate(@Param("pickupDate") LocalDate pickupDate, @Param("returnDate") LocalDate returnDate);

}
