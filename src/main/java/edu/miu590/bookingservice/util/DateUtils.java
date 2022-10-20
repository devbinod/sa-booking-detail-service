package edu.miu590.bookingservice.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {

   public static BigDecimal getNoOfDaysBetweenPickupAndReturn(LocalDate pickupDate, LocalDate returnDate){
        return new BigDecimal(ChronoUnit.DAYS.between(pickupDate,returnDate));
    }
}
