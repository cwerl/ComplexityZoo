package de.cwerl.complexityzoo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
public class ComplexityClass {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter @Setter
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Getter @Setter
    private String description;
    
    @Getter @Setter
    @OneToMany(mappedBy = "complexityClass", cascade = CascadeType.REMOVE)
    List<Problem> problems;
}
