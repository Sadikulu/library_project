package com.lib;

import com.lib.domain.Role;
import com.lib.domain.RoleType;
import com.lib.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

    roleRepository.save(new Role(1,RoleType.ROLE_MEMBER));
    roleRepository.save(new Role(2,RoleType.ROLE_EMPLOYEE));
    roleRepository.save(new Role(3, RoleType.ROLE_ADMIN));
    }
}

