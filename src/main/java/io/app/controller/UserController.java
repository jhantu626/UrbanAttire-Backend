package io.app.controller;

import io.app.dto.LoginRequest;
import io.app.dto.RegisterRequest;
import io.app.dto.ResponseToken;
import io.app.service.AuthService;
import io.app.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseToken register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseToken login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }



}
