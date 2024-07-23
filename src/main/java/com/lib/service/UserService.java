package com.lib.service;

import com.lib.domain.Role;
import com.lib.domain.RoleType;
import com.lib.domain.User;
import com.lib.dto.UserDTO;
import com.lib.dto.request.RegisterRequest;
import com.lib.exception.ConflictException;
import com.lib.exception.ErrorMessage;
import com.lib.exception.ResourceNotFoundException;
import com.lib.mapper.UserMapper;
import com.lib.repository.UserRepository;
import com.lib.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
//@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND_MESSAGE));
    }

    public User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
        return getUserByEmail(email);
    }

    public UserDTO saveUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, registerRequest.getEmail()));
        }
        Role role = roleService.findByRoleName(RoleType.ROLE_MEMBER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhone(registerRequest.getPhone());
        user.setAddress(registerRequest.getAddress());
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }
}
