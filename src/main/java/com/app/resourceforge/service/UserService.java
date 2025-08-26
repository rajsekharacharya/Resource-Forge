package com.app.resourceforge.service;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.DTO.UserDTO;

public interface UserService {

    
    ResponseEntity<?> getSaasUser();

    ResponseEntity<?> creatSaasUser(UserDTO userDTO);

    ResponseEntity<?> creatSuperAdmin( UserDTO userDTO);

    ResponseEntity<?> getSuperAdmin();

    ResponseEntity<?> accountStatusToggle(Integer id);

    ResponseEntity<?> accountExpiryToggle(Integer id);

    ResponseEntity<?> updateUser(UserDTO userDTO);

    ResponseEntity<?> userGetById(Integer id);

    Boolean usernameAvailability(String username);

    Boolean emailAvailability(String email);

    ResponseEntity<?> creatAdmin(UserDTO userDTO);

    ResponseEntity<?> getAdmin();

    ResponseEntity<?> creatUser(UserDTO userDTO);

    ResponseEntity<?> getUser();

    ResponseEntity<?> deleteUser(Integer id);

    ResponseEntity<?> getServiceEngineer();

    ResponseEntity<?> getLoginUser();


}
