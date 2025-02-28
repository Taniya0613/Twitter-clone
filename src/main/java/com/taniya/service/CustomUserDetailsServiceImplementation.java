package com.taniya.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taniya.model.User;
import com.taniya.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by email (username)
        User user = userRepository.findByEmail(username);

        // Check if the user exists
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Check if the user is logging in with Google (optional, based on your requirements)
        if (user.isLogin_with_google()) {
            throw new UsernameNotFoundException("Google-login users cannot use email/password login: " + username);
        }

        // Create a list of authorities (roles)
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Add roles to the authorities list (example: ROLE_USER)
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Return a UserDetails object
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),       // Username (email)
            user.getPassword(),     // Password
            authorities             // Authorities (roles)
        );
    }
}