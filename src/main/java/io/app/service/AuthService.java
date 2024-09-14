package io.app.service;

import io.app.dto.LoginRequest;
import io.app.dto.RegisterRequest;
import io.app.dto.ResponseToken;

import java.util.Optional;

public interface AuthService {
    public ResponseToken register(RegisterRequest registerRequest);
    public ResponseToken login(LoginRequest loginRequest);
}
