package com.tp.backend.service;

import com.tp.backend.enums.TokenType;
import com.tp.backend.model.User;
import com.tp.backend.model.VerificationToken;
import com.tp.backend.repository.UserRepository;
import com.tp.backend.repository.VerificationTokenRepository;
import com.tp.backend.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CommonService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return "." + fileName.substring(i + 1);
        } else {
            throw new CustomException("File doesn't have any extension.");
        }
    }

    public String generateVerificationToken(User user, TokenType tokenType) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setTokenType(tokenType);
        verificationToken.setUser(user);
        verificationToken.setCreatedAt(LocalDateTime.now());
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new CustomException("Invalid User Details."));
        return user;
    }
}
