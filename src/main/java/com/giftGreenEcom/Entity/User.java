package com.giftGreenEcom.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;

    private String resetToken;
    private LocalDateTime tokenExpiry;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "email")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }

    )
    private Set<Role> Role;

    public User() {
    }

    public User(Long id, String name, String email, String password, String phone, String resetToken, LocalDateTime tokenExpiry, Set<Role> role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.resetToken = resetToken;
        this.tokenExpiry = tokenExpiry;
        Role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Role> getRole() {
        return Role;
    }

    public void setRole(Set<Role> role) {
        Role = role;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }
}
