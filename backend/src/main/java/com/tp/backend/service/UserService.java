package com.tp.backend.service;

import com.tp.backend.dto.UpdatePasswordRequestDto;
import com.tp.backend.dto.UserRequestDto;
import com.tp.backend.dto.UserResponseDto;
import com.tp.backend.enums.TokenType;
import com.tp.backend.mapper.UserMapper;
import com.tp.backend.model.NotificationEmail;
import com.tp.backend.model.User;
import com.tp.backend.model.VerificationToken;
import com.tp.backend.repository.UserRepository;
import com.tp.backend.repository.VerificationTokenRepository;
import com.tp.backend.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Value("${app.server.baseurl}")
    private String serverBaseUrl;

    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        Long id = userRequestDto.getId();
        User existingUser = userRepository.findById(id).orElseThrow(() -> new
                CustomException("User with given id doesn't exist."));
        //We will not use this API to update password or email.
        if ((userRequestDto.getPassword() != null && !userRequestDto.getPassword().equals("")) ||
                (userRequestDto.getEmail() != null && !userRequestDto.getEmail().equals(""))) {
            throw new CustomException("Updating email and password is not allowed using this api.");
        }
        userRequestDto.setEmail(existingUser.getEmail());
        MultipartFile file = userRequestDto.getImageFile();
        String newImageUrl = null;
        if (file != null) {
            newImageUrl = fileUploadService.getUploadedFileString(id.toString(), file);
        }
        String imageUrl = newImageUrl != null ? newImageUrl :
                (userRequestDto.getImageLink() != null && !userRequestDto.getImageLink().equals("")) ?
                        userRequestDto.getImageLink() : existingUser.getImg();
        User user = userMapper.mapToModel(userRequestDto, imageUrl, existingUser.getPassword());
        user = userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    public String updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto) {
        User user = userRepository.findById(updatePasswordRequestDto.getId()).orElseThrow(() ->
                new CustomException("User with given id not found."));
        boolean passwordMatches = passwordEncoder.matches(updatePasswordRequestDto.getOldPassword(),
                user.getPassword());
        if (!passwordMatches) {
            throw new CustomException("Invalid password.");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordRequestDto.getNewPassword()));
        userRepository.save(user);
        return "Password updated successfully";
    }

    public String sendUpdateEmailVerificationToken(Long userId, String email){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException
                ("User with given id not found."));
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByUserAndTokenType(user,
                TokenType.EmailUpdateVerification);
        if(verificationToken.isPresent()){
            VerificationToken verToken = verificationToken.get();
            verificationTokenRepository.deleteById(verToken.getId());
        }
        String token = commonService.generateVerificationToken(user, TokenType.AccountActivation);
        VerificationToken newVerificationToken = new VerificationToken();
        newVerificationToken.setToken(token);
        newVerificationToken.setTokenType(TokenType.EmailUpdateVerification);
        newVerificationToken.setUpdatingValue(email);
        newVerificationToken.setUser(user);
        verificationTokenRepository.save(newVerificationToken);
        sendUpdateEmailVerificationEmail(user, token);
        return "A verification email has been sent on the provided email.Your email will be updated after you verify the email";
    }

    private void sendUpdateEmailVerificationEmail(User user, String token){
        String url = serverBaseUrl + "/user/update-email/"+token;
        String btnName = "Verify";
        String text = "Please click on the button below to verify your email and register it with " +
                "your account in BookingApp.";
        String msg = mailContentBuilder.build(text, url, btnName);
        String subject = "Please Verify your email.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }

    public String verifyUpdateEmailVerificationToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new
                CustomException("Invalid Token."));
        User user = verificationToken.getUser();
        user.setEmail(verificationToken.getUpdatingValue());
        userRepository.save(user);
        verificationTokenRepository.deleteById(verificationToken.getId());
        return "Email verified and updated successfully.";
    }

    public List<UserResponseDto> getUsers(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt"));
        return userRepository.findAll(pageable).stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User with given id doesn't exist"));
        return userMapper.mapToDto(user);
    }
}
