package de.cwerl.complexityzoo.model.relations;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.model.Problem;
import lombok.Getter;
import lombok.Setter;

@Entity
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
