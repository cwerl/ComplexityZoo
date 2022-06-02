package de.cwerl.complexityzoo.model.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Parameterized")
public class ParaProblem extends Problem {
    
}
