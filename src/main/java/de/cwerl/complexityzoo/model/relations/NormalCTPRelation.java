package de.cwerl.complexityzoo.model.relations;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Normal")
public class NormalCTPRelation extends CTPRelation {
    
}
