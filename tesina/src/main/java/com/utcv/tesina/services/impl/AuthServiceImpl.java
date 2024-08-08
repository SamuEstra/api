package com.utcv.tesina.services.impl;

import com.utcv.tesina.persistence.entities.UserEntity;
import com.utcv.tesina.persistence.repositories.UserRepository;
import com.utcv.tesina.services.models.IAuthService;
import com.utcv.tesina.services.models.IJWTUtilityService;
import com.utcv.tesina.services.models.dtos.LoginDTO;
import com.utcv.tesina.services.models.dtos.ResponseDTO;
import com.utcv.tesina.services.models.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Autowired
    private UserValidation userValidation;


    @Override
    public HashMap<String,String> login(LoginDTO login) throws Exception {
        try {
            HashMap<String,String> jwt = new HashMap<>();
            Optional<UserEntity> users = userRepository.findByEmail(login.getEmail());

            if (users.isEmpty()){
                jwt.put("error", "User not registered!");
                return jwt;
            }

            //verificar la contrasena
            if (verifyPassword(login.getPassword(), users.get().getPassword())){
                jwt.put("jwt", jwtUtilityService.generateJWT(users.get().getId()));
            }else {
                jwt.put("error", "Authentication failed");
            }
            return jwt;


        }catch (Exception e){
            throw new Exception(e.toString());
        }

    }

    public ResponseDTO register(UserEntity users)throws Exception {
        try{
            ResponseDTO response = userValidation.validate(users);

            if (response.getNumOfErrors() > 0){
                return response;
            }

            List<UserEntity> getAllUsers = userRepository.findAll();

            for (UserEntity existingUser : getAllUsers) {
                if (existingUser.getEmail().equals(users.getEmail())) {
                    response.setMessage("Email already exists!");
                    return response;
                }
                // Agrega más comparaciones de campos relevantes según sea necesario.
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            users.setPassword(encoder.encode(users.getPassword()));
            userRepository.save(users);
            response.setMessage("User created successfully!");
            return response;

        }catch (Exception e){
            throw new Exception(e.toString());

        }
    }


    private boolean verifyPassword(String enteredPassword, String storedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
