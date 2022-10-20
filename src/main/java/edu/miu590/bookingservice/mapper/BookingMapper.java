package edu.miu590.bookingservice.mapper;


import edu.miu590.bookingservice.entity.BookingDetail;
import edu.miu590.bookingservice.model.BookingRequestDto;
import edu.miu590.bookingservice.model.BookingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {

    BookingResponseDto toDto(BookingDetail bookingDetail);

    BookingDetail toEntity(BookingRequestDto bookingResponseDto);

    List<BookingResponseDto> toDtoList(List<BookingDetail> bookingDetailList);

    List<BookingDetail> toEntityList(List<BookingRequestDto> bookingRequestDtoList);
}
