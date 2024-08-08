package com.utcv.tesina.services.models;

import com.utcv.tesina.persistence.entities.UserEntity;
import com.utcv.tesina.services.models.dtos.LoginDTO;
import com.utcv.tesina.services.models.dtos.ResponseDTO;

import java.util.HashMap;

public interface IAuthService {


    public HashMap<String,String> login(LoginDTO login) throws Exception;

    public ResponseDTO register(UserEntity user)throws Exception;
}
