package com.enjoyit.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.domain.entities.JpaRole;
import com.enjoyit.enums.UserRoles;

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
