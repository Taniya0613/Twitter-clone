package com.taniya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taniya.config.JwtProvider;
import com.taniya.dto.SignupRequest;
import com.taniya.exception.UserException;
import com.taniya.model.User;
import com.taniya.model.Verification;
import com.taniya.repository.UserRepository;
import com.taniya.response.AuthResponse;
import com.taniya.service.CustomUserDetailsServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsServiceImplementation customUserDetailsService;

    // ✅ SIGNUP endpoint with DTO
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest request) throws UserException {

        String email = request.getEmail();
        String password = request.getPassword();
        String fullName = request.getFullName();
        String birthDate = request.getBirthDate();

        if (userRepository.findByEmail(email) != null) {
            throw new UserException("Email is already used with another account");
        }

        // Create and save new user
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setBirthDate(birthDate);
        createdUser.setVerification(new Verification());

        userRepository.save(createdUser);

        // ✅ Authenticate user after signup
        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Generate JWT token only if authentication is successful
        String token = jwtProvider.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(token, true), HttpStatus.CREATED);
    }

    // ✅ SIGNIN endpoint
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody SignupRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            Authentication authentication = authenticate(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponse(token, true), HttpStatus.ACCEPTED);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthResponse(null, false), HttpStatus.UNAUTHORIZED);
        }
    }

    // ✅ Secure authentication method
    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
