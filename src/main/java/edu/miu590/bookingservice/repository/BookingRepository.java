package edu.miu590.bookingservice.repository;

import edu.miu590.bookingservice.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingDetail,String> {
}
