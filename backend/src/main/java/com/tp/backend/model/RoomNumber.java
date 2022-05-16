package com.tp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "room_number")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomNumber extends DatabaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull(message = "Room number is required")
    @Column(name = "room_number")
    private Integer roomNumber;

    @ElementCollection(targetClass = LocalDate.class)
    @CollectionTable
    @Column(name = "unavailable_dates")
    private List<LocalDate> unavailableDates;
}
