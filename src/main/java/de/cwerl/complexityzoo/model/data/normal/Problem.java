package de.cwerl.complexityzoo.model.data.normal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
@Entity
@DiscriminatorValue(ComplexityDataType.Values.NORMAL)
public class Problem extends AbstractProblem {

    public TinyMCESuggestion toTinyMCESuggestion() {
        TinyMCESuggestion suggestion = new TinyMCESuggestion();
        suggestion.setText(this.getName());
        suggestion.setType("Problem");
        suggestion.setValue("<a href=\"../problems/" + this.getId() + "\">"+ this.getName() +"</a>");
        return suggestion;
    }

    @Override
    public String getPath() {
        return "/problems/" + this.getId();
    }
}
