package de.cwerl.complexityzoo.model.data.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;

@Entity
@DiscriminatorValue(ComplexityDataType.Values.PARAMETERIZED)
public class ParaComplexityClass extends ComplexityClass {
    
    public ParaComplexityClass() {}

    public ParaComplexityClass(String name) {
        super(name);
    }
}
