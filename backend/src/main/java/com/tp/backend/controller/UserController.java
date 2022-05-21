package com.tp.backend.controller;

import com.tp.backend.dto.UpdatePasswordRequestDto;
import com.tp.backend.dto.UserRequestDto;
import com.tp.backend.service.AuthService;
import exception.BackendException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final AuthService authService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto userRequestDto){
        try{
            //We had created createOrUpdateUser() method in such a way that it can work for both, signup() method
            //of AuthController and updateUser() method of UserController.
            return new ResponseEntity<>(authService.createOrUpdateUser(userRequestDto), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDto updatePasswordRequestDto){
        try{
            return new ResponseEntity<>(authService.updatePassword(updatePasswordRequestDto), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
