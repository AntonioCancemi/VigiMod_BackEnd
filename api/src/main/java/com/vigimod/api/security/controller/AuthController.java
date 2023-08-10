package com.vigimod.api.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vigimod.api.security.entity.User;
import com.vigimod.api.security.payload.JWTAuthResponse;
import com.vigimod.api.security.payload.LoginDto;
import com.vigimod.api.security.payload.RegisterDto;
import com.vigimod.api.security.repository.UserRepository;
import com.vigimod.api.security.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;
	@Autowired
	private UserRepository userRepo;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// Build Login REST API
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {

		String token = authService.login(loginDto);

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setUsername(loginDto.getUsername());
		jwtAuthResponse.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	// Build Register REST API
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		System.out.println(registerDto);
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = { "{username}" })
	public ResponseEntity<?> getMyProfile(@PathVariable String username) {
		if (!userRepo.existsByUsername(username)) {
			return new ResponseEntity<>("user not found!!", HttpStatus.NOT_FOUND);
		}
		User u = userRepo.findByUsername(username).get();
		u.setPassword("[PROTECTED]");
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
}
