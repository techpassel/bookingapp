package com.tp.backend.model;

import com.tp.backend.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Type is required")
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Distance is required")
    private String distance;

    @NotBlank(message = "Title is required")
    private String title;

    @Lob    //For LongText type data
    @NotBlank(message = "Description is required")
    private String description;

    @ElementCollection(targetClass = String.class)
    @CollectionTable
    private List<String> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Room> rooms;

    @Column(name = "min_price")
    private Integer minPrice;

    /*
    @Min(0)
    @Max(5)
    private Byte rating;
    Above definition of rating field will allow us to store only integer values in range of 0-5.
    But we want to store decimal value also of up to 1 decimal places i.e. we want to allow values
    like "3.7", "4.1" etc. also as valid input for this field. We can achieve that as follows.
     */
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    @Digits(integer = 1, fraction = 1)
    private BigDecimal rating;

    @Column(name = "is_featured")
    private Boolean isFeatured;
}
