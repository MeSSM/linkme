package me.linkme.models.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import me.linkme.models.DTO.UserDTO;


public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserDTO> {

    @Override
    public void initialize(PasswordMatch p) {

    }

    public boolean isValid(UserDTO user, ConstraintValidatorContext c) {
        String plainPassword = user.getPassword();
        String repeatPassword = user.getConfirmPassword();

        return plainPassword != null && plainPassword.equals(repeatPassword);
    }

}
