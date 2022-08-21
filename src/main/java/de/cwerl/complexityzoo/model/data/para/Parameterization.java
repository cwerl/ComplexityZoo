package de.cwerl.complexityzoo.model.data.para;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.normal.Problem;
import lombok.Getter;
import lombok.Setter;


@Entity
@DiscriminatorValue(ComplexityDataType.Values.PARAMETERIZED)
public class Parameterization extends AbstractProblem {

    @Getter @Setter
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Problem parent;

    @Override
    public String getName() {
        return parent.getName() + " (" + super.getName() + ")";
    }

    public String getParameter() {
        return super.getName();
    }

    @Override
    public String getPath() {
        return "/problems/" + this.getParent().getId() + "/params/" + this.getId();
    }
}
