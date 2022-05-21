package com.tp.backend.service;

import com.tp.backend.dto.*;
import com.tp.backend.mapper.UserMapper;
import com.tp.backend.model.User;
import com.tp.backend.repository.UserRepository;
import com.tp.backend.security.JwtProvider;
import exception.BackendException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;

    public UserResponseDto createOrUpdateUser(UserRequestDto userRequestDto) {
        //This api will be used for creating user as well updating user data. So we have called this
        //function from AuthController "signup" method as well as UserController "updateUser" method.
        Long id = userRequestDto.getId();
        User existingUser = null;
        if (id != null) {
            existingUser = userRepository.findById(id).orElseThrow(() -> new
                    BackendException("User with given id doesn't exist."));
        }
        Optional<User> emailUser = userRepository.findByEmail(userRequestDto.getEmail());
        if ((id == null && emailUser.isPresent()) ||
                (id != null && emailUser.isPresent() && emailUser.get().getId() != id)) {
            throw new BackendException("Email already exist.");
        }
        //We will not use this API to update password. So updating password only if existing user is null;
        String password = null;
        if(existingUser == null) {
            password = passwordEncoder.encode(userRequestDto.getPassword());
        } else {
            password = existingUser.getPassword();
        }
        String imageUrl = userRequestDto.getImageLink();
        User user = userMapper.mapToModel(userRequestDto, imageUrl, password);
        user = userRepository.save(user);
        MultipartFile file = userRequestDto.getImageFile();
        if (file != null) {
            id = user.getId();
            String image = fileUploadService.getUploadedFileString(id.toString(), file);
            user.setImg(image);
            user = userRepository.save(user);
        }
        return userMapper.mapToDto(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
                loginRequestDto.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        final String jwtToken = jwtProvider.generateToken(userDetails);
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() ->
                new BackendException("User not found."));
        return new LoginResponseDto.LoginResponseBuilder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .country(user.getCountry())
                .city(user.getCity())
                .img(user.getImg())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .token(jwtToken)
                .build();
    }

    public String updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto){
        User user = userRepository.findById(updatePasswordRequestDto.getId()).orElseThrow(() ->
                new BackendException("User with given id not found."));
        boolean passwordMatches = passwordEncoder.matches(updatePasswordRequestDto.getOldPassword(),
                user.getPassword());
        if(!passwordMatches){
            throw new BackendException("Invalid password.");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordRequestDto.getNewPassword()));
        userRepository.save(user);
        return "Password updated successfully";
    }
}
