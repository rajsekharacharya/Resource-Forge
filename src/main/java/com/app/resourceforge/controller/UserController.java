package com.app.resourceforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.DTO.UserDTO;
import com.app.resourceforge.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getSaasUser")
    public ResponseEntity<?> getSaasUser() {
        return userService.getSaasUser();
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PostMapping(value = "/creatSaasUser")
    public ResponseEntity<?> creatSaasUser(@RequestBody UserDTO userDTO) {
        return userService.creatSaasUser(userDTO);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PostMapping(value = "/creatSuperAdmin")
    public ResponseEntity<?> creatSuperAdmin(@RequestBody UserDTO userDTO) {
        return userService.creatSuperAdmin(userDTO);
    }

    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @PostMapping(value = "/creatAdmin")
    public ResponseEntity<?> creatAdmin(@RequestBody UserDTO userDTO) {
        return userService.creatAdmin(userDTO);
    }

    @PostMapping(value = "/creatUser")
    public ResponseEntity<?> creatUser(@RequestBody UserDTO userDTO) {
        return userService.creatUser(userDTO);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getSuperAdmin")
    public ResponseEntity<?> getSuperAdmin() {
        return userService.getSuperAdmin();
    }

    @GetMapping(value = "/getUser")
    public ResponseEntity<?> getUser() {
        return userService.getUser();
    }
    @GetMapping(value = "/getServiceEngineer")
    public ResponseEntity<?> getServiceEngineer() {
        return userService.getServiceEngineer();
    }

    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @GetMapping(value = "/getAdmin")
    public ResponseEntity<?> getAdmin() {
        return userService.getAdmin();
    }

    @PreAuthorize("hasAnyAuthority('VARELI','SUPERADMIN','ADMIN')")
    @PutMapping(value = "/accountStatusToggle")
    public ResponseEntity<?> accountStatusToggle(@RequestParam Integer id) {
        return userService.accountStatusToggle(id);
    }

    @PreAuthorize("hasAnyAuthority('VARELI','SUPERADMIN','ADMIN')")
    @PutMapping(value = "/accountExpiryToggle")
    public ResponseEntity<?> accountExpiryToggle(@RequestParam Integer id) {
        return userService.accountExpiryToggle(id);
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @GetMapping(value = "/userGetById")
    public ResponseEntity<?> userGetById(@RequestParam Integer id) {
        return userService.userGetById(id);
    }

    @GetMapping(value = "/usernameAvailability")
    public Boolean usernameAvailability(@RequestParam String username) {
        return userService.usernameAvailability(username);
    }

    @GetMapping(value = "/emailAvailability")
    public Boolean emailAvailability(@RequestParam String email) {
        return userService.emailAvailability(email);
    }

    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        return userService.deleteUser(id);
    }

    
    @GetMapping(value = "/getLoginUser")
    public ResponseEntity<?> getLoginUser() {
        return userService.getLoginUser();
    }

}
