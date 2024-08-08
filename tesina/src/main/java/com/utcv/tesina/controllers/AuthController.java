package com.utcv.tesina.controllers;

import com.utcv.tesina.persistence.entities.UserEntity;
import com.utcv.tesina.persistence.repositories.UserRepository;
import com.utcv.tesina.services.models.IAuthService;
import com.utcv.tesina.services.models.dtos.LoginDTO;
import com.utcv.tesina.services.models.dtos.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(IAuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserEntity user) throws Exception {
        ResponseDTO response = authService.register(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) throws Exception {
        HashMap<String, String> loginResponse = authService.login(loginRequest);
        if (loginResponse.containsKey("jwt")) {
            // Si la autenticación es exitosa, buscar el usuario por email
            Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getEmail());
            if (user.isPresent()) {
                HashMap<String, Object> response = new HashMap<>();
                response.put("jwt", loginResponse.get("jwt"));
                response.put("user", user.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}
