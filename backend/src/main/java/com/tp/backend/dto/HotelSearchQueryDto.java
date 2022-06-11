package com.tp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelSearchQueryDto {
    private Integer pageNo;     //0 for 1st page, 1 for 2nd page and so on.
    private Integer pageSize;
    private Integer minPrice;
    private Integer maxPrice;
    private String hotelName;
    private String city;
}
