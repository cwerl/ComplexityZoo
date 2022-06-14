package de.cwerl.complexityzoo.model.data.normal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.Problem;

@Entity
@DiscriminatorValue(ComplexityDataType.Values.NORMAL)
public class NormalProblem extends Problem {
    
}
