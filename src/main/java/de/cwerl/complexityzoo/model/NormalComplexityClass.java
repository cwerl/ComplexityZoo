package de.cwerl.complexityzoo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Normal")
public class NormalComplexityClass extends ComplexityClass {
    
}
