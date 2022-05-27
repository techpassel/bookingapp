package com.tp.backend.controller.admin;

import com.tp.backend.dto.RoomDto;
import com.tp.backend.exception.CustomException;
import com.tp.backend.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/room")
@AllArgsConstructor
public class AdminRoomController {
    private final RoomService roomService;
    @RequestMapping(method = RequestMethod.POST, value = "/")
    private ResponseEntity<?> createRoom(@RequestBody RoomDto roomRequestDto){
        try{
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
