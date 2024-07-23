package com.lib.dto;

import com.lib.domain.Loans;
import com.lib.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer score;

    private String address;

    private String phone;

    private LocalDate birthDate;

    private String email;

    private String password;

    private LocalDateTime createAt = LocalDateTime.now();

    private Boolean builtIn = false;

    private Set<String> roles ;

    public void setRoles(Set<Role> roles) {
        Set<String> roleStr=new HashSet<>();
        roles.forEach(Role::getName);
        this.roles=roleStr;
    }
}
