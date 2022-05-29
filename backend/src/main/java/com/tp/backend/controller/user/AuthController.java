package com.tp.backend.controller.user;

import com.tp.backend.dto.LoginRequestDto;
import com.tp.backend.dto.UserRequestDto;
import com.tp.backend.service.AuthService;
import com.tp.backend.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> signup(@ModelAttribute UserRequestDto userRequestDto){
        try{
            authService.signup(userRequestDto);
            return new ResponseEntity<>("User created successfully", HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DisabledException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resend")
    public ResponseEntity<?> resendActivationEmail(@RequestBody Map<String, String> emailData){
        try{
            authService.resendActivationEmail(emailData.get("email"));
            return new ResponseEntity<>("Verification email is resent successfully.", HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account-verification/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable String token){
        try{
            authService.verifyAccount(token);
            return new ResponseEntity<>("Account verified successfully.", HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
