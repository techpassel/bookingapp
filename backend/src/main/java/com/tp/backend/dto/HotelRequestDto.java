package com.tp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDto {
    private Long id;
    private String name;
    private String type;
    private String city;
    private String address;
    private String distance;
    private String title;
    private String description;
    private List<MultipartFile> imagesFiles;
    private List<String> imagesLinks;
    // Here We are not creating fields for "rooms". We will implement functionality to add rooms in RoomService and
    // will use RoomDto for that purpose;.
    private Integer minPrice;
    private Byte rating;
    private Boolean isFeatured;
}
