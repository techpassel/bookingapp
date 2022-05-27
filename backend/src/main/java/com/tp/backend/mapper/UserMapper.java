package com.tp.backend.mapper;

import com.tp.backend.dto.UserRequestDto;
import com.tp.backend.dto.UserResponseDto;
import com.tp.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "id", source = "userRequestDto.id")
    @Mapping(target = "img", source = "img")
    @Mapping(target = "password", source = "password")
    public abstract User mapToModel(UserRequestDto userRequestDto, String img, String password);

    public abstract UserResponseDto mapToDto(User user);
}
