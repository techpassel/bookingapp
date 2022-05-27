package com.tp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private Long hotelId;
    private String title;
    private Float price;
    private Integer maxPeople;
    private String description;
    private List<RoomNumberDto> roomNumbers;
}
