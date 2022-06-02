package de.cwerl.complexityzoo.model.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Normal")
public class NormalProblem extends Problem {
    
}
