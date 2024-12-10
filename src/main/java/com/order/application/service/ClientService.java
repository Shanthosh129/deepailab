package com.order.application.service;

import com.order.application.dto.LoginRequest;
import com.order.application.dto.SignupRequest;
import com.order.application.dto.SignupResponse;
import com.order.application.entity.Client;
import com.order.application.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepo clientRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public ClientService(ClientRepo clientRepo, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepo = clientRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public SignupResponse register(SignupRequest request) {
        if(clientRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        clientRepo.save(client);

        SignupResponse response = new SignupResponse();
        response.setEmail(client.getEmail());
        response.setMessage("Client registered successfully");
        return response;
    }
    public String verify(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return "success";
        } else {
            return "fail";
        }
    }
}
