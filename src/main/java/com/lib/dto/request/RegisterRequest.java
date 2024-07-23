package com.lib.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 2, max = 30, message = "Your first name ${validatedValue} must be between ${min} and ${max} characters.")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30, message = "Your last name ${validatedValue} must be between ${min} and ${max} characters." )
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", //541-317-8828
            message = "Please provide valid phone number")
    private String phone;

    @Size(min = 10, max=100)
    @NotBlank(message = "Please provide your address")
    @NotNull(message = "Please provide your address")
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Size(min=10, max=80)
    @Email
    private String email;

    private String password;

}
