package com.order.application.controller;

import com.order.application.dto.LoginRequest;
import com.order.application.dto.LoginResponse;
import com.order.application.dto.SignupRequest;
import com.order.application.dto.SignupResponse;
import com.order.application.security.JwtUtil;
import com.order.application.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Qualifier("authenticationManager")
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ClientController(ClientService clientService, JwtUtil jwtUtil) {
        this.clientService = clientService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request){
        SignupResponse response = clientService.register(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
       try {
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
           );
           String token = jwtUtil.generateToken(authentication.getName());
           return new LoginResponse(token);
       }catch (AuthenticationException e){
           throw new RuntimeException("Invalid email or password");
       }

    }


}
