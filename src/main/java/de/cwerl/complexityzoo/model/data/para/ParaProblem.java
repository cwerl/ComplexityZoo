package de.cwerl.complexityzoo.model.data.para;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.Problem;


@Entity
@DiscriminatorValue(ComplexityDataType.Values.PARAMETERIZED)
public class ParaProblem extends Problem {
    
}
