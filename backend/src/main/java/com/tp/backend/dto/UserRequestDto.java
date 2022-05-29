package com.tp.backend.dto;

import com.tp.backend.annotation.enum_validation.EnumValidation;
import com.tp.backend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    private String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password can't be blank")
    private String password;
    @NotBlank(message = "Phone is required")
    private String phone;
    @EnumValidation(enumClass = UserType.class, message = "Invalid value for userType : must be 'User' or 'Admin'")
    private String userType;
    private String country;
    private String city;
    private MultipartFile imageFile;
    private String imageLink;
    private Boolean isActive;
}
