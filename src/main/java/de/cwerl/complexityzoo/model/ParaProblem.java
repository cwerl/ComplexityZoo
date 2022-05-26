package de.cwerl.complexityzoo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Parameterized")
public class ParaProblem extends Problem {
    
}
