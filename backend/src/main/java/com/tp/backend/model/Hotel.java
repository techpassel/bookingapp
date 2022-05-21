package com.tp.backend.model;

import com.tp.backend.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends DatabaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Type is required")
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

    @Min(0)
    @Max(5)
    private Byte rating;

    @Column(name = "is_featured")
    private Boolean isFeatured;
}
