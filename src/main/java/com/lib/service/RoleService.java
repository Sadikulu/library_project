package com.lib.service;

import com.lib.domain.Role;
import com.lib.domain.RoleType;
import com.lib.exception.ErrorMessage;
import com.lib.exception.ResourceNotFoundException;
import com.lib.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByRoleName(RoleType roleType) {
        return roleRepository.findByName(roleType).orElseThrow(() ->
                new ResourceNotFoundException(String.format(
                        ErrorMessage.ROLE_NOT_FOUND_MESSAGE, roleType.name())));

    }
}
