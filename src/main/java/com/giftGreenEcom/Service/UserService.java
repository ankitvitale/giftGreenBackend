package com.giftGreenEcom.Service;

import com.giftGreenEcom.DTO.UserDto.UserRespond;
import com.giftGreenEcom.Entity.Admin;
import com.giftGreenEcom.Entity.Role;
import com.giftGreenEcom.Entity.User;
import com.giftGreenEcom.Repository.AdminRepository;
import com.giftGreenEcom.Repository.RoleDao;
import com.giftGreenEcom.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminDao;

    @Autowired
    private UserRepository userDao;

//    @Autowired
//    private EmailService emailService; // Service for sending emails

    public void initRoleAndUser() {
        // Create roles
        if (!roleDao.existsById("Admin")) {
            Role adminRole = new Role();
            adminRole.setRoleName("Admin");
            adminRole.setRoleDescription("Admin role");
            roleDao.save(adminRole);
        }
        if (!roleDao.existsById("User")) {
            Role userRole = new Role();
            userRole.setRoleName("User");
            userRole.setRoleDescription("User role");
            roleDao.save(userRole);
        }


    }

    public Admin registerAdmin(Admin admin) {
        Role role = roleDao.findById("Admin").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        admin.setRole(userRoles);
        admin.setPassword(getEncodedPassword(admin.getPassword()));

        adminDao.save(admin);
        return admin;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
    public User registeruser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Role role = roleDao.findById("User").orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);

        user.setPassword(getEncodedPassword(user.getPassword())); // Ensure password is valid
        userDao.save(user);
        return user;
    }

    public List<UserRespond> getAllUsers() {
        return userDao.findAll().stream().map(u ->
                new UserRespond(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getPhone()
                )
        ).toList();
    }



//
//
//    public void generateResetToken(String email) {
//        User user = userDao.findByEmail(email)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        String token = UUID.randomUUID().toString();
//        user.setResetToken(token);
//        user.setTokenExpiry(LocalDateTime.now().plusMinutes(30)); // Token valid for 30 min
//        userDao.save(user);
//
//        String resetLink = "http://localhost:8080/auth/reset-password?token=" + token;
//        emailService.sendEmail(user.getEmail(), "Reset Password",
//                "Click the link to reset your password: " + resetLink);
//    }
//
//    public void resetPassword(String token, String newPassword) {
//        User user = userDao.findByResetToken(token)
//                .orElseThrow(() -> new EntityNotFoundException("Invalid or expired token"));
//
//        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
//            throw new IllegalStateException("Token expired");
//        }
//
//        user.setPassword(new BCryptPasswordEncoder().encode(newPassword)); // Encrypt password
//        user.setResetToken(null);
//        user.setTokenExpiry(null);
//        userDao.save(user);
//    }

}
