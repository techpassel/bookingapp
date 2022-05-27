package com.tp.backend.service;

import com.tp.backend.dto.RoomDto;
import com.tp.backend.dto.RoomNumberDto;
import com.tp.backend.exception.CustomException;
import com.tp.backend.mapper.RoomMapper;
import com.tp.backend.mapper.RoomNumberMapper;
import com.tp.backend.model.Hotel;
import com.tp.backend.model.Room;
import com.tp.backend.model.RoomNumber;
import com.tp.backend.repository.HotelRepository;
import com.tp.backend.repository.RoomNumberRepository;
import com.tp.backend.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RoomService {
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;
    private final RoomNumberMapper roomNumberMapper;
    private final RoomRepository roomRepository;
    private final RoomNumberRepository roomNumberRepository;

    public RoomDto createRoom(RoomDto roomDto){
        Long hotelId = roomDto.getHotelId();
        List<RoomNumberDto> roomNumbersData = roomDto.getRoomNumbers();
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new CustomException("Hotel not found."));
        List<RoomNumber> roomNumbers = new ArrayList<>();
        Room room = roomMapper.mapToModel(roomDto, hotel, roomNumbers);
        room = roomRepository.save(room);
        //Since variables used in Lambda expression should eb final or effectively final so creating a copy of room.
        Room finalRoom = room;
        roomNumbers = roomNumbersData.stream().map(e -> {
            return roomNumberRepository.save(roomNumberMapper.mapToModel(e, finalRoom));
        }).collect(Collectors.toList());
        room.setRoomNumbers(roomNumbers);
        return roomMapper.mapToDto(roomRepository.save(room));
    }

    public String deleteRoom(Long id){
        try {
            roomRepository.deleteById(id);
            return "Room has been deleted.";
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException("Room with given id doesn't exist.", e);
        }
    }

    public RoomNumberDto updateRoomAvailability(RoomNumberDto roomNumberDto){
        Long id = roomNumberDto.getId();
        Long roomId = roomNumberDto.getRoomId();
        RoomNumber roomNumber = roomNumberRepository.findById(id).orElseThrow(() ->
                new CustomException("Room number not found"));
        roomNumber.setUnavailableDates(roomNumberDto.getUnavailableDates());
        return roomNumberMapper.mapToDto(roomNumberRepository.save(roomNumber));
    }

    public RoomDto getRoomById(Long id){
        return roomMapper.mapToDto(roomRepository.findById(id).orElseThrow(() ->
                new CustomException("Room not found.")));
    }

    public List<RoomDto> getRooms(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt"));
        return roomRepository.findAll(pageable).stream().map(e -> roomMapper.mapToDto(e)).collect
                (Collectors.toList());
    }
}
