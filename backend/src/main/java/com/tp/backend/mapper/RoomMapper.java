package com.tp.backend.mapper;

import com.tp.backend.dto.RoomDto;
import com.tp.backend.dto.RoomNumberDto;
import com.tp.backend.model.Hotel;
import com.tp.backend.model.Room;
import com.tp.backend.model.RoomNumber;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class RoomMapper {
    @Autowired
    private RoomNumberMapper roomNumberMapper;

    @Mapping(target = "id", source = "roomDto.id")
    @Mapping(target = "title", source = "roomDto.title")
    @Mapping(target = "description", source = "roomDto.description")
    @Mapping(target = "hotel", source = "hotel")
    @Mapping(target = "roomNumbers", source = "roomNumbers")
    public abstract Room mapToModel(RoomDto roomDto, Hotel hotel, List<RoomNumber> roomNumbers);

    @Mapping(target = "hotelId", expression = "java(getHotelId(room))")
    @Mapping(target = "roomNumbers", expression = "java(getRoomNumbers(room))")
    public abstract RoomDto mapToDto(Room room);

    public Long getHotelId(Room room){
        return room.getHotel().getId();
    }

    public List<RoomNumberDto> getRoomNumbers(Room room) {
        List<RoomNumber> roomNumbers = room.getRoomNumbers();
        return roomNumbers.stream().map(roomNumberMapper::mapToDto).collect(Collectors.toList());
    }
}
