package de.cwerl.complexityzoo.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
public class InvitedUser {
    
    @Id
    @Getter @Setter
    private String email;
}
