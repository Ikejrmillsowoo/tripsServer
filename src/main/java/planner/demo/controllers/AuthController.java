package planner.demo.controllers;

import planner.demo.DTO.auth.AuthResponse;
import planner.demo.DTO.auth.LoginRequest;
import planner.demo.DTO.auth.RegisterRequest;
import planner.demo.DTO.common.ApiResponse;
import planner.demo.models.User;
import planner.demo.security.JwtTokenProvider;
import planner.demo.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtTokenProvider.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getName()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        User user = authService.authenticate(request.getEmail(), request.getPassword());

        String token = jwtTokenProvider.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getName()
        );

        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            boolean isValid = jwtTokenProvider.validateToken(token);
            return ResponseEntity.ok(ApiResponse.success(isValid));
        }

        return ResponseEntity.ok(ApiResponse.success(false));
    }
}