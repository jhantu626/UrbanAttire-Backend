package io.app.service.impl;

import io.app.dto.LoginRequest;
import io.app.dto.RegisterRequest;
import io.app.dto.ResponseToken;
import io.app.exceptions.DuplicateFoundException;
import io.app.exceptions.ResourceNotFoundException;
import io.app.models.Roles;
import io.app.models.User;
import io.app.repository.UserRepository;
import io.app.service.AuthService;
import io.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseToken register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateFoundException("User already registerd!");
        }
        User user = User.builder()
                .name(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Roles.USER)
                .build();
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return ResponseToken.builder()
                .token(token)
                .status(true)
                .build();
    }

    @Override
    public ResponseToken login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new ResourceNotFoundException("Invalid Credentials!");  // Custom exception or message
        }
        User user=userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("Invalid Credentials!"));
        String token=jwtService.generateToken(user);

        return new ResponseToken(token,true);
    }

}
