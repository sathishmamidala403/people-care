package com.care.people.auth.service.impl;

import com.care.people.auth.model.AuthRequest;
import com.care.people.auth.model.AuthResponse;
import com.care.people.auth.model.UserRegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface UserServiceImpl {

    AuthResponse authenticate(AuthRequest request);
    ResponseEntity<?> register(UserRegistrationRequest request);
}
