package com.enjoyit.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.entities.JpaRole;
import com.enjoyit.persistence.repositories.RoleRepository;

@Component
public class DBInitialConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(final String... args) throws Exception {
        if (this.roleRepository.count() == 0) {
            Arrays.stream(UserRoles.values()).forEach(r -> {
                this.roleRepository.save(new JpaRole(r));
            });
        }

    }

}
