package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.DTO.UserDTO;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.model.User;
import com.app.resourceforge.repository.CompanyRepository;
import com.app.resourceforge.repository.SubscriptionPlanRepository;
import com.app.resourceforge.repository.SuperCompanyRepository;
import com.app.resourceforge.repository.UserRepository;
import com.app.resourceforge.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SuperCompanyRepository superCompanyRepository;

    @Autowired
    SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;


    @Override
    public ResponseEntity<?> getSaasUser() {
        return ResponseEntity.status(HttpStatus.OK).body(
                userRepository.findByRole("VARELI"));
    }

    @Override
    public ResponseEntity<?> creatSaasUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name already taken");

        } else {
            User user = modelMapper.map(userDTO, User.class);
            // String pass = user.getPassword();
            user.setRole("VARELI");
            user.setCoId(0);
            user.setSuperCompanyId(0);
            user.setEnabled(true);
            user.setAccountNotExpired(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> creatSuperAdmin(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name already taken");

        } else {
            User user = modelMapper.map(userDTO, User.class);
            // String pass = user.getPassword();
            user.setRole("SUPERADMIN");
            user.setCoId(0);
            user.setEnabled(true);
            user.setAccountNotExpired(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> getSuperAdmin() {
        return ResponseEntity.status(HttpStatus.OK).body(
                userRepository.findByRole("SUPERADMIN").stream()
                        .peek(user -> user.setCompanyName(
                                superCompanyRepository.findById(user.getSuperCompanyId()).get().getName()))
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> accountStatusToggle(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "User Enabled");
        messages.put(false, "User Disabled");

        String message = userOptional
                .map(user -> {
                    user.setEnabled(!user.isEnabled());
                    userRepository.save(user);
                    return messages.get(user.isEnabled());
                })
                .orElse("User Not Found");

        return ResponseEntity.status(message.equals("User Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);

    }

    @Override
    public ResponseEntity<?> accountExpiryToggle(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "User Expired");
        messages.put(false, "User Active");

        String message = userOptional
                .map(user -> {
                    user.setAccountNotExpired(!user.isAccountNotExpired());
                    userRepository.save(user);
                    return messages.get(user.isAccountNotExpired());
                })
                .orElse("User Not Found");

        return ResponseEntity.status(message.equals("User Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

    @Override
    public ResponseEntity<?> updateUser(UserDTO userDTO) {
        User map = modelMapper.map(userDTO, User.class);
        Optional<User> findById = userRepository.findById(map.getId());
        if (findById.isPresent()) {
            if (map.getPassword() == null) {
                map.setPassword(findById.get().getPassword());
            } else {
                map.setPassword(passwordEncoder.encode(map.getPassword()));
            }
            User save = userRepository.save(map);
            return ResponseEntity.status(HttpStatus.OK).body("Updated");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User");
        }

    }

    @Override
    public ResponseEntity<?> userGetById(Integer id) {
        Optional<User> findById = userRepository.findById(id);
        if (findById.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(findById.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Found");
        }
    }

    @Override
    public Boolean usernameAvailability(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean emailAvailability(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseEntity<?> creatAdmin(UserDTO userDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetail = (MyUserDetails) auth.getPrincipal();
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name already taken");
        } else {
            User user = modelMapper.map(userDTO, User.class);
            // String pass = user.getPassword();
            user.setRole("ADMIN");
            user.setEnabled(true);
            user.setSuperCompanyId(userDetail.getSuperCompanyId());
            user.setAccountNotExpired(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> getAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails logeduser = (MyUserDetails) auth.getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(
                userRepository.findByRoleAndSuperCompanyId("ADMIN", logeduser.getSuperCompanyId())
                        .stream()
                        .peek(user -> user.setCompanyName(companyRepository.findById(user.getCoId()).get().getName()))
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> creatUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name already taken");

        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails logeduser = (MyUserDetails) auth.getPrincipal();

            Optional<SubscriptionPlan> plan = subscriptionPlanRepository.findByStatusAndSuperCompanyId(true,
                    logeduser.getSuperCompanyId());

            Integer userCount = userRepository.countByCoIdAndSuperCompanyId(logeduser.getCompanyId(),
                    logeduser.getSuperCompanyId());

            if (plan.get().getUser() > userCount) {

                User user = modelMapper.map(userDTO, User.class);
                // String pass = user.getPassword();
                user.setCoId(logeduser.getCompanyId());
                user.setSuperCompanyId(logeduser.getSuperCompanyId());
                user.setEnabled(true);
                user.setAccountNotExpired(true);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body("Saved");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Limit Exceeded");
            }

        }

    }

    @Override
    public ResponseEntity<?> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails logeduser = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(
                userRepository.findByCoIdAndSuperCompanyId(logeduser.getCompanyId(), logeduser.getSuperCompanyId())
                        .stream()
                        .peek(user -> user.setCompanyName(
                                superCompanyRepository.findById(user.getSuperCompanyId()).get().getName()))
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
    }

    @Override
    public ResponseEntity<?> getServiceEngineer() {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByRoleAndSuperCompanyIdAndCoIdAndEnabled(
                "SERVICEENGINEER", user.getSuperCompanyId(), user.getCompanyId(), true));
    }

    @Override
    public ResponseEntity<?> getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails logeduser = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(
                userRepository.findByUsername(logeduser.getUsername()));


    }

}
