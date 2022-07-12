package de.cwerl.complexityzoo.model.relations.PTPRelation;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityData;
import de.cwerl.complexityzoo.model.relations.Relation;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PTPRelation extends Relation {
    
    @ManyToOne
    @JoinColumn(name = "first_problem_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    @NotNull
    private AbstractProblem firstProblem;

    @ManyToOne
    @JoinColumn(name = "second_problem_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    @NotNull
    private AbstractProblem secondProblem;

    @Getter @Setter
    @NotBlank
    String relationType;

    @Override
    public String getPath() {
        return "/relations/ptp/" + this.getId();
    }

    @Override
    public ComplexityData getFirst() {
        return firstProblem;
    }

    @Override
    public ComplexityData getSecond() {
        return secondProblem;
    }
}
