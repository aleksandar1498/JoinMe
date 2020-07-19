package com.enjoyit.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.enjoyit.domain.dto.RoleDTO;

public class StringToRoleDTOConverterFactory implements ConverterFactory<String, RoleDTO> {

    private static class StringToRoleDTOConverter<T extends RoleDTO> implements Converter<String, T> {
        public StringToRoleDTOConverter(final Class<T> roleDTOType) {
        }

        @Override
        public T convert(final String role) {
            return (T) new RoleDTO(role);
        }

    }

    @Override
    public <T extends RoleDTO> Converter<String, T> getConverter(final Class<T> targetType) {
        return new StringToRoleDTOConverter<T>(targetType);
    }

}
