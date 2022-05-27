package com.tp.backend.mapper;

import com.tp.backend.dto.RoomNumberDto;
import com.tp.backend.model.Room;
import com.tp.backend.model.RoomNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class RoomNumberMapper {
    @Mapping(target = "id", source = "roomNumberDto.id")
    @Mapping(target = "room", source = "room")
    public abstract RoomNumber mapToModel(RoomNumberDto roomNumberDto, Room room);

    @Mapping(target = "roomId", expression = "java(getRoomId(roomNumber))")
    public abstract RoomNumberDto mapToDto(RoomNumber roomNumber);

    public Long getRoomId(RoomNumber roomNumber){
        return roomNumber.getRoom().getId();
    }
}
