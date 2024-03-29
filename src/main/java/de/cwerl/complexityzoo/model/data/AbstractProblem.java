package de.cwerl.complexityzoo.model.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractProblem extends ComplexityData {

    @Getter @Setter
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    @Getter
    @Column(name = "type", insertable = false, updatable = false, nullable = false)
    private String type;
}
