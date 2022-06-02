package de.cwerl.complexityzoo.model.relations;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.Problem;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class CTPRelation extends Relation {

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    private ComplexityClass complexityClass;

    @ManyToOne
    @JoinColumn(name = "problem_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    private Problem problem;
}
