package com.upgrade.campsite.dto.mapper;

import com.upgrade.campsite.dto.BookingDetailDTO;
import com.upgrade.campsite.model.BookingDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingDetailMapper {
    BookingDetailMapper INSTANCE = Mappers.getMapper(BookingDetailMapper.class);

    BookingDetail toBooking(BookingDetailDTO bookingDetailDTO);
}
