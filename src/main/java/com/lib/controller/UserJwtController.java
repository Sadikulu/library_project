package com.lib.controller;

import com.lib.dto.UserDTO;
import com.lib.dto.request.LoginRequest;
import com.lib.dto.request.LoginResponse;
import com.lib.dto.request.RegisterRequest;
import com.lib.security.jwt.JwtUtils;
import com.lib.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserJwtController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	// register user
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		UserDTO userDTO= userService.saveUser(registerRequest);
		return ResponseEntity.ok(userDTO);
	}

	// login user
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(), loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(passwordAuthenticationToken);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtUtils.generateJwtToken(userDetails);
		LoginResponse loginResponse = new LoginResponse(token);
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}

}
