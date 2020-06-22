package com.enjoyit.persistence;

import org.springframework.security.core.GrantedAuthority;

public interface Role extends GrantedAuthority{

    @Override
    String getAuthority();

    String getId();
}
