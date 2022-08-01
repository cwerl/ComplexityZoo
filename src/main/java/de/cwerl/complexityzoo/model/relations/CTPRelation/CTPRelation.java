package de.cwerl.complexityzoo.model.relations.CTPRelation;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.ComplexityData;
import de.cwerl.complexityzoo.model.relations.Relation;
import de.cwerl.complexityzoo.model.data.AbstractProblem;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CTPRelation extends Relation {

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    @NotNull
    private ComplexityClass complexityClass;

    @ManyToOne
    @JoinColumn(name = "problem_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    @NotNull
    private AbstractProblem problem;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @NotNull
    private CTPRelationType relationType;

    @Override
    public String getPath() {
        return "/relations/ctp/" + this.getId();
    }

    @Override
    public ComplexityData getFirst() {
        return problem;
    }

    @Override
    public ComplexityData getSecond() {
        return complexityClass;
    }
}
