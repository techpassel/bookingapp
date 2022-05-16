package com.tp.backend.controller;

import com.tp.backend.dto.HotelSearchQueryDto;
import com.tp.backend.service.HotelService;
import exception.BackendException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@AllArgsConstructor
public class HotelController {
    HotelService hotelService;

    @RequestMapping(method = RequestMethod.GET, value = "/:id")
    public ResponseEntity<?> getHotel(@PathVariable Long id){
        try{
            return new ResponseEntity<>(hotelService.getHotelById(id), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<?> searchHotel(@RequestBody HotelSearchQueryDto queryDate){
        try{
            return new ResponseEntity<>(hotelService.searchHotel(queryDate), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/city/count/:cityName")
    public ResponseEntity<?> countHotelsByCity(@PathVariable String cityName){
        try{
            return new ResponseEntity<>(hotelService.countHotelsByCity(cityName), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
