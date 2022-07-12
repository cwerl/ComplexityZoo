package de.cwerl.complexityzoo.model.relations;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import de.cwerl.complexityzoo.model.data.ComplexityData;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class Relation {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter @Setter
    @NotBlank
    private String reference;

    public abstract String getPath();

    public abstract ComplexityData getFirst();
    
    public abstract ComplexityData getSecond();
}
