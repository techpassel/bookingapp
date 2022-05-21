package com.tp.backend.dto;

import com.tp.backend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String country;
    private String city;
    private MultipartFile imageFile;
    private String imageLink;
    private String phone;
    private Boolean isActive;
    private UserType userType;
}
