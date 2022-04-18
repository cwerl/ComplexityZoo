package de.cwerl.complexityzoo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Problem {
    
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter @Setter
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter @ManyToOne @NotNull
    private ComplexityClass complexityClass;
}
