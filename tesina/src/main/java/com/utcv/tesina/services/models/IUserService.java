package com.utcv.tesina.services.models;

import com.utcv.tesina.persistence.entities.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface IUserService {


    public UserEntity createUser(UserEntity userEntity);
    public List<UserEntity> getAllUsers();
    public Optional<UserEntity> getUserById(Long id);
    public UserEntity updateUser(Long id, UserEntity newUser);
    public HashMap<String,String> deleteUser(Long id);

}
