package com.lib.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@Email(message = "Please provide your email")
	@NotBlank
	@NotNull
	private String email;

	@NotBlank
	@NotNull
	private String password; 
}
