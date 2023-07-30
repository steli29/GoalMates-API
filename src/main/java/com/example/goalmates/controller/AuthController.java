package com.example.goalmates.controller;

import com.example.goalmates.dto.UserWithoutPasswordDTO;
import com.example.goalmates.dto.VerifyDTO;
import com.example.goalmates.service.AuthenticationService;
import com.example.goalmates.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<UserWithoutPasswordDTO> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/verify")
    public ResponseEntity<UserWithoutPasswordDTO> verify(@RequestBody VerifyDTO verifyDTO){
        return ResponseEntity.ok(service.verify(verifyDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<UserWithoutPasswordDTO> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.login(request));
    }
}
