package com.tp.backend.controller.user;

import com.tp.backend.dto.LoginRequestDto;
import com.tp.backend.dto.UserRequestDto;
import com.tp.backend.service.AuthService;
import com.tp.backend.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> signup(@RequestBody UserRequestDto userRequestDto){
        try{
            authService.signup(userRequestDto);
            return new ResponseEntity<>("User created successfully", HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
