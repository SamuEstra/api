package com.utcv.tesina.services.models.validation;


import com.utcv.tesina.persistence.entities.UserEntity;
import com.utcv.tesina.services.models.dtos.ResponseDTO;

public class UserValidation {

    public ResponseDTO validate(UserEntity users){
        ResponseDTO response = new ResponseDTO();

        if (users.getEmail() == null ||
                !users.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo email no es correcto");
        }

        if (users.getPassword() == null ||
                !users.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("La contraseña debe tener entre 8 y 16 caracteres, al menos un número, una minúscula y una mayúscula.");
        }

        return response;

    }

}
