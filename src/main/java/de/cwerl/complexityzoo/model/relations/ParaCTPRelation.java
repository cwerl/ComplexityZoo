package de.cwerl.complexityzoo.model.relations;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Parameterized")
public class ParaCTPRelation extends CTPRelation {
    
    @Getter @Setter
    private String param;
}
