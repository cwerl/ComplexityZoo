package de.cwerl.complexityzoo.model.relations;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.data.Problem;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PTPRelation extends Relation {
    
    @ManyToOne
    @JoinColumn(name = "first_problem_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    private Problem firstProblem;

    @ManyToOne
    @JoinColumn(name = "second_problem_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter @Setter
    private Problem secondProblem;

    @Getter @Setter
    String type;
}
