package com.tp.backend.service;

import com.tp.backend.UserRepository;
import com.tp.backend.model.UserInfo;
import com.tp.backend.model.auth.AuthUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> user = userRepository.findByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found : "+email));
        return user.map(AuthUserDetails::new).get();
        //To convert user data into default UserDetails class object
    }
}
