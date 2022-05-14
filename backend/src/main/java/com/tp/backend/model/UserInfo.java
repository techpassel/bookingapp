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
public class UserInfo extends DatabaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    private String password;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    private boolean enabled;

    @Column(name= "usertype")
    private UserType userType;
}
