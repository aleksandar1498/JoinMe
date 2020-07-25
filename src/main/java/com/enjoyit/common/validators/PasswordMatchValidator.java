package com.enjoyit.common.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.enjoyit.domain.dto.UserRegisterDTO;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchingValidator, UserRegisterDTO>{

    @Override
    public boolean isValid(final UserRegisterDTO user, final ConstraintValidatorContext context) {
        if (!(user instanceof UserRegisterDTO)) {
            throw new IllegalArgumentException("@PasswordMatchingValidator only applies to UserRegisterModel");
        }

        return user.getPassword().equals(user.getConfirmPassword());
    }

}
