package com.app.resourceforge.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.configuration.OTPGenerator;
import com.app.resourceforge.model.User;
import com.app.resourceforge.repository.UserRepository;

@RestController
@RequestMapping("/test")
public class test {

    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping(value = "/pushOTP", produces = "application/json")
    public ResponseEntity<String> setOTP(@RequestParam String username, @RequestParam String email) {
        Optional<User> user = userRepository.findByUsernameAndEmail(username, email);
        if (user.isPresent()) {

            try {
                String generateOTP = OTPGenerator.generateOTP();
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(SENDER_EMAIL);
                helper.setTo(user.get().getEmail());
                helper.setSubject("Password Reset Request");
                helper.setText("Your OTP is:" + generateOTP, true);
                emailSender.send(message);

                user.get().setOtp(generateOTP);
                user.get().setOtpExprTime(LocalDateTime.now());
                userRepository.save(user.get());

                return new ResponseEntity<String>("Please Enter OTP AND Password", HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<String>("Internal Server Error", HttpStatus.GATEWAY_TIMEOUT);
            }

        } else {
            return new ResponseEntity<String>("User Not Found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/resetPassword", produces = "application/json")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String email,
            @RequestParam String otp, @RequestParam String password) {
        Optional<User> user = userRepository.findByUsernameAndEmail(username, email);
        if (user.isPresent()) {

            LocalDateTime dt = LocalDateTime.now();
            Duration duration = Duration.between(user.get().getOtpExprTime(), dt);
            long minutes = duration.toMinutes();

            if (minutes <= 5) {
                try {
                    MimeMessage message = emailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setFrom(SENDER_EMAIL);
                    helper.setTo(user.get().getEmail());
                    helper.setSubject("Password Reset Successfully");
                    helper.setText("Your Password is:" + password, true);
                    emailSender.send(message);

                    String encode = passwordEncoder.encode(password);
                    user.get().setPassword(encode);
                    userRepository.save(user.get());
                    return new ResponseEntity<String>("Password Reset Successfully", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<String>("Internal Server Error", HttpStatus.GATEWAY_TIMEOUT);
                }

            } else {
                return new ResponseEntity<String>("OTP Expiry, Please Resent", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<String>("User Not Found", HttpStatus.BAD_REQUEST);
        }
    }
}
