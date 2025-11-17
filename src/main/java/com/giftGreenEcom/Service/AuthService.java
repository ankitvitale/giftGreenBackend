package com.giftGreenEcom.Service;

import com.giftGreenEcom.DTO.JwtRequest;
import com.giftGreenEcom.DTO.JwtResponse;
import com.giftGreenEcom.Entity.Admin;
import com.giftGreenEcom.Entity.User;
import com.giftGreenEcom.Repository.AdminRepository;
import com.giftGreenEcom.Repository.UserRepository;
import com.giftGreenEcom.Security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private JwtHelper jwtUtil;

    @Autowired
    private AdminRepository adminDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String email = jwtRequest.getEmail();
        String password = jwtRequest.getPassword();

        authenticate(email, password);
        UserDetails userDetails = loadUserByUsername(email);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        Admin admin = adminDao.findByEmail(email);
        Optional<User> user = userDao.findByEmail(email);

        if (admin != null) {
            return new JwtResponse(admin, null, newGeneratedToken);
        } else if (user.isPresent()) {
            return new JwtResponse(null, user.get(), newGeneratedToken);
        } else {
            throw new Exception("User not found with email: " + email);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        Admin admin = adminDao.findByEmail(email);
        Optional<User> userOptional = userDao.findByEmail(email);

        if (admin == null && userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        Collection<GrantedAuthority> authorities = new HashSet<>();
        String password = null;

        if (admin != null) {
            admin.getRole().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
            password = admin.getPassword();
        } else {
            User user = userOptional.get();
            user.getRole().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
            password = user.getPassword();
        }

        if (password == null) {
            throw new UsernameNotFoundException("Password is missing for email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(email, password, authorities);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
