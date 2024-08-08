package com.utcv.tesina.services.impl;

import com.utcv.tesina.persistence.entities.UserEntity;
import com.utcv.tesina.persistence.repositories.UserRepository;
import com.utcv.tesina.services.models.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;


    @Override
    public UserEntity createUser(UserEntity userEntity) {
        try{
            return userRepository.save(userEntity);
        }catch (Exception e){
            LOGGER.error("Error while creating user: {}", e.getMessage());
            throw new RuntimeException("Error creating user");
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        try {
            return userRepository.findAll();
        }catch (Exception e){
            LOGGER.error("Error while fetching all users: {}", e.getMessage());
            throw new RuntimeException("Error fetching tutors");
        }
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        try{
            return userRepository.findById(id);
        }catch (Exception e){
            LOGGER.error("Error while fetching user by ID: {}", e.getMessage());
            throw new RuntimeException("Error fetching user by ID");
        }
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity newUser) {
        try {
            Optional<UserEntity> existingUserOpt = userRepository.findById(id);
            if (existingUserOpt.isPresent()){
                UserEntity existingUser = existingUserOpt.get();
                existingUser.setEmail(newUser.getEmail());
                existingUser.setPassword(newUser.getPassword());

                return userRepository.save(existingUser);
            }
            throw new RuntimeException("User not found");
        }catch (Exception e){
            LOGGER.error("Error while updating user: {}", e.getMessage());
            throw new RuntimeException("Error updating user");
        }
    }

    @Override
    public HashMap<String, String> deleteUser(Long id) {
        try {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully!");
            userRepository.deleteById(id);
            return response;
        }catch (Exception e){
            LOGGER.error("Error while deleting user: {}", e.getMessage());
            throw new RuntimeException("Error deleting user");
        }
    }
}
