package com.giftGreenEcom.Controller;

import com.giftGreenEcom.DTO.UserDto.UserRespond;
import com.giftGreenEcom.Entity.User;
import com.giftGreenEcom.Service.AuthService;
import com.giftGreenEcom.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;


//    @Autowired
//    private EmailService emailService;


    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
    @PostMapping({"/registerUser"})
    public User registerNewAdmin(@RequestBody User user) {
        return userService.registeruser(user);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public List<UserRespond> getAllUsers() {
        return userService.getAllUsers();
    }






//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
//        userService.generateResetToken(email);
//        return ResponseEntity.ok("Password reset link sent to your email.");
//    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
//        userService.resetPassword(token, newPassword);
//        return ResponseEntity.ok("Password reset successful.");
//    }

}
