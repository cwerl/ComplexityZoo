package de.cwerl.complexityzoo.model.data.normal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;

@Entity
@DiscriminatorValue(ComplexityDataType.Values.NORMAL)
public class NormalComplexityClass extends ComplexityClass {
    
    public NormalComplexityClass() {}

    public NormalComplexityClass(String name) {
        super(name);
    }
}
