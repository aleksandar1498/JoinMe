package com.enjoyit.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.Role;
import com.enjoyit.persistence.entities.JpaRole;

/**
 * @author AStefanov
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<JpaRole, String>{

    /**
     * @param authority
     * @return
     */
    Role findByAuthority(UserRoles authority);
}
