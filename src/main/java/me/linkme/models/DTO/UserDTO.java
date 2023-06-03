package me.linkme.models.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.linkme.models.Validators.PasswordMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@PasswordMatch
public class UserDTO {


    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}


