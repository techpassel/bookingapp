package com.tp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelSearchQueryDto {
    private Integer pageNo;
    private Integer pageSize;
    private Integer minPrice;
    private Integer maxPrice;
    private String hotelName;
    private String city;
}
