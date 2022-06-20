package com.tp.backend.service;

import com.tp.backend.dto.*;
import com.tp.backend.enums.TokenType;
import com.tp.backend.enums.UserType;
import com.tp.backend.mapper.UserMapper;
import com.tp.backend.model.NotificationEmail;
import com.tp.backend.model.User;
import com.tp.backend.model.VerificationToken;
import com.tp.backend.repository.UserRepository;
import com.tp.backend.repository.VerificationTokenRepository;
import com.tp.backend.security.JwtProvider;
import com.tp.backend.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MailContentBuilder mailContentBuilder;
    @Autowired
    private MailService mailService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Value("${app.server.baseurl}")
    private String serverBaseUrl;

    public void signup(UserRequestDto userRequestDto) {
        Optional<User> emailUser = userRepository.findByEmail(userRequestDto.getEmail());
        if (emailUser.isPresent()) {
            throw new CustomException("Email already exist.");
        }
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        String imageUrl = userRequestDto.getImageLink();
        UserType userType = null;
        if(userRequestDto.getUserType() == null){
           userType = UserType.User;
        } else {
            userType = UserType.valueOf(userRequestDto.getUserType());
        }
        User user = userMapper.mapToModel(userRequestDto, imageUrl, password, userType);
        user.setIsActive(false);
        user.setIsEmailVerified(false);
        user = userRepository.save(user);
        MultipartFile file = userRequestDto.getImageFile();
        if (file != null) {
            Long id = user.getId();
            String image = fileUploadService.getUploadedFileString(id.toString(), file);
            user.setImg(image);
            user = userRepository.save(user);
        }
        sendAccountActivationEmail(user);
    }

    public void resendActivationEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found."));
        if (user.getIsActive() && user.getIsEmailVerified()){
            throw new CustomException("User Account is already activated");
        } else {
            sendAccountActivationEmail(user);
        }
    }

    public void verifyAccount(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new CustomException("Invalid token"));
        if(isTokenExpired(verificationToken.getCreatedAt()) == false){
            throw new CustomException("Token is expired.");
        }
        fetchUserAndEnable(verificationToken);
    }

    public boolean isTokenExpired(LocalDateTime createdAt){
        //We will declare a token as expired if it was created before 72 hours
        return createdAt.plusHours(72).isAfter(LocalDateTime.now());
    }

    public void fetchUserAndEnable(VerificationToken verificationToken){
        Optional<User> tokenUser = userRepository.findById(verificationToken.getUser().getId());
        tokenUser.orElseThrow(() -> new CustomException("User not found. Please signup again."));
        User user = tokenUser.get();
        user.setIsActive(true);
        user.setIsEmailVerified(true);
        userRepository.save(user);
        verificationTokenRepository.deleteById(verificationToken.getId());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        User user = userRepository.findByEmail(loginRequestDto.getUsername()).orElseThrow(() ->
                new CustomException("User does not exist."));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                    loginRequestDto.getPassword()));
        } catch (DisabledException e) {
            if (user.getIsEmailVerified() == false) {
                throw new CustomException("Email is not verified yet. Please verify your email first to login.");
            } else {
                throw new DisabledException("Your account is disabled now.");
            }
        } catch (BadCredentialsException e) {
            // Since we have already checked if email exist or not above.
            // So throwing this error here simply means password is incorrect.
            throw new CustomException("Incorrect password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String jwtToken = jwtProvider.generateToken(userDetails);

        //If you are using manual implementation of Builder pattern
        /*
        LoginResponseDto response = new LoginResponseDto.LoginResponseBuilder()
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
        */
        //If you are using lombok @Builder annotation for builder pattern
        LoginResponseDto response = LoginResponseDto.builder()
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

        return response;
    }

    private void sendAccountActivationEmail(User user){
        String token = commonService.generateVerificationToken(user, TokenType.AccountActivation);
        String url = serverBaseUrl + "/auth/account-verification/"+token;
        String btnName = "Activate";
        String text = "Thanks for signing up in Booking App. Please click on the button below to activate your account.";
        String msg = mailContentBuilder.build(text, url, btnName);

        String subject = "Please Activate your account.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }
}
