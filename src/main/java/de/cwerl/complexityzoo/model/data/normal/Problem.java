package de.cwerl.complexityzoo.model.data.normal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import de.cwerl.complexityzoo.model.TinyMCESuggestion;
import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(ComplexityDataType.Values.NORMAL)
public class Problem extends AbstractProblem {
    
    @Getter @Setter
    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

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
