package com.tp.backend.controller.admin;

import com.tp.backend.dto.HotelRequestDto;
import com.tp.backend.service.HotelService;
import com.tp.backend.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/hotel")
@AllArgsConstructor
public class AdminHotelController {
    private final HotelService hotelService;

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createHotel(@ModelAttribute HotelRequestDto data){
        //This api will work for create hotel as well as update hotel
        try{
            return new ResponseEntity<>(hotelService.createHotel(data), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/:id")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id){
        try{
            return new ResponseEntity<>(hotelService.deleteHotel(id), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
