package com.tp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomNumberDto {
    private Long id;
    private Long roomId;
    private Integer roomNumber;
    private List<LocalDate> unavailableDates;
}
