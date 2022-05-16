package com.tp.backend.mapper;

import com.tp.backend.dto.HotelRequestDto;
import com.tp.backend.dto.HotelResponseDto;
import com.tp.backend.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class HotelMapper {
    @Mapping(target = "id", source = "hotelRequestDto.id")
    @Mapping(target = "images", source = "images")
    public abstract Hotel mapToModel(HotelRequestDto hotelRequestDto, List<String> images);

    public abstract HotelResponseDto mapToDto(Hotel hotel);
}
