package de.cwerl.complexityzoo.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@PasswordMatches
public class UserDto {
    @NotNull
    @NotBlank
    @Getter @Setter
    private String username;

    @NotNull
    @NotBlank
    @Getter @Setter
    private String email;

    @NotNull
    @NotBlank
    @Getter @Setter
    @Size(min = 8, max = 20)
    private String password;
    @Getter @Setter
    private String matchingPassword;

}
