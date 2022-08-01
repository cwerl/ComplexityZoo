package de.cwerl.complexityzoo.security;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
public class InvitedUser {
    
    @Id
    @Getter @Setter
    @NotBlank
    private String email;
}
