package de.cwerl.complexityzoo.model.data.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.Problem;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(ComplexityDataType.Values.PARAMETERIZED_SUB)
public class ParaSubProblem extends Problem {
    
    @Getter @Setter
    private String parameter;

    @Getter @Setter
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ParaProblem parentProblem;

    @Override
    public TinyMCESuggestion toTinyMCESuggestion() {
        TinyMCESuggestion suggestion = new TinyMCESuggestion();
        suggestion.setText(this.getName() + "(" + this.getParameter() + ")");
        suggestion.setType("Problem");
        suggestion.setValue("<a href=\"../problems/" + this.getId() + "\">"+ this.getName() +"</a>");
        return suggestion;
    }
}
