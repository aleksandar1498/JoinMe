package com.enjoyit.utils;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.enjoyit.domain.dto.RoleDTO;

@Component
public class GrantedAuthorityToRoleDTOConverter implements Converter<SimpleGrantedAuthority, RoleDTO> {

    @Override
    public RoleDTO convert(final MappingContext<SimpleGrantedAuthority, RoleDTO> context) {
        return new RoleDTO(context.getSource().getAuthority().replace("ROLE_", ""));
    }

}
