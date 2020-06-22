package com.enjoyit.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.enjoyit.domain.entities.JpaRole;
import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.RoleRepository;

@Component
public class DBInitialConfig implements ApplicationListener<ContextRefreshedEvent>{

    private boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    private void createRoleIfNotFound(
      final UserRoles authority) {

       if(roleRepository.findByAuthority(authority) == null) {
           this.roleRepository.save(new JpaRole(authority));
       }
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if(alreadySetup) {
            return;
        }

        createRoleIfNotFound(UserRoles.ADMIN);
        createRoleIfNotFound(UserRoles.USER);
        createRoleIfNotFound(UserRoles.ORGANIZER);
        alreadySetup = true;
    }

}
