package com.lib.service;

import com.lib.domain.User;
import com.lib.exception.ErrorMessage;
import com.lib.exception.ResourceNotFoundException;
import com.lib.repository.UserRepository;
import com.lib.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND_MESSAGE));
    }

    public User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
        return getUserByEmail(email);

    }
}
