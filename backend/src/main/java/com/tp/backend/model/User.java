package com.tp.backend.model;

import com.tp.backend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userdata")
//In postgresql we can't keep table name as "user" so we had to rename table as "userdata".
public class User extends DatabaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "City is required")
    private String city;

    private String img;

    @NotBlank(message = "Phone is required")
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Enumerated(EnumType.STRING)
    @Column(name= "usertype")
    private UserType userType;
}
