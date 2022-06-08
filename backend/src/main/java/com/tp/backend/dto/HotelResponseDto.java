package com.tp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDto {
    private Long id;
    private String name;
    private String type;
    private String city;
    private String address;
    private String distance;
    private String title;
    private String description;
    private List<String> images;
    //private List<String> rooms;
    private Integer minPrice;
    private String rating;
    private Boolean isFeatured;
}
