package com.tp.backend.controller.user;

import com.tp.backend.dto.HotelSearchQueryDto;
import com.tp.backend.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@AllArgsConstructor
public class HotelController {
    HotelService hotelService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getHotel(@PathVariable Long id){
        return new ResponseEntity<>(hotelService.getHotelById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<?> searchHotel(@RequestBody HotelSearchQueryDto queryDate){
        return new ResponseEntity<>(hotelService.searchHotel(queryDate), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count-by-city")
    public ResponseEntity<?> countByCity(@RequestParam(name = "cities", required = true) String cityNames){
        /*
        If we had created variable name as "cities" then adding (name = "cities") were not necessary.
        And default value for "required" is true so adding it here is also not necessary.I just wanted to show how
        to make param fields required and optional. However, in Java 8 or later we can use Optional<String> in place
        of String and this will work in the same manner as required = false. Same can be applied with @PathVariable
        as well.
        */
        return new ResponseEntity<>(hotelService.countByCity(cityNames), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count-by-type")
    public ResponseEntity<?> countByType(){
        return new ResponseEntity<>(hotelService.countByType(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/featured/{limit}")
    public ResponseEntity<?> getFeaturedProperties(@PathVariable(name = "limit") int responseSize){
        return new ResponseEntity<>(hotelService.getFeaturedProperties(responseSize), HttpStatus.OK);
    }
}
