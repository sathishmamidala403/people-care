package com.care.people.auth.service;

import com.care.people.auth.model.AuthRequest;
import com.care.people.auth.model.AuthResponse;
import com.care.people.auth.model.User;
import com.care.people.auth.model.UserRegistrationRequest;
import com.care.people.auth.repository.UserRepository;
import com.care.people.auth.service.impl.UserServiceImpl;
import com.care.people.auth.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImpl {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AuthenticationManager authManager,
                       JwtUtil jwtUtil,
                       CustomUserDetailsService userDetailsService,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token);
    }

    public ResponseEntity<?> register(UserRegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User(
                request.getUsername(),
                encodedPassword,
                request.getRole(),
                request.getProfileId()
        );

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
