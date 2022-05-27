package com.tp.backend.controller.user;

import com.tp.backend.dto.UpdatePasswordRequestDto;
import com.tp.backend.dto.UserRequestDto;
import com.tp.backend.service.AuthService;
import com.tp.backend.service.UserService;
import com.tp.backend.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<?> getUsers(@RequestBody Map<String, Integer> queryData){
        try{
            int pageNo = queryData.get("pageNo");
            int pageSize = queryData.get("pageSize");
            return new ResponseEntity<>(userService.getUsers(pageNo, pageSize), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/:id")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto userRequestDto){
        try{
            return new ResponseEntity<>(userService.updateUser(userRequestDto), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDto updatePasswordRequestDto){
        try{
            return new ResponseEntity<>(userService.updatePassword(updatePasswordRequestDto), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //This api is used for updating user email
    @RequestMapping(method = RequestMethod.POST, value = "/email")
    public ResponseEntity<?> sendUpdateEmailVerificationToken(@RequestBody Map<String, Object> data){
        try{
            Long userId = (Long) data.get("userId");
            String email = (String) data.get("email");
            return new ResponseEntity<>(userService.sendUpdateEmailVerificationToken(userId, email), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/email/{id}")
    public ResponseEntity<?> verifyUpdateEmailVerificationToken(@PathVariable String token){
        try{
            return new ResponseEntity<>(userService.verifyUpdateEmailVerificationToken(token), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
