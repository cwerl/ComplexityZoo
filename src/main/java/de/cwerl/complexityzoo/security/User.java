package de.cwerl.complexityzoo.security;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
public class User {
    
    @Id
    @NotBlank(message = "Username is required")
    @Getter @Setter
    private String username;

    @NotBlank(message = "Email is required")
    @Getter @Setter
    private String email;

    @NotBlank(message = "Password is required")
    @Getter @Setter
    private String password;

    protected User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
